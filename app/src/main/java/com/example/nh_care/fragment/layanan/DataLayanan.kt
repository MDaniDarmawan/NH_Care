package com.example.nh_care.fragment.layanan

data class DataLayanan (
    val Pertanyaan: String,
    val Jawaban: String,
    var isExpandable: Boolean = false // Tambahkan properti isExpandable
)
