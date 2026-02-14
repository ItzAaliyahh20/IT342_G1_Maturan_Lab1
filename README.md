# IT342_G1_MATURAN - Authentication Application

**Developer:** Frances Aaliyah Maturan  
**Date:** February 15, 2026

---

## Project Description

A full-stack authentication application with a modern, responsive UI across web and mobile platforms. The project demonstrates a complete implementation of user registration, login, and profile management with secure token-based authentication.

**Key Features:**
- User registration with validation
- Secure login with JWT tokens
- Protected profile endpoint
- Responsive web UI with gradient design
- Native Android mobile app with modern Material3 design
- Real-time password visibility toggle
- Token-based session management

---

## Technologies Used

### Backend
- **Framework:** Spring Boot 2.7.13
- **Language:** Java 11
- **Build Tool:** Maven
- **Database:** (Configured in application.properties)
- **Authentication:** JWT (Token-based)
- **Dependencies:**
  - Spring Web
  - Spring Data JPA
  - Spring Security (implied)

### Web Frontend
- **Framework:** React 18
- **Routing:** React Router DOM
- **Styling:** CSS3 with gradients & animations
- **HTTP Client:** Fetch API
- **Build Tool:** Create React App
- **Icons:** Inline SVG (2D)
- **State Management:** React Context API

### Mobile App
- **Language:** Kotlin
- **Platform:** Android (API 24+)
- **Framework:** Android AppCompat
- **Network Library:** Retrofit 2.9.0
- **JSON Serialization:** Gson 2.10.1
- **Async:** Kotlin Coroutines
- **UI:** Material3 with ConstraintLayout & CardView
- **Icons:** SVG drawables

---

## Steps to Run Backend

### Prerequisites
- Java 11 or higher
- Maven installed

### Instructions

1. **Navigate to backend directory:**
   ```bash
   cd backend
   ```

2. **Build the project:**
   ```bash
   .\mvnw.cmd clean install -DskipTests
   ```

3. **Run the Spring Boot application:**
   ```bash
   .\mvnw.cmd spring-boot:run
   ```

   The backend will start on `http://localhost:8080`

4. **(Optional) Run tests:**
   ```bash
   .\mvnw.cmd test
   ```

### Configuration
- Edit `src/main/resources/application.properties` to configure:
  - Database connection
  - Server port
  - Logging levels

---

## Steps to Run Web App

### Prerequisites
- Node.js 16+ and npm

### Instructions

1. **Navigate to web directory:**
   ```bash
   cd web
   ```

2. **Install dependencies:**
   ```bash
   npm install
   ```

3. **Start development server:**
   ```bash
   npm start
   ```

   The web app will open in browser at `http://localhost:3000`

4. **Build for production:**
   ```bash
   npm run build
   ```

### Features
- **Login Page** (`/login`) - Email & password with eye icon toggle
- **Register Page** (`/register`) - Create new account with first/last name
- **Dashboard** (`/dashboard`) - Protected route showing user profile and settings
- **Authentication:** Token stored in localStorage, auto-logout if token expires

---

## Steps to Run Mobile App

### Prerequisites
- Android Studio installed
- Android SDK (API 24+)
- Android Emulator or physical device

### Configuration

1. **Update backend URL** (for physical devices):
   - Open `mobile/app/src/main/java/com/example/authappmobile/network/RetrofitClient.kt`
   - Replace `http://10.0.2.2:8080/` with your machine's IP (e.g., `http://192.168.x.x:8080/`)

### Instructions

1. **Open mobile folder in Android Studio:**
   ```bash
   File > Open > select 'mobile' folder
   ```

2. **Sync Gradle:**
   - Let Android Studio sync dependencies automatically
   - Or: `Build > Rebuild Project`

3. **Run the app:**
   - Connect emulator or device
   - Press `Shift+F10` or click `Run > Run 'app'`

4. **Verify network connection:**
   - Ensure backend is running on localhost:8080
   - Check Logcat for network errors (View > Tool Windows > Logcat)

### Features
- **Login Screen** - Email/password with password visibility toggle
- **Register Screen** - First name, last name, email, password
- **Profile Screen** - Displays user info fetched from backend with avatar
- **Authentication:** Token stored in SharedPreferences
- **Network:** Retrofit with logging interceptor for debugging

### Troubleshooting
- **"Cleartext communication not permitted"** → Already fixed via `network_security_config.xml`
- **"Network error"** → Check backend is running and Logcat for details
- **Red files in IDE** → `Build > Rebuild Project` and `File > Invalidate Caches / Restart`

---

## API Endpoints

### Authentication

#### 1. Register User
```
POST /register
Content-Type: application/json

Request Body:
{
  "email": "user@example.com",
  "first_name": "John",
  "last_name": "Doe",
  "password": "secure_password"
}

Response (201 Created):
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "userId": 1,
  "name": "John Doe",
  "email": "user@example.com"
}
```

#### 2. Login User
```
POST /login
Content-Type: application/json

Request Body:
{
  "email": "user@example.com",
  "password": "secure_password"
}

Response (200 OK):
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
  "userId": 1,
  "name": "John Doe",
  "email": "user@example.com"
}
```

#### 3. Logout User
```
POST /logout
Authorization: Bearer <token>
(or) ?token=<token>

Response (200 OK):
{}
```

### Dashboard

#### 4. Get User Profile
```
GET /profile
Authorization: Bearer <token>

Response (200 OK):
{
  "user_id": 1,
  "email": "user@example.com",
  "first_name": "John",
  "last_name": "Doe"
}

Response (401 Unauthorized):
"Invalid or expired token"
```

### Error Responses
All endpoints return appropriate HTTP status codes:
- `200 OK` - Successful request
- `201 Created` - Resource created
- `400 Bad Request` - Validation error
- `401 Unauthorized` - Invalid/missing token
- `500 Internal Server Error` - Server-side error

---

## Project Structure

```
├── backend/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/example/IT342_G1_Maturan/
│   │   │   │   ├── controller/     # REST endpoints
│   │   │   │   ├── service/        # Business logic
│   │   │   │   ├── entity/         # Database models
│   │   │   │   ├── dto/            # Data transfer objects
│   │   │   │   └── repository/     # Database access
│   │   │   └── resources/
│   │   │       └── application.properties
│   │   └── test/
│   └── pom.xml
├── web/
│   ├── public/
│   ├── src/
│   │   ├── components/   # Reusable components
│   │   ├── pages/        # Page components
│   │   ├── context/      # React context
│   │   ├── styles/       # CSS files
│   │   └── App.js
│   ├── package.json
│   └── README.md
├── mobile/
│   ├── app/
│   │   ├── src/
│   │   │   ├── main/
│   │   │   │   ├── java/com/example/authappmobile/
│   │   │   │   │   ├── ui/              # Activities
│   │   │   │   │   ├── network/         # Retrofit setup
│   │   │   │   │   └── model/           # Data models
│   │   │   │   ├── res/
│   │   │   │   │   ├── layout/          # XML layouts
│   │   │   │   │   ├── drawable/        # SVG icons & shapes
│   │   │   │   │   └── values/          # Colors, themes, dimensions
│   │   │   │   └── AndroidManifest.xml
│   │   │   └── test/
│   │   └── build.gradle.kts
│   ├── settings.gradle.kts
│   └── MOBILE_SETUP.md
├── docs/                 # Documentation
└── README.md
```

---

## Quick Start Guide

### All Three Services Running

**Terminal 1 - Backend:**
```bash
cd backend && .\mvnw.cmd spring-boot:run
```

**Terminal 2 - Web:**
```bash
cd web && npm install && npm start
```

**Terminal 3 - Mobile:**
- Open `mobile` folder in Android Studio and run emulator

### Test Workflow
1. Go to `http://localhost:3000/register` (web) or use mobile app
2. Register a new account
3. You'll be redirected to dashboard/profile
4. View your profile info
5. Click Logout to clear session

---

## Notes
- All authentication uses JWT tokens stored client-side
- Tokens are required for accessing protected endpoints
- Frontend redirects unauthenticated users to login
- Mobile app uses emulator IP `10.0.2.2` to reach localhost
