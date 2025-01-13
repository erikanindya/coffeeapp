package com.example.coffee

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.coffee.R

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        // Cari tombol login berdasarkan ID
        val loginButton: Button = findViewById(R.id.login_btn)

        // Set OnClickListener untuk tombol login
        loginButton.setOnClickListener {
            // Buat Intent untuk berpindah ke MenuActivity
            val intent = Intent(this, MenuActivity::class.java)
            startActivity(intent) // Pindah ke MenuActivity
        }
    }
}