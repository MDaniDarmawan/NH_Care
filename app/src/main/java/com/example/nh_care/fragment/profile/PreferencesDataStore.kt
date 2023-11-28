package com.example.nh_care.fragment.profile

import android.content.Context
import android.content.SharedPreferences

class PreferencesDataStore(context: Context) {
    private val sharedPreferences: SharedPreferences = context.getSharedPreferences("user", Context.MODE_PRIVATE)

    // Fungsi untuk menyimpan nilai nama pengguna
    fun saveNama(value: String) {
        sharedPreferences.edit().putString("nama", value).apply()
    }

    // Fungsi untuk mendapatkan nilai nama pengguna
    fun getNama(): String? {
        return sharedPreferences.getString("nama", null)
    }

    // Fungsi untuk menyimpan nilai email pengguna
    fun saveEmail(value: String) {
        sharedPreferences.edit().putString("email", value).apply()
    }

    // Fungsi untuk mendapatkan nilai email pengguna
    fun getEmail(): String? {
        return sharedPreferences.getString("email", null)
    }

    // Fungsi untuk menyimpan nilai password pengguna
    fun savePassword(value: String) {
        sharedPreferences.edit().putString("password", value).apply()
    }

    // Fungsi untuk mendapatkan nilai password pengguna
    fun getPassword(): String? {
        return sharedPreferences.getString("password", null)
    }

    // Fungsi untuk menyimpan nilai no_hp pengguna
    fun saveNoHp(value: String) {
        sharedPreferences.edit().putString("no_hp", value).apply()
    }

    // Fungsi untuk mendapatkan nilai no_hp pengguna
    fun getNoHp(): String? {
        return sharedPreferences.getString("no_hp", null)
    }

    // Fungsi untuk menghapus semua nilai yang tersimpan
    fun eraseValues() {
        sharedPreferences.edit()
            .remove("nama")
            .remove("email")
            .remove("password")
            .remove("no_hp")
            .apply()
    }
}
