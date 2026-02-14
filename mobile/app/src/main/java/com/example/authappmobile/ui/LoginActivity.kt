package com.example.authappmobile.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.ProgressBar
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
    private lateinit var registerLink: ImageView
    private lateinit var progress: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        emailInput = findViewById(R.id.email)
        passwordInput = findViewById(R.id.password)
        loginBtn = findViewById(R.id.btnLogin)
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
                    val res = RetrofitClient.api.login(LoginRequest(email, password))
                    if (res.isSuccessful) {
                        val body = res.body()
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
                        withContext(Dispatchers.Main) {
                            progress.visibility = android.view.View.GONE
                            Toast.makeText(this@LoginActivity, "Login failed", Toast.LENGTH_SHORT).show()
                        }
                    }
                } catch (e: Exception) {
                    withContext(Dispatchers.Main) {
                        progress.visibility = android.view.View.GONE
                        Toast.makeText(this@LoginActivity, "Network error", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

        registerLink.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }
}
