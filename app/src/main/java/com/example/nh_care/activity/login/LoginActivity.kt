package com.example.nh_care.activity.login

import android.os.Bundle
import android.widget.Toast
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.nh_care.activity.MainActivity
import com.example.nh_care.databinding.ActivityLoginBinding
import com.example.nh_care.database.DbContract
import com.example.nh_care.activity.register.RegisterActivity

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var api: DbContract


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize the db property
        api = DbContract() // or initialize it in a way that suits your code

        binding.pertanyaanDaftar.setOnClickListener {
            startActivity(Intent(applicationContext, RegisterActivity::class.java))
        }

        binding.tombolMasuk.setOnClickListener {
            val email = binding.etEmail.text.toString()
            val password = binding.etKataSandi.text.toString()

            if (!(email.isEmpty() || password.isEmpty())) {

                val requestQueue: RequestQueue = Volley.newRequestQueue(applicationContext)

                val stringRequest = StringRequest(
                    Request.Method.GET,
                    api.urlLogin + "?email=$email&password=$password",
                    { response ->
                        if (response == "welcome") {
                            Toast.makeText(applicationContext, "Login Berhasil", Toast.LENGTH_SHORT)
                                .show()
                            startActivity(Intent(applicationContext, MainActivity::class.java))
                        } else {
                            Toast.makeText(applicationContext, "Login Gagal", Toast.LENGTH_SHORT)
                                .show()
                        }
                    },
                    { error ->
                        Toast.makeText(applicationContext, error.toString(), Toast.LENGTH_SHORT)
                            .show()
                    }
                )
                requestQueue.add(stringRequest)
            } else {
                Toast.makeText(
                    applicationContext,
                    "Password Atau Username Salah",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}