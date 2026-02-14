package com.example.authappmobile.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.authappmobile.R
import com.example.authappmobile.model.RegisterRequest
import com.example.authappmobile.network.RetrofitClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RegisterActivity : AppCompatActivity() {
    private lateinit var firstName: EditText
    private lateinit var lastName: EditText
    private lateinit var email: EditText
    private lateinit var password: EditText
    private lateinit var btnRegister: Button
    private lateinit var progress: ProgressBar
    private lateinit var togglePasswordBtn: ImageButton
    private val TAG = "RegisterActivity"
    private var showPassword = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        firstName = findViewById(R.id.firstName)
        lastName = findViewById(R.id.lastName)
        email = findViewById(R.id.email)
        password = findViewById(R.id.password)
        btnRegister = findViewById(R.id.btnRegister)
        progress = findViewById(R.id.progress)
        togglePasswordBtn = findViewById(R.id.togglePassword)

        togglePasswordBtn.setOnClickListener {
            showPassword = !showPassword
            if (showPassword) {
                password.inputType = android.text.InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                togglePasswordBtn.setImageResource(R.drawable.ic_eye_off)
            } else {
                password.inputType = android.text.InputType.TYPE_CLASS_TEXT or android.text.InputType.TYPE_TEXT_VARIATION_PASSWORD
                togglePasswordBtn.setImageResource(R.drawable.ic_eye)
            }
            password.setSelection(password.text.length)
        }

        btnRegister.setOnClickListener {
            val f = firstName.text.toString().trim()
            val l = lastName.text.toString().trim()
            val e = email.text.toString().trim()
            val p = password.text.toString().trim()
            if (f.isEmpty() || l.isEmpty() || e.isEmpty() || p.isEmpty()) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            progress.visibility = android.view.View.VISIBLE
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    Log.d(TAG, "Attempting register with email: $e")
                    val req = RegisterRequest(e, f, l, p)
                    val res = RetrofitClient.api.register(req)
                    if (res.isSuccessful) {
                        val body = res.body()
                        Log.d(TAG, "Register successful: $body")
                        val prefs = getSharedPreferences("auth", MODE_PRIVATE)
                        prefs.edit().putString("token", body?.token).apply()
                        prefs.edit().putLong("userId", body?.userId ?: -1L).apply()
                        prefs.edit().putString("name", body?.name).apply()
                        prefs.edit().putString("email", body?.email).apply()

                        withContext(Dispatchers.Main) {
                            progress.visibility = android.view.View.GONE
                            val intent = Intent(this@RegisterActivity, ProfileActivity::class.java)
                            startActivity(intent)
                            finish()
                        }
                    } else {
                        Log.e(TAG, "Register failed: ${res.code()} ${res.message()}")
                        Log.e(TAG, "Error body: ${res.errorBody()?.string()}")
                        withContext(Dispatchers.Main) {
                            progress.visibility = android.view.View.GONE
                            Toast.makeText(this@RegisterActivity, "Registration failed: ${res.message()}", Toast.LENGTH_SHORT).show()
                        }
                    }
                } catch (e: Exception) {
                    Log.e(TAG, "Network error", e)
                    withContext(Dispatchers.Main) {
                        progress.visibility = android.view.View.GONE
                        Toast.makeText(this@RegisterActivity, "Network error: ${e.message}", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}
