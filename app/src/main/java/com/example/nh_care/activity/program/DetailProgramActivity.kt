package com.example.nh_care.activity.program

import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.nh_care.R
import com.example.nh_care.databinding.ActivityDetailProgramBinding

class DetailProgramActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailProgramBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailProgramBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        supportActionBar?.hide()

        val headingTujuan = binding.inputJudul
        val mainTujuan = binding.inputDeskripsi
        val imageP = binding.imageProgram

        val bundle: Bundle? = intent.extras
        val judul = bundle!!.getString("judul")
        val deskripsi = bundle.getString("deskripsi")
        val imageProgramBlob = bundle.getByteArray("img_program") // Ambil BLOB dari intent

        headingTujuan.text = judul
        mainTujuan.text = deskripsi

        if (imageProgramBlob != null) {
            // Jika Anda memiliki BLOB dari gambar
            val gambarBlob = BitmapFactory.decodeByteArray(imageProgramBlob, 0, imageProgramBlob.size)
            imageP.setImageBitmap(gambarBlob)
        } else {
            // Jika tidak ada gambar, tampilkan placeholder
            imageP.setImageResource(R.drawable.comingsoon)
        }
        binding.btnbackdetailprogram.setOnClickListener() {
            startActivity(Intent(this, ProgramActivity::class.java))
        }
    }

}
