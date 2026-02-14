package com.example.authappmobile.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.authappmobile.R
import com.example.authappmobile.network.RetrofitClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ProfileActivity : AppCompatActivity() {
    private lateinit var nameView: TextView
    private lateinit var emailView: TextView
    private lateinit var fullNameView: TextView
    private lateinit var emailInfoView: TextView
    private lateinit var avatarText: TextView
    private lateinit var logoutBtn: Button
    private val TAG = "ProfileActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        nameView = findViewById(R.id.name)
        emailView = findViewById(R.id.email)
        fullNameView = findViewById(R.id.fullName)
        emailInfoView = findViewById(R.id.emailInfo)
        avatarText = findViewById(R.id.avatarText)
        logoutBtn = findViewById(R.id.btnLogout)

        val prefs = getSharedPreferences("auth", MODE_PRIVATE)
        val token = prefs.getString("token", null)
        if (token == null) {
            // Not authenticated
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
            return
        }

        CoroutineScope(Dispatchers.IO).launch {
            try {
                Log.d(TAG, "Fetching profile with token: $token")
                val res = RetrofitClient.api.profile("Bearer $token")
                if (res.isSuccessful) {
                    val user = res.body()
                    Log.d(TAG, "Profile fetched: $user")
                    withContext(Dispatchers.Main) {
                        val fullName = "${user?.first_name} ${user?.last_name}"
                        val firstLetter = user?.first_name?.firstOrNull()?.uppercaseChar() ?: "A"
                        nameView.text = fullName
                        emailView.text = user?.email
                        fullNameView.text = fullName
                        emailInfoView.text = user?.email
                        avatarText.text = firstLetter.toString()
                    }
                } else {
                    Log.e(TAG, "Failed to fetch profile: ${res.code()} ${res.message()}")
                    withContext(Dispatchers.Main) {
                        Toast.makeText(this@ProfileActivity, "Failed to fetch profile", Toast.LENGTH_SHORT).show()
                    }
                }
            } catch (e: Exception) {
                Log.e(TAG, "Network error", e)
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@ProfileActivity, "Network error: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }

        logoutBtn.setOnClickListener {
            prefs.edit().remove("token").remove("userId").remove("name").remove("email").apply()
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }
}
