# Android Mobile App Setup

## Prerequisites
- Android Studio with Android SDK
- Backend running on localhost:8080 (Spring Boot)
- Android Emulator or physical device with Android 5.0+ (API 24+)

## Build & Run

1. Open the `mobile` folder in Android Studio
2. Let Gradle sync and download dependencies
3. Connect emulator or device
4. Run the app: `Run > Run 'app'` or press `Shift+F10`

## Network Configuration

The app connects to the backend via Retrofit:
- **Emulator**: Uses `http://10.0.2.2:8080/` (special alias for host's localhost)
- **Physical Device**: Update `BASE_URL` in `RetrofitClient.kt` to your machine's IP (e.g., `http://192.168.x.x:8080/`)

## Key Fixes Applied

1. **INTERNET Permission**: Added `<uses-permission android:name="android.permission.INTERNET" />` to `AndroidManifest.xml`
2. **JSON Field Mapping**: Used `@SerializedName` annotations on Kotlin data classes to match backend JSON field names:
   - `userId` (backend) → `userId` (model)
   - `user_id`, `first_name`, `last_name` (backend) mapped in `User` model
3. **Timeouts**: Set 30-second connect/read/write timeouts in OkHttp
4. **Logging**: Added Log statements to show actual network errors in Logcat

## Debugging

1. **Check Logcat** for error details (Android Studio: View > Tool Windows > Logcat)
   - Search for `LoginActivity` or `RegisterActivity` tags
   - Look for `E/` (error) level logs to see the actual failure reason

2. **Common Issues**:
   - "Connection refused": Backend is not running
   - "Timeout": Backend is too slow or network is unstable
   - "Invalid JSON": Field name mismatch (check @SerializedName)
   - "401 Unauthorized": Wrong credentials

3. **Backend Status**: Verify backend is running:
   ```bash
   cd backend
   .\mvnw.cmd spring-boot:run
   ```

## Test Credentials

Register a new account or use existing credentials created in the database.

## Architecture

- **Models**: `com.example.authappmobile.model.*` - Retrofit-compatible data classes
- **Network**: `com.example.authappmobile.network.RetrofitClient` - Retrofit instance & OkHttp config
- **UI**: 
  - `LoginActivity` - Login form & validation
  - `RegisterActivity` - Registration form & validation
  - `ProfileActivity` - Fetch & display user profile

All authentication data stored locally in SharedPreferences.
