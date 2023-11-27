package com.example.nh_care.activity.alokasi

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.berandaberanda.activitity.alokasi.AlokasiAdapter
import com.example.berandaberanda.activitity.alokasi.DataItem
import com.example.nh_care.activity.MainActivity
import com.example.nh_care.R
import com.example.nh_care.databinding.ActivityAlokasiBinding

class AlokasiActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAlokasiBinding
    private lateinit var mList: ArrayList<DataItem>
    private lateinit var searchView: SearchView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAlokasiBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        binding.recyclerView.setHasFixedSize(true)
        binding.recyclerView.layoutManager = LinearLayoutManager(this)

        mList = ArrayList()
        prepareData()

        val adapter = AlokasiAdapter(mList)
        binding.recyclerView.adapter = adapter


    }

    private fun prepareData() {

        mList.add(
            DataItem(
                "Pembangunan dan Fasilitas",
                "Alokasi dana untuk pembangunan fasilitas panti asuhan adalah upaya menyediakan dana guna membangun atau meningkatkan infrastruktur panti asuhan. Dana ini digunakan untuk memperbaiki, membangun, atau memperluas fasilitas seperti ruang tidur, kamar mandi, ruang belajar, dan area rekreasi. Sasaran alokasi dana ini adalah menciptakan lingkungan yang nyaman dan aman bagi anak-anak di panti asuhan, memastikan mereka memiliki tempat yang layak untuk tinggal dan belajar.",
                R.drawable.beranda,
                null
            )
        )
        mList.add(
            DataItem(
                "Santunan Anak Yatim dan Piatu",
                "Alokasi dana untuk santunan anak yatim piatu berfokus pada memberikan dukungan finansial langsung kepada anak-anak yang tinggal di panti asuhan. Dana ini digunakan untuk memenuhi kebutuhan dasar seperti pendidikan, pangan, pakaian, dan kebutuhan keseharian lainnya. Sasaran alokasi dana ini adalah memberikan bantuan praktis dan nyata kepada anak-anak yatim piatu, memastikan bahwa mereka mendapatkan dukungan untuk menghadapi kehidupan sehari-hari dan memiliki akses ke fasilitas yang mendukung perkembangan mereka secara holistik.",
                R.drawable.beranda,
                null
            )
        )

        mList.add(
            DataItem(
                "Coming Soon",
                null,
                null,
                R.drawable.comingsoon
            )
        )

        mList.add(
            DataItem(
                "Coming Soon",
                null,
                null,
                R.drawable.comingsoon
            )
        )


        mList.add(
            DataItem(
                "Administrator",
                null,
                null,
                null
            )
        )

        binding.backalokasi.setOnClickListener() {
            startActivity(Intent(this, MainActivity::class.java))
        }
    }
}
