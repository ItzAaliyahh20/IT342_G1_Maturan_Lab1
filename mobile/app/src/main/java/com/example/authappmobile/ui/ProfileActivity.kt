package com.example.authappmobile.ui

import android.content.Intent
import android.os.Bundle
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
    private lateinit var logoutBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        nameView = findViewById(R.id.name)
        emailView = findViewById(R.id.email)
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
                val res = RetrofitClient.api.profile("Bearer $token")
                if (res.isSuccessful) {
                    val user = res.body()
                    withContext(Dispatchers.Main) {
                        nameView.text = "${user?.first_name} ${user?.last_name}"
                        emailView.text = user?.email
                    }
                } else {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(this@ProfileActivity, "Failed to fetch profile", Toast.LENGTH_SHORT).show()
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@ProfileActivity, "Network error", Toast.LENGTH_SHORT).show()
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
