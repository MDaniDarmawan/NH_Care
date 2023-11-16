package com.example.nh_care.activity.login

import android.os.Bundle
import android.widget.Toast
import android.content.Intent
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.nh_care.R
import com.example.nh_care.activity.MainActivity
import com.example.nh_care.databinding.ActivityLoginBinding
import com.example.nh_care.database.DbContract
import com.example.nh_care.ui.beranda.BerandaFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {
    private lateinit var InputEmail: EditText
    private lateinit var InputPass: EditText
    private lateinit var loginButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        InputEmail = findViewById(R.id.et_email)
        InputPass = findViewById(R.id.et_kata_sandi)
        loginButton = findViewById(R.id.tombol_masuk)

        loginButton.setOnClickListener {
            val email = InputEmail.text.toString()
            val password = InputPass.text.toString()

            if (email == "user" && password == "1234") {
                // Login berhasil, alihkan ke aktivitas lain (misalnya, MainActivity)
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)

            } else {
                // Login gagal, berikan pesan kesalahan
                Toast.makeText(
                    this,
                    "Login gagal. Periksa username dan password.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}

//    private lateinit var binding: ActivityLoginBinding
//    private val etUsername: EditText by lazy { binding.etEmail }
//    private val etPassword: EditText by lazy { binding.etKataSandi }
//        override fun onCreate(savedInstanceState: Bundle?) {
//            super.onCreate(savedInstanceState)
//            binding = ActivityLoginBinding.inflate(layoutInflater)
//            setContentView(binding.root)
//            supportActionBar?.hide()
//
//            binding.tombolMasuk.setOnClickListener {
//                val username = etUsername.text.toString()
//                val password = etPassword.text.toString()
//
//                if (!(username.isEmpty() || password.isEmpty())) {
//                    loginUser(username, password)
//                } else {
//                    Toast.makeText(applicationContext, "Fields cannot be empty", Toast.LENGTH_SHORT).show()
//                }
//            }
//        }
//
//        private fun loginUser(username: String, password: String) {
//            val requestQueue: RequestQueue = Volley.newRequestQueue(applicationContext)
//
//            val stringRequest = StringRequest(
//                Request.Method.GET,
//                "${DbContract.urlLogin}?email=$username&password=$password",
//                { response ->
//                    Log.d("LoginActivity", "Server Response: $response")
//                    if (response == "welcome") {
//                        Log.d("LoginActivity", "Login Successful")
//                        Toast.makeText(applicationContext, "Login successful", Toast.LENGTH_SHORT).show()
//                        startActivity(Intent(applicationContext, MainActivity::class.java))
//                    } else {
//                        Log.d("LoginActivity", "Login Failed")
//                        Toast.makeText(applicationContext, "Login failed", Toast.LENGTH_SHORT).show()
//                    }
//                },
//                { error ->
//                    Log.e("LoginActivity", "Volley Error: ${error.message}")
//                    Toast.makeText(
//                        applicationContext,
//                        "Login Failed: ${error.message}",
//                        Toast.LENGTH_SHORT
//                    ).show()
//                }
//            )
//            requestQueue.add(stringRequest)
//        }
//    }