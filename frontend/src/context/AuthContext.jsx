import { createContext, useContext, useState, useEffect } from "react";
import api from "../services/api";

const AuthContext = createContext(null);

function loadUsers() {
  try {
    const raw = localStorage.getItem("gr_users");
    return raw ? JSON.parse(raw) : [
      { id: 1, username: "staff1", email: "staff@grandreserve.com", password: "staff123", name: "Priya Verma", role: "MANAGEMENT" },
    ];
  } catch { return []; }
}
function saveUsers(u) { localStorage.setItem("gr_users", JSON.stringify(u)); }
function nextId() {
  const u = loadUsers();
  return u.length ? Math.max(...u.map(x => x.id)) + 1 : 2;
}

export function AuthProvider({ children }) {
  const [user, setUser]       = useState(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const s = localStorage.getItem("gr_user");
    if (s) setUser(JSON.parse(s));
    setLoading(false);
  }, []);

  const login = async (identifier, password, role) => {
    // Production → await api.post("/auth/login", { identifier, password, role });
    const users = loadUsers();
    const found = users.find(u =>
      (u.email === identifier || u.username === identifier) &&
      u.password === password && u.role === role
    );
    if (!found) throw new Error("Invalid credentials or wrong role selected.");
    const { password: _, ...safe } = found;
    localStorage.setItem("gr_user", JSON.stringify(safe));
    setUser(safe);
    return safe;
  };

  const register = async ({ name, email, password }) => {
    // Production → await api.post("/auth/register", { name, email, password, role: "CUSTOMER" });
    const users = loadUsers();
    if (users.find(u => u.email === email.toLowerCase()))
      throw new Error("An account with this email already exists.");
    const newUser = {
      id: nextId(),
      username: email.toLowerCase(),
      email: email.toLowerCase(),
      password,
      name: name.trim(),
      role: "CUSTOMER",
    };
    saveUsers([...users, newUser]);
    const { password: _, ...safe } = newUser;
    localStorage.setItem("gr_user", JSON.stringify(safe));
    setUser(safe);
    return safe;
  };

  const logout = () => {
    localStorage.removeItem("gr_user");
    delete api.defaults.headers.common["Authorization"];
    setUser(null);
  };

  return (
    <AuthContext.Provider value={{ user, login, register, logout, loading }}>
      {children}
    </AuthContext.Provider>
  );
}

export const useAuth = () => useContext(AuthContext);
