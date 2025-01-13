package com.example.coffee

import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.SimpleCursorAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.coffee.R
import com.example.coffee.DBHelper

class OrderFragment : Fragment() {

    private lateinit var dbHelper: DBHelper
    private lateinit var database: SQLiteDatabase
    private lateinit var nameEditText: EditText
    private lateinit var phoneEditText: EditText
    private lateinit var addButton: Button
    private lateinit var updateButton: Button
    private lateinit var deleteButton: Button
    private lateinit var listView: ListView
    private var selectedContactId: Long? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_order, container, false)

        // Inisialisasi komponen UI
        nameEditText = rootView.findViewById(R.id.nameEditText)
        phoneEditText = rootView.findViewById(R.id.phoneEditText)
        addButton = rootView.findViewById(R.id.addButton)
        updateButton = rootView.findViewById(R.id.updateButton)
        deleteButton = rootView.findViewById(R.id.deleteButton)
        listView = rootView.findViewById(R.id.listView)

        // Inisialisasi DBHelper dan database
        dbHelper = DBHelper(requireContext())
        database = dbHelper.writableDatabase

        // Menambahkan kontak baru
        addButton.setOnClickListener {
            addContact()
            displayContacts()
        }

        // Memperbarui kontak yang dipilih
        updateButton.setOnClickListener {
            updateContact()
            displayContacts()
        }

        // Menghapus kontak yang dipilih
        deleteButton.setOnClickListener {
            deleteContact()
            displayContacts()
        }

        // Menampilkan kontak di ListView
        displayContacts()

        // Menangani klik pada item ListView
        listView.setOnItemClickListener { _, _, position, id ->
            selectContact(id)
        }

        return rootView
    }

    private fun addContact() {
        val name = nameEditText.text.toString().trim()
        val phone = phoneEditText.text.toString().trim()

        if (name.isNotEmpty() && phone.isNotEmpty()) {
            val values = ContentValues().apply {
                put("name", name)
                put("phone", phone)
            }

            database.insert("contacts", null, values)
            nameEditText.text.clear()
            phoneEditText.text.clear()
        } else {
            nameEditText.error = "Name is required"
            phoneEditText.error = "Phone is required"
        }
    }

    private fun updateContact() {
        val name = nameEditText.text.toString().trim()
        val phone = phoneEditText.text.toString().trim()

        if (selectedContactId != null && name.isNotEmpty() && phone.isNotEmpty()) {
            val values = ContentValues().apply {
                put("name", name)
                put("phone", phone)
            }

            database.update("contacts", values, "_id=?", arrayOf(selectedContactId.toString()))
            nameEditText.text.clear()
            phoneEditText.text.clear()
            selectedContactId = null
        } else {
            Toast.makeText(requireContext(), "Select a contact and fill all fields", Toast.LENGTH_SHORT).show()
        }
    }

    private fun deleteContact() {
        if (selectedContactId != null) {
            database.delete("contacts", "_id=?", arrayOf(selectedContactId.toString()))
            nameEditText.text.clear()
            phoneEditText.text.clear()
            selectedContactId = null
        } else {
            Toast.makeText(requireContext(), "Select a contact to delete", Toast.LENGTH_SHORT).show()
        }
    }

    private fun selectContact(id: Long) {
        val cursor: Cursor = database.query(
            "contacts", arrayOf("_id", "name", "phone"),
            "_id=?", arrayOf(id.toString()),
            null, null, null
        )

        if (cursor.moveToFirst()) {
            selectedContactId = id
            nameEditText.setText(cursor.getString(cursor.getColumnIndexOrThrow("name")))
            phoneEditText.setText(cursor.getString(cursor.getColumnIndexOrThrow("phone")))
        }
        cursor.close()
    }

    private fun displayContacts() {
        val cursor: Cursor = database.query(
            "contacts", arrayOf("_id", "name", "phone"),
            null, null, null, null, "name ASC"
        )

        val from = arrayOf("name", "phone")
        val to = intArrayOf(android.R.id.text1, android.R.id.text2)

        val adapter = SimpleCursorAdapter(
            requireContext(),
            android.R.layout.simple_list_item_2,
            cursor, from, to, 0
        )

        listView.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        database.close()
    }
}