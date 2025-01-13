package com.example.coffee

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.coffee.R

class TemperatureConversionActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_temperature_conversion)

        // Menangani klik tombol kembali
        val btnBack = findViewById<androidx.appcompat.widget.AppCompatImageButton>(R.id.btn_back)
        btnBack.setOnClickListener {
            onBackPressed() // Kembali ke aktivitas sebelumnya
        }

        // Mendapatkan referensi elemen layout
        val etTemperature = findViewById<EditText>(R.id.et_temperature)
        val spinnerFromUnit = findViewById<Spinner>(R.id.spinner_from_unit)
        val spinnerToUnit = findViewById<Spinner>(R.id.spinner_to_unit)
        val btnConvertTemperature = findViewById<Button>(R.id.btn_convert_temperature)
        val tvConvertedTemperature = findViewById<TextView>(R.id.tv_converted_temperature)

        // Menambahkan aksi konversi suhu
        btnConvertTemperature.setOnClickListener {
            val temperature = etTemperature.text.toString().toDoubleOrNull()
            if (temperature != null) {
                val fromUnit = spinnerFromUnit.selectedItem.toString()
                val toUnit = spinnerToUnit.selectedItem.toString()
                val convertedTemperature = convertTemperature(temperature, fromUnit, toUnit)
                tvConvertedTemperature.text = "Converted Temperature: $convertedTemperature $toUnit"
            } else {
                Toast.makeText(this, "Please enter a valid temperature", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // Fungsi untuk konversi suhu
    private fun convertTemperature(value: Double, fromUnit: String, toUnit: String): Double {
        return when {
            fromUnit == "Celsius" && toUnit == "Fahrenheit" -> (value * 9 / 5) + 32
            fromUnit == "Celsius" && toUnit == "Kelvin" -> value + 273.15
            fromUnit == "Celsius" && toUnit == "Reamur" -> value * 4 / 5
            fromUnit == "Fahrenheit" && toUnit == "Celsius" -> (value - 32) * 5 / 9
            fromUnit == "Fahrenheit" && toUnit == "Kelvin" -> (value - 32) * 5 / 9 + 273.15
            fromUnit == "Fahrenheit" && toUnit == "Reamur" -> (value - 32) * 4 / 9
            fromUnit == "Kelvin" && toUnit == "Celsius" -> value - 273.15
            fromUnit == "Kelvin" && toUnit == "Fahrenheit" -> (value - 273.15) * 9 / 5 + 32
            fromUnit == "Kelvin" && toUnit == "Reamur" -> (value - 273.15) * 4 / 5
            fromUnit == "Reamur" && toUnit == "Celsius" -> value * 5 / 4
            fromUnit == "Reamur" && toUnit == "Fahrenheit" -> (value * 9 / 4) + 32
            fromUnit == "Reamur" && toUnit == "Kelvin" -> (value * 5 / 4) + 273.15
            else -> value // jika satuan dari dan ke sama, tidak perlu konversi
        }
    }
}
