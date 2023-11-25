package com.example.nh_care.activity.anakasuh

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.nh_care.activity.MainActivity
import com.example.nh_care.R
import com.example.nh_care.activity.anakasuh.DataAnak
import com.example.nh_care.databinding.ActivityDetailAnakasuhBinding

class DetailAnakActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailAnakasuhBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailAnakasuhBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        supportActionBar?.hide()

        val headingNama = binding.inputNamaAnak
        val detailKelas = binding.inputKelasAnak
        val detailSekolah = binding.inputSekolahAnak
        val detailTentang = binding.inputTentang
        val imageAnak = binding.imageAnak

        val bundle: Bundle? = intent.extras
        val nama = bundle?.getString("nama")
        val kelas = bundle?.getString("kelas")
        val sekolah = bundle?.getString("nama_sekolah")
        val tentang = bundle?.getString("keterangan")
        val imgAnak = bundle?.getParcelable<Bitmap>("img_anak")

        headingNama.text = nama
        detailKelas.text = kelas
        detailSekolah.text = sekolah
        detailTentang.text = tentang

        if (imgAnak != null) {
            imageAnak.setImageBitmap(imgAnak)
        } else {
            imageAnak.setImageResource(R.drawable.comingsoon)
        }


        imageAnak.setOnClickListener {
            val intent = Intent(this, DetailAnakActivity::class.java)
            val bundle: Bundle? = intent.extras
            // Tambahkan data anak ke intent DetailAnakActivity
            if (bundle != null) {
                intent.putExtras(bundle)
            }
            startActivity(intent)
        }


//        binding.btnBackAnak.setOnClickListener() {
//            startActivity(Intent(this, MainActivity::class.java))
//        }
    }
}

