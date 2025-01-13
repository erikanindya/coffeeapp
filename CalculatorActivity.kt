package com.example.coffee

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageButton
import com.example.coffee.R

class CalculatorActivity : AppCompatActivity() {

    private lateinit var workingsTV: TextView
    private lateinit var resultsTV: TextView
    private lateinit var backButton: AppCompatImageButton

    private var canAddOperation = false
    private var canAddDecimal = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calculator)

        // Inisialisasi komponen UI
        workingsTV = findViewById(R.id.workingsTV)
        resultsTV = findViewById(R.id.resultsTV)

        // Tombol Kembali
        backButton = findViewById(R.id.btn_back)
        backButton.setOnClickListener {
            onBackPressed() // Aksi untuk kembali ke activity sebelumnya
        }

        // Setel aksi untuk tombol kalkulator dalam GridLayout
        setGridButtonActions()
    }

    // Menangani aksi tombol angka dan titik desimal
    fun numberAction(view: View) {
        if (view is Button) { // pastikan view adalah Button
            if (view.text == ".") {
                if (canAddDecimal)
                    workingsTV.append(view.text)
                canAddDecimal = false
            } else {
                workingsTV.append(view.text)
            }
            canAddOperation = true
        }
    }

    // Menangani aksi tombol operasi (+, -, *, /)
    fun operationAction(view: View) {
        if (view is Button && canAddOperation) { // pastikan view adalah Button
            workingsTV.append(view.text)
            canAddOperation = false
            canAddDecimal = true
        }
    }

    // Menangani aksi tombol "C" (Clear)
    fun allClearAction(view: View) {
        workingsTV.text = ""
        resultsTV.text = ""
    }

    // Menangani aksi tombol backspace
    fun backSpaceAction(view: View) {
        val length = workingsTV.length()
        if (length > 0) {
            workingsTV.text = workingsTV.text.subSequence(0, length - 1)
        }
    }

    // Menangani aksi tombol "=" (equals)
    fun equalsAction(view: View) {
        try {
            resultsTV.text = calculateResults()
        } catch (e: Exception) {
            resultsTV.text = "Error"  // Menampilkan pesan error jika terjadi exception
            e.printStackTrace()
        }
    }

    // Fungsi untuk menghitung hasil kalkulasi
    private fun calculateResults(): String {
        val digitsOperators = digitsOperators()
        if (digitsOperators.isEmpty()) return ""

        val timesDivision = timesDivisionCalculate(digitsOperators)
        if (timesDivision.isEmpty()) return ""

        val result = addSubtractCalculate(timesDivision)
        return result.toString()
    }

    // Fungsi untuk menghitung operasi penjumlahan dan pengurangan
    private fun addSubtractCalculate(passedList: MutableList<Any>): Float {
        var result = passedList[0] as Float

        for (i in passedList.indices) {
            if (passedList[i] is Char && i != passedList.lastIndex) {
                val operator = passedList[i]
                val nextDigit = passedList[i + 1] as Float
                when (operator) {
                    '+' -> result += nextDigit
                    '-' -> result -= nextDigit
                }
            }
        }

        return result
    }

    // Fungsi untuk menghitung operasi perkalian dan pembagian
    private fun timesDivisionCalculate(passedList: MutableList<Any>): MutableList<Any> {
        var list = passedList
        while (list.contains('x') || list.contains('/')) {
            list = calcTimesDiv(list)
        }
        return list
    }

    // Fungsi untuk menghitung perkalian dan pembagian
    private fun calcTimesDiv(passedList: MutableList<Any>): MutableList<Any> {
        val newList = mutableListOf<Any>()
        var restartIndex = passedList.size

        for (i in passedList.indices) {
            if (passedList[i] is Char && i != passedList.lastIndex && i < restartIndex) {
                val operator = passedList[i]
                val prevDigit = passedList[i - 1] as Float
                val nextDigit = passedList[i + 1] as Float
                when (operator) {
                    'x' -> {
                        newList.add(prevDigit * nextDigit)
                        restartIndex = i + 1
                    }
                    '/' -> {
                        if (nextDigit == 0f) {
                            throw ArithmeticException("Cannot divide by zero")
                        }
                        newList.add(prevDigit / nextDigit)
                        restartIndex = i + 1
                    }
                    else -> {
                        newList.add(prevDigit)
                        newList.add(operator)
                    }
                }
            }

            if (i > restartIndex)
                newList.add(passedList[i])
        }

        return newList
    }

    // Fungsi untuk memisahkan angka dan operator
    private fun digitsOperators(): MutableList<Any> {
        val list = mutableListOf<Any>()
        var currentDigit = ""
        for (character in workingsTV.text) {
            if (character.isDigit() || character == '.') {
                currentDigit += character
            } else {
                if (currentDigit.isNotEmpty()) {
                    list.add(currentDigit.toFloat())
                    currentDigit = ""
                }
                list.add(character)
            }
        }

        if (currentDigit.isNotEmpty())
            list.add(currentDigit.toFloat())

        return list
    }

    // Setel aksi untuk tombol kalkulator
    private fun setGridButtonActions() {
        // Tombol angka dan titik desimal
        val numberButtons = listOf(
            findViewById<Button>(R.id.btn_one),
            findViewById<Button>(R.id.btn_two),
            findViewById<Button>(R.id.btn_three),
            findViewById<Button>(R.id.btn_four),
            findViewById<Button>(R.id.btn_five),
            findViewById<Button>(R.id.btn_six),
            findViewById<Button>(R.id.btn_seven),
            findViewById<Button>(R.id.btn_eight),
            findViewById<Button>(R.id.btn_nine),
            findViewById<Button>(R.id.btn_zero),
            findViewById<Button>(R.id.btn_decimal)
        )
        numberButtons.forEach { it.setOnClickListener { view -> numberAction(view) } }

        // Tombol operasi
        val operationButtons = listOf(
            findViewById<Button>(R.id.btn_add),
            findViewById<Button>(R.id.btn_subtract),
            findViewById<Button>(R.id.btn_multiply),
            findViewById<Button>(R.id.btn_divide)
        )
        operationButtons.forEach { it.setOnClickListener { view -> operationAction(view) } }

        // Tombol C (Clear)
        findViewById<Button>(R.id.btn_clear).setOnClickListener { view -> allClearAction(view) }

        // Tombol Backspace
        findViewById<Button>(R.id.btn_backspace).setOnClickListener { view -> backSpaceAction(view) }

        // Tombol "=" (Equals)
        findViewById<Button>(R.id.btn_equals).setOnClickListener { view -> equalsAction(view) }
    }
}
