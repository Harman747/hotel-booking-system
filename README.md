# 🏨 Grand Reserve — Hospitality Reservation System (v2)

Full-stack online table & room reservation system with customer self-registration, booking history, and management dashboard.

---

## Project Structure

```
grand-reserve-v2/
├── frontend/                  # React 18 app
│   └── src/
│       ├── context/AuthContext.jsx        # Login, register, session
│       ├── pages/
│       │   ├── LoginPage.jsx              # Sign in + Create Account tabs
│       │   ├── CustomerHome.jsx           # Home + My Bookings tabs
│       │   ├── RoomBooking.jsx            # 2-step room booking form
│       │   ├── TableBooking.jsx           # Table booking form
│       │   └── ManagementDashboard.jsx    # Staff dashboard (4 tabs)
│       ├── components/
│       │   ├── Receipt.jsx                # E-receipt modal
│       │   ├── Navbar.jsx
│       │   └── UI.jsx                     # Shared components
│       └── services/api.js               # Axios + localStorage BookingService
│
└── backend/                   # Spring Boot 3 + JWT
    └── src/main/java/com/grandreserve/
        ├── entity/User.java               # Has username + email fields
        ├── service/AuthService.java       # Login by email OR username
        ├── controller/AuthController.java # /register + /admin/create-staff
        └── config/DataSeeder.java         # Seeds default staff on startup
```

---

## 🚀 Quick Start

### Frontend (works standalone — no backend needed)
```bash
cd frontend
npm install
npm start
# Opens at http://localhost:3000
```

### Backend
```bash
cd backend
mvn spring-boot:run
# Starts at http://localhost:8080
# H2 console: http://localhost:8080/h2-console (JDBC URL: jdbc:h2:mem:grandreserve)
```

---

## 👤 User Roles & Authentication

### Customers — self-register via UI
- Click **"Create Account"** on the login page
- Enter name, email, and password (min 6 chars)
- Login using **email address** + password
- Role is always `CUSTOMER` — cannot be changed via UI

### Management Staff — backend only

Staff accounts **cannot** be created from the website. There are two ways to create them:

---

#### Method 1 — DataSeeder (automatic on startup)
Edit `DataSeeder.java` and add a new block:

```java
if (!userRepository.existsByUsername("staff2")) {
    userRepository.save(User.builder()
        .username("staff2")
        .email("staff2@grandreserve.com")
        .password(passwordEncoder.encode("yourpassword"))
        .name("Amit Kumar")
        .role(User.Role.MANAGEMENT)
        .build());
}
```
Then restart the backend with `mvn spring-boot:run`.

---

#### Method 2 — API endpoint (no restart needed)

**Step 1** — Login as an existing staff member to get a JWT token:
```bash
POST http://localhost:8080/api/auth/login
Content-Type: application/json

{
  "identifier": "staff1",
  "password": "staff123",
  "role": "MANAGEMENT"
}
```
Copy the `token` from the response.

**Step 2** — Create the new staff account:
```bash
POST http://localhost:8080/api/auth/admin/create-staff
Content-Type: application/json
Authorization: Bearer <paste_token_here>

{
  "name": "Amit Kumar",
  "username": "staff2",
  "password": "securepassword",
  "email": "amit@grandreserve.com"
}
```

You can use any REST client (Postman, Insomnia, curl, or the browser's H2 console).

---

#### Default staff credentials (seeded on first run)
| Username | Password  | Name        |
|----------|-----------|-------------|
| staff1   | staff123  | Priya Verma |

> ⚠️ Change this password in production by editing `DataSeeder.java` before first run, or using the API.

---

## 🌐 API Reference

### Auth endpoints
| Method | URL                           | Access         | Description                    |
|--------|-------------------------------|----------------|--------------------------------|
| POST   | /api/auth/login               | Public         | Login (email or username)      |
| POST   | /api/auth/register            | Public         | Customer self-registration     |
| POST   | /api/auth/admin/create-staff  | Management JWT | Create a staff account         |

### Room Bookings
| Method | URL                          | Access     | Description              |
|--------|------------------------------|------------|--------------------------|
| POST   | /api/rooms                   | Any auth   | Create room booking      |
| GET    | /api/rooms                   | Management | All room bookings        |
| GET    | /api/rooms/my/{userId}       | Any auth   | Bookings by user         |
| PATCH  | /api/rooms/{id}/status       | Management | Accept / cancel          |
| DELETE | /api/rooms/{id}              | Management | Delete booking           |
| GET    | /api/rooms/floor/{floor}     | Management | Floor occupancy          |

### Table Bookings
| Method | URL                          | Access     | Description              |
|--------|------------------------------|------------|--------------------------|
| POST   | /api/tables                  | Any auth   | Create table booking     |
| GET    | /api/tables                  | Management | All table bookings       |
| GET    | /api/tables/my/{userId}      | Any auth   | Bookings by user         |
| PATCH  | /api/tables/{id}/status      | Management | Accept / cancel          |
| DELETE | /api/tables/{id}             | Management | Delete booking           |

---

## 💰 Pricing

### Room (per night)
| Feature    | Options                                                       |
|------------|---------------------------------------------------------------|
| Room Type  | Single ₹1,500 · Double ₹2,500 · Triple ₹3,500 · Quad ₹5,000 |
| Bed Size   | Twin +₹300 · Queen +₹500 · King +₹700                        |
| Floor      | F1 +₹0 · F2 +₹200 · F3 +₹400 · F4 +₹600 · F5 +₹800          |
| Balcony    | +₹500                                                         |
| Pool       | +₹800                                                         |

### Table (cover charge)
| Seats | Price  |
|-------|--------|
| 1     | ₹200   |
| 2     | ₹350   |
| 4     | ₹600   |
| 10    | ₹1,200 |
| 20    | ₹2,200 |

---

## 🔌 Connecting Frontend to Real Backend

The frontend uses **localStorage** by default (fully functional without a backend).

To switch to Spring Boot:

1. In `AuthContext.jsx`, replace the `login()` and `register()` bodies with real `api.post()` calls.
2. In `api.js`, replace each `BookingService` method with the corresponding REST call.
3. Set `REACT_APP_API_URL=http://localhost:8080/api` in a `.env` file.

---

## 🛠️ Tech Stack

| Layer    | Technology                              |
|----------|-----------------------------------------|
| Frontend | React 18, React Router v6, Axios        |
| Backend  | Spring Boot 3.2, Spring Security 6      |
| Auth     | JWT (jjwt 0.11.5), BCrypt passwords     |
| Database | H2 in-memory (dev) → MySQL/PostgreSQL   |
| ORM      | Spring Data JPA / Hibernate             |
