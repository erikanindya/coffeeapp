package com.example.coffee

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.coffee.R

class WeightConversionActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weight_conversion)

        // Menangani klik tombol kembali
        val btnBack = findViewById<androidx.appcompat.widget.AppCompatImageButton>(R.id.btn_back)
        btnBack.setOnClickListener {
            onBackPressed() // Kembali ke aktivitas sebelumnya
        }

        // Mendapatkan referensi elemen layout
        val etWeight = findViewById<EditText>(R.id.et_weight)
        val spinnerFromWeightUnit = findViewById<Spinner>(R.id.spinner_from_weight_unit)
        val spinnerToWeightUnit = findViewById<Spinner>(R.id.spinner_to_weight_unit)
        val btnConvertWeight = findViewById<Button>(R.id.btn_convert_weight)
        val tvConvertedWeight = findViewById<TextView>(R.id.tv_converted_weight)

        // Menyiapkan adapter untuk spinner
        val adapter = ArrayAdapter.createFromResource(
            this,
            R.array.weight_units,
            android.R.layout.simple_spinner_item
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        // Mengatur adapter untuk kedua spinner
        spinnerFromWeightUnit.adapter = adapter
        spinnerToWeightUnit.adapter = adapter

        // Menambahkan aksi konversi berat
        btnConvertWeight.setOnClickListener {
            val weight = etWeight.text.toString().toDoubleOrNull()
            if (weight != null) {
                val fromUnit = spinnerFromWeightUnit.selectedItem.toString()
                val toUnit = spinnerToWeightUnit.selectedItem.toString()
                val convertedWeight = convertWeight(weight, fromUnit, toUnit)
                tvConvertedWeight.text = "Converted Weight: $convertedWeight $toUnit"
            } else {
                Toast.makeText(this, "Please enter a valid weight", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // Fungsi untuk konversi berat
    private fun convertWeight(value: Double, fromUnit: String, toUnit: String): Double {
        return when {
            fromUnit == "Kilograms" && toUnit == "Pounds" -> value * 2.20462
            fromUnit == "Kilograms" && toUnit == "Ounces" -> value * 35.274
            fromUnit == "Kilograms" && toUnit == "Grams" -> value * 1000.0
            fromUnit == "Pounds" && toUnit == "Kilograms" -> value / 2.20462
            fromUnit == "Pounds" && toUnit == "Ounces" -> value * 16
            fromUnit == "Pounds" && toUnit == "Grams" -> value * 453.592
            fromUnit == "Ounces" && toUnit == "Kilograms" -> value / 35.274
            fromUnit == "Ounces" && toUnit == "Pounds" -> value / 16
            fromUnit == "Ounces" && toUnit == "Grams" -> value * 28.3495
            fromUnit == "Grams" && toUnit == "Kilograms" -> value / 1000.0
            fromUnit == "Grams" && toUnit == "Pounds" -> value / 453.592
            fromUnit == "Grams" && toUnit == "Ounces" -> value / 28.3495
            else -> value // jika satuan dari dan ke sama, tidak perlu konversi
        }
    }
}
