package com.example.nh_care.activity.donasi.riwayat

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.nh_care.databinding.ActivityDetailDonasiBinding

class DetailRiwayatDonasi : AppCompatActivity() {

    private lateinit var binding: ActivityDetailDonasiBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailDonasiBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        supportActionBar?.hide()

        val idorder = binding.kotakOrderId
        val namaD = binding.kotakNamaDonatur
        val headingTujuan = binding.kotakKeterangan
        val mainTujuan = binding.kotakGrossamount
        val date = binding.kotakTanggal

        val bundle: Bundle? = intent.extras
        val orderID = bundle?.getString("orderID")
        val namaDon = bundle?.getString("namaDon")
        val judul = bundle?.getString("judul")
        val jumlah = bundle?.getString("jumlah")
        val tanggal = bundle?.getString("tanggal")

        idorder.text = orderID
        namaD.text = namaDon
        headingTujuan.text = judul
        mainTujuan.text = jumlah
        date.text = tanggal

        binding.btnbackdetaildonasi.setOnClickListener() {
            startActivity(Intent(this, RiwayatDonasiActivity::class.java))
        }
    }
}