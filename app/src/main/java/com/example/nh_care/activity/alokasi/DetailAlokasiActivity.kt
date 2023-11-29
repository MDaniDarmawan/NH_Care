package com.example.nh_care.activity.alokasi

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.nh_care.databinding.ActivityDetailAlokasiBinding

class DetailAlokasiActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailAlokasiBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailAlokasiBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Get data passed from AlokasiActivity
        val title = intent.getStringExtra("title")
        val description = intent.getStringExtra("description")
        val avatarResourceId = intent.getIntExtra("avatarResourceId", 0)
        val totalDonasi = intent.getStringExtra("totalDonasi")
        binding.nominalSantunan.text = totalDonasi


        // Set data to views
        binding.imageAlokasi.setImageResource(avatarResourceId)
        binding.txtAlokasi.text = title
        binding.DeskAlokasi.text = description

        // Set up back button click listener
        binding.btnbackdetailalokasi.setOnClickListener {
            onBackPressed() // Go back to the previous screen
        }
    }
}

