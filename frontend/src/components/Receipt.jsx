import { fmt, formatDate, padId } from "../utils/helpers";

export default function Receipt({ booking, type, onClose }) {
  const isRoom = type === "room";
  const rows = isRoom
    ? [
        ["Guest Name",   booking.name],
        ["Age",          booking.age],
        ["Contact",      booking.contact],
        ["Address",      booking.address],
        ["ID Proof",     booking.idProof],
        ["Room Type",    booking.roomType],
        ["Bed Size",     booking.bedSize],
        ["Floor",        `Floor ${booking.floor}`],
        ["Balcony",      booking.balcony ? "Yes ✓" : "No"],
        ["Pool Access",  booking.pool    ? "Yes ✓" : "No"],
        ["Booked On",    formatDate(booking.createdAt)],
      ]
    : [
        ["Guest Name",  booking.name],
        ["Age",         booking.age],
        ["Contact",     booking.contact],
        ["Floor",       `Floor ${booking.floor}`],
        ["Seats",       booking.seats],
        ["Booked On",   formatDate(booking.createdAt)],
      ];

  return (
    <div style={{
      position: "fixed", inset: 0,
      background: "rgba(0,0,0,0.55)",
      display: "flex", alignItems: "center", justifyContent: "center",
      zIndex: 999, padding: 16,
    }}>
      <div style={{
        background: "#fff", borderRadius: 16, padding: "2rem",
        maxWidth: 460, width: "100%", maxHeight: "90vh", overflowY: "auto",
      }}>
        {/* Header */}
        <div style={{ textAlign: "center", marginBottom: 20 }}>
          <div style={{ fontSize: 42 }}>✅</div>
          <h2 style={{ fontFamily: "'Playfair Display', serif", color: "#1a1a2e", margin: "8px 0 4px" }}>
            {booking.status === "accepted" ? "Booking Confirmed!" : "Booking Submitted!"}
          </h2>
          <p style={{ color: "#6b7280", fontSize: 13 }}>
            E-Receipt · {padId(booking.id, isRoom ? "R" : "T")}
          </p>
        </div>

        {/* Details table */}
        <div style={{ border: "1px dashed #ddd", borderRadius: 10, padding: "1rem", marginBottom: 16 }}>
          <table style={{ width: "100%", fontSize: 13, borderCollapse: "collapse" }}>
            <tbody>
              {rows.map(([k, v]) => (
                <tr key={k}>
                  <td style={{ color: "#6b7280", padding: "5px 0", width: "45%" }}>{k}</td>
                  <td style={{ fontWeight: 500, textAlign: "right", padding: "5px 0" }}>{v}</td>
                </tr>
              ))}
            </tbody>
          </table>
          <hr className="divider" />
          <div style={{ display: "flex", justifyContent: "space-between", alignItems: "center", fontWeight: 600, fontSize: 16 }}>
            <span>Total Amount</span>
            <span style={{ color: "var(--accent)", fontSize: 20 }}>{fmt(booking.price)}</span>
          </div>
        </div>

        <p style={{ fontSize: 12, color: "#9ca3af", textAlign: "center", marginBottom: 16 }}>
          {isRoom
            ? "Please carry a valid ID proof during check-in. Booking is valid only after management approval."
            : "Please arrive 15 minutes before your reservation. Booking valid after management approval."}
        </p>

        <button className="btn btn-primary btn-full" onClick={onClose}>
          Close Receipt
        </button>
      </div>
    </div>
  );
}
