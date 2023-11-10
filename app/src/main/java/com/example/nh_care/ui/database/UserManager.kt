package com.example.nh_care.ui.database

import android.content.ContentValues
import android.content.Context

class UserManager(private val context: Context) {
    private val dbHelper = DatabaseHelper(context)

    fun addUser(user: User) {
        val db = dbHelper.writableDatabase
        val values = ContentValues()
        values.put(COLUMN_USERNAME, user.username)
        values.put(COLUMN_PASSWORD, user.password)
        db.insert(TABLE_USERS, null, values)
        db.close()
    }

    fun getUserByUsername(username: String): User? {
        val db = dbHelper.readableDatabase
        val query = "SELECT * FROM $TABLE_USERS WHERE $COLUMN_USERNAME = ?"
        val cursor = db.rawQuery(query, arrayOf(username))
        var user: User? = null
        if (cursor.moveToFirst()) {
            user = User(
                cursor.getInt(cursor.getColumnIndex(COLUMN_ID)),
                cursor.getString(cursor.getColumnIndex(COLUMN_USERNAME)),
                cursor.getString(cursor.getColumnIndex(COLUMN_PASSWORD))
            )
        }
        cursor.close()
        db.close()
        return user
    }
}