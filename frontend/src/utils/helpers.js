export const ROOM_PRICES = {
  type:    { Single: 1500, Double: 2500, Triple: 3500, Quad: 5000 },
  bed:     { Twin: 300, Queen: 500, King: 700 },
  floor:   { 1: 0, 2: 200, 3: 400, 4: 600, 5: 800 },
  balcony: 500,
  pool:    800,
};

export const TABLE_PRICES = { 1: 200, 2: 350, 4: 600, 10: 1200, 20: 2200 };

export const calcRoomPrice = ({ roomType, bedSize, floor, balcony, pool }) => {
  let total = ROOM_PRICES.type[roomType]  || 0;
  total    += ROOM_PRICES.bed[bedSize]    || 0;
  total    += ROOM_PRICES.floor[floor]    || 0;
  if (balcony) total += ROOM_PRICES.balcony;
  if (pool)    total += ROOM_PRICES.pool;
  return total;
};

export const fmt = (n) =>
  `₹${Number(n).toLocaleString("en-IN")}`;

export const formatDate = (iso) =>
  new Date(iso).toLocaleDateString("en-IN", { day: "2-digit", month: "short", year: "numeric" });

export const padId = (id, prefix = "B") =>
  `${prefix}${String(id).padStart(4, "0")}`;
