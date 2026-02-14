package com.example.authappmobile.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.authappmobile.R
import com.example.authappmobile.model.LoginRequest
import com.example.authappmobile.network.RetrofitClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginActivity : AppCompatActivity() {
    private lateinit var emailInput: EditText
    private lateinit var passwordInput: EditText
    private lateinit var loginBtn: Button
    private lateinit var registerLink: TextView
    private lateinit var progress: ProgressBar
    private lateinit var togglePasswordBtn: ImageButton
    private val TAG = "LoginActivity"
    private var showPassword = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        emailInput = findViewById(R.id.email)
        passwordInput = findViewById(R.id.password)
        loginBtn = findViewById(R.id.btnLogin)
        togglePasswordBtn = findViewById(R.id.togglePassword)

        togglePasswordBtn.setOnClickListener {
            showPassword = !showPassword
            if (showPassword) {
                passwordInput.inputType = android.text.InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                togglePasswordBtn.setImageResource(R.drawable.ic_eye_off)
            } else {
                passwordInput.inputType = android.text.InputType.TYPE_CLASS_TEXT or android.text.InputType.TYPE_TEXT_VARIATION_PASSWORD
                togglePasswordBtn.setImageResource(R.drawable.ic_eye)
            }
            passwordInput.setSelection(passwordInput.text.length)
        }
        registerLink = findViewById(R.id.linkRegister)
        progress = findViewById(R.id.progress)

        loginBtn.setOnClickListener {
            val email = emailInput.text.toString().trim()
            val password = passwordInput.text.toString().trim()
            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            progress.visibility = android.view.View.VISIBLE
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    Log.d(TAG, "Attempting login with email: $email")
                    val res = RetrofitClient.api.login(LoginRequest(email, password))
                    if (res.isSuccessful) {
                        val body = res.body()
                        Log.d(TAG, "Login successful: $body")
                        // Save token and navigate to ProfileActivity
                        val prefs = getSharedPreferences("auth", MODE_PRIVATE)
                        prefs.edit().putString("token", body?.token).apply()
                        prefs.edit().putLong("userId", body?.userId ?: -1L).apply()
                        prefs.edit().putString("name", body?.name).apply()
                        prefs.edit().putString("email", body?.email).apply()

                        withContext(Dispatchers.Main) {
                            progress.visibility = android.view.View.GONE
                            val intent = Intent(this@LoginActivity, ProfileActivity::class.java)
                            startActivity(intent)
                            finish()
                        }
                    } else {
                        Log.e(TAG, "Login failed: ${res.code()} ${res.message()}")
                        Log.e(TAG, "Error body: ${res.errorBody()?.string()}")
                        withContext(Dispatchers.Main) {
                            progress.visibility = android.view.View.GONE
                            Toast.makeText(this@LoginActivity, "Login failed: ${res.message()}", Toast.LENGTH_SHORT).show()
                        }
                    }
                } catch (e: Exception) {
                    Log.e(TAG, "Network error", e)
                    withContext(Dispatchers.Main) {
                        progress.visibility = android.view.View.GONE
                        Toast.makeText(this@LoginActivity, "Network error: ${e.message}", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

        registerLink.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }
}
