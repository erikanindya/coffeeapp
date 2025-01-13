package com.example.coffee

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelper(context: Context) : SQLiteOpenHelper(context, "PhoneBookDB", null, 1) {

    override fun onCreate(db: SQLiteDatabase?) {
        val createTableQuery = """
            CREATE TABLE contacts (
                _id INTEGER PRIMARY KEY AUTOINCREMENT,
                name TEXT,
                phone TEXT
            );
        """
        db?.execSQL(createTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS contacts")
        onCreate(db)
    }
}
