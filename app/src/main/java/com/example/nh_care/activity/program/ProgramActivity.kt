package com.example.nh_care.activity.program

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.nh_care.R

class ProgramActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_program)
        supportActionBar?.hide()
    }
}