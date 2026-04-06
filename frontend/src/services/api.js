import axios from "axios";

const api = axios.create({
  baseURL: process.env.REACT_APP_API_URL || "http://localhost:8080/api",
  headers: { "Content-Type": "application/json" },
});

api.interceptors.response.use(
  res => res,
  err => {
    if (err.response?.status === 401) {
      localStorage.removeItem("gr_user");
      window.location.href = "/login";
    }
    return Promise.reject(err);
  }
);

export default api;

// ── Local booking store ──────────────────────────────────────
const KEY = "gr_bookings";

function loadDb() {
  try {
    const raw = localStorage.getItem(KEY);
    return raw
      ? JSON.parse(raw)
      : { roomBookings: [], tableBookings: [], nextRoomId: 1, nextTableId: 1 };
  } catch {
    return { roomBookings: [], tableBookings: [], nextRoomId: 1, nextTableId: 1 };
  }
}
function saveDb(db) { localStorage.setItem(KEY, JSON.stringify(db)); }

export const BookingService = {
  // ── Create ─────────────────────────────────────────────────
  createRoomBooking: (data) => {
    const db = loadDb();
    const booking = { ...data, id: db.nextRoomId++, status: "pending", createdAt: new Date().toISOString() };
    db.roomBookings.push(booking);
    saveDb(db);
    return Promise.resolve(booking);
  },

  createTableBooking: (data) => {
    const db = loadDb();
    const booking = { ...data, id: db.nextTableId++, status: "pending", createdAt: new Date().toISOString() };
    db.tableBookings.push(booking);
    saveDb(db);
    return Promise.resolve(booking);
  },

  // ── Read all (management) ───────────────────────────────────
  getAllRoomBookings: () => Promise.resolve(loadDb().roomBookings),
  getAllTableBookings: () => Promise.resolve(loadDb().tableBookings),

  // ── Read by user (customer) ─────────────────────────────────
  getRoomBookingsByUser: (userId) =>
    Promise.resolve(loadDb().roomBookings.filter(b => b.userId === userId)),
  getTableBookingsByUser: (userId) =>
    Promise.resolve(loadDb().tableBookings.filter(b => b.userId === userId)),

  // ── Update status (management) ──────────────────────────────
  updateBookingStatus: (type, id, status) => {
    const db = loadDb();
    const arr = type === "room" ? db.roomBookings : db.tableBookings;
    const item = arr.find(x => x.id === id);
    if (item) item.status = status;
    saveDb(db);
    return Promise.resolve(item);
  },
};
