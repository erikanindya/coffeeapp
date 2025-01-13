package com.example.coffee;

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.example.coffee.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Delay selama 3 detik sebelum berpindah ke HomeActivity
        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this@MainActivity, HomeActivity::class.java)
            startActivity(intent)
            finish() // Tutup Splash Screen agar tidak dapat kembali dengan tombol Back
        }, 3000) // 3000 ms = 3 detik
    }
}
