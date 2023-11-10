package com.example.nh_care.ui.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, dbUser, null, DATABASE_VERSION) {
    override fun onCreate(db: SQLiteDatabase) {
        val createTableQuery = "CREATE TABLE $TABLE_USERS ($COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT, $COLUMN_USERNAME TEXT, $COLUMN_PASSWORD TEXT)"
        db.execSQL(createTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        val dropTableQuery = "DROP TABLE IF EXISTS $TABLE_USERS"
        db.execSQL(dropTableQuery)
        onCreate(db)
    }
}