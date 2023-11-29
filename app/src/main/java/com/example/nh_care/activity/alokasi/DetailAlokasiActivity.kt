package com.example.nh_care.activity.alokasi

import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.nh_care.R
import com.example.nh_care.databinding.ActivityDetailAlokasiBinding

class DetailAlokasiActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailAlokasiBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailAlokasiBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        supportActionBar?.hide()

        val headingTujuan = binding.JudulAlokasi
        val mainTujuan = binding.DeskAlokasi
        val imageP = binding.imageAlokasi

        val bundle: Bundle? = intent.extras
        val judul = bundle!!.getString("judul")
        val deskripsi = bundle.getString("deskripsi")
        val imageAlokasiBlob = bundle.getByteArray("img_alokasi") // Ambil BLOB dari intent

        headingTujuan.text = judul
        mainTujuan.text = deskripsi

        if (imageAlokasiBlob != null) {
            // Jika Anda memiliki BLOB dari gambar
            val gambarBlob = BitmapFactory.decodeByteArray(imageAlokasiBlob, 0, imageAlokasiBlob.size)
            imageP.setImageBitmap(gambarBlob)
        } else {
            // Jika tidak ada gambar, tampilkan placeholder
            imageP.setImageResource(R.drawable.comingsoon)
        }
        binding.backalokasi.setOnClickListener() {
            startActivity(Intent(this, AlokasiActivity::class.java))
        }
    }

}
