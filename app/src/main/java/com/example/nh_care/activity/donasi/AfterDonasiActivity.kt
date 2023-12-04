package com.example.nh_care.activity.donasi

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.nh_care.activity.MainActivity
import com.example.nh_care.databinding.ActivityAfterDonasiBinding

class AfterDonasiActivity : AppCompatActivity(){
    private lateinit var binding: ActivityAfterDonasiBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAfterDonasiBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.kembaliBeranda.setOnClickListener {
            val intent = Intent (this@AfterDonasiActivity, MainActivity::class.java)
            startActivity(intent)
        }
    }
}