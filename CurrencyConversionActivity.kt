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

class CurrencyConversionActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_currency_conversion)

        // Menangani klik tombol kembali
        val btnBack = findViewById<androidx.appcompat.widget.AppCompatImageButton>(R.id.btn_back)
        btnBack.setOnClickListener {
            onBackPressed() // Kembali ke aktivitas sebelumnya
        }

        // Mendapatkan referensi elemen layout
        val etAmount = findViewById<EditText>(R.id.et_amount)
        val spinnerFromCurrency = findViewById<Spinner>(R.id.spinner_from_currency)
        val spinnerToCurrency = findViewById<Spinner>(R.id.spinner_to_currency)
        val btnConvertCurrency = findViewById<Button>(R.id.btn_convert_currency)
        val tvConvertedCurrency = findViewById<TextView>(R.id.tv_converted_currency)

        // Menambahkan data mata uang ke spinner
        val currencies = arrayOf("USD", "IDR", "SAR", "JPY", "KRW")
        val adapterFrom = ArrayAdapter(this, android.R.layout.simple_spinner_item, currencies)
        adapterFrom.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerFromCurrency.adapter = adapterFrom

        val adapterTo = ArrayAdapter(this, android.R.layout.simple_spinner_item, currencies)
        adapterTo.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerToCurrency.adapter = adapterTo

        // Menambahkan aksi konversi mata uang
        btnConvertCurrency.setOnClickListener {
            val amount = etAmount.text.toString().toDoubleOrNull()
            if (amount != null) {
                val fromCurrency = spinnerFromCurrency.selectedItem.toString()
                val toCurrency = spinnerToCurrency.selectedItem.toString()
                val convertedAmount = convertCurrency(amount, fromCurrency, toCurrency)
                tvConvertedCurrency.text = "Converted Amount: $convertedAmount $toCurrency"
            } else {
                Toast.makeText(this, "Please enter a valid amount", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // Fungsi untuk konversi mata uang
    private fun convertCurrency(amount: Double, fromCurrency: String, toCurrency: String): Double {
        // Nilai tukar mata uang yang diperbarui (per 1 USD sebagai acuan)
        val exchangeRates = mapOf(
            "USD" to 1.0,        // Dolar Amerika
            "IDR" to 14150.0,    // Rupiah Indonesia
            "SAR" to 3.75,       // Riyal Saudi
            "JPY" to 135.0,      // Yen Jepang
            "KRW" to 1330.0      // Won Korea
        )

        val fromRate = exchangeRates[fromCurrency] ?: 1.0
        val toRate = exchangeRates[toCurrency] ?: 1.0

        return amount * (toRate / fromRate)
    }
}
