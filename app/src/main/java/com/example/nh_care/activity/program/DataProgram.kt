package com.example.nh_care.activity.program

import android.graphics.Bitmap

// Mengubah tipe image_program di DataProgram menjadi String
data class DataProgram(
    val Judul: String,
    val Deskripsi: String,
    val img_program: Bitmap
)