// ── Badge ────────────────────────────────────────────────────
export function Badge({ status }) {
  return (
    <span className={`badge badge-${status}`}>
      {status.charAt(0).toUpperCase() + status.slice(1)}
    </span>
  );
}

// ── Field wrapper ────────────────────────────────────────────
export function Field({ label, children, style }) {
  return (
    <div className="field" style={style}>
      {label && <label>{label}</label>}
      {children}
    </div>
  );
}

// ── Step progress bar ────────────────────────────────────────
export function StepBar({ steps, current }) {
  return (
    <div className="step-bar">
      {Array.from({ length: steps }).map((_, i) => (
        <div key={i} className={`step${current > i ? " active" : ""}`} />
      ))}
    </div>
  );
}

// ── Toggle button (room type, seat count, etc.) ──────────────
export function ToggleBtn({ label, sub, active, onClick }) {
  return (
    <button className={`toggle-btn${active ? " active" : ""}`} onClick={onClick}>
      {label}
      {sub && (
        <span style={{ display: "block", fontSize: 11, marginTop: 2, color: active ? "var(--accent)" : "var(--text-muted)" }}>
          {sub}
        </span>
      )}
    </button>
  );
}

// ── Metric card ──────────────────────────────────────────────
export function MetricCard({ label, value, accent }) {
  return (
    <div style={{ background: "#f5f4f0", borderRadius: 10, padding: "1rem" }}>
      <div style={{ fontSize: 12, color: "var(--text-muted)", marginBottom: 4 }}>{label}</div>
      <div style={{ fontSize: 22, fontWeight: 600, color: accent || "var(--text)" }}>{value}</div>
    </div>
  );
}

// ── Spinner ──────────────────────────────────────────────────
export function Spinner() {
  return (
    <div style={{ display: "flex", justifyContent: "center", padding: "2rem" }}>
      <div style={{ width: 28, height: 28, border: "3px solid var(--border)", borderTopColor: "var(--accent)", borderRadius: "50%", animation: "spin 0.7s linear infinite" }} />
      <style>{`@keyframes spin { to { transform: rotate(360deg); } }`}</style>
    </div>
  );
}

// ── Back button ──────────────────────────────────────────────
export function BackBtn({ onClick, label = "Back" }) {
  return (
    <button className="btn btn-ghost" onClick={onClick} style={{ marginBottom: 16, paddingLeft: 8 }}>
      ← {label}
    </button>
  );
}

// ── Empty state ──────────────────────────────────────────────
export function EmptyState({ icon = "📭", message }) {
  return (
    <div style={{ textAlign: "center", padding: "3rem 1rem", color: "var(--text-muted)" }}>
      <div style={{ fontSize: 40, marginBottom: 12 }}>{icon}</div>
      <p>{message}</p>
    </div>
  );
}
