package com.example.nh_care.activity.login

import android.os.Bundle
import android.widget.Toast
import android.content.Intent
import android.util.Log
import androidx.activity.ComponentActivity
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.nh_care.activity.MainActivity
import com.example.nh_care.activity.register.RegisterActivity
import com.example.nh_care.databinding.ActivityLoginBinding

class LoginActivity : ComponentActivity() {
    private lateinit var binding: ActivityLoginBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.daftarakun.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        val url = "http://192.168.1.12/api-mysql-main/api-login.php" //

        binding.tombolMasuk.setOnClickListener {
            val request: RequestQueue = Volley.newRequestQueue(applicationContext)

            val stringRequest = StringRequest(
                Request.Method.GET,
                "$url?email=${binding.etEmail.text}&password=${binding.etKataSandi.text}",
                { response ->
                    if (response == "welcome") {
                        val intent = Intent(this, MainActivity::class.java)
                        binding.etEmail.text.toString()
                        startActivity(intent)
                    } else {
                        Toast.makeText(applicationContext, "Gagal login", Toast.LENGTH_LONG).show()
                    }
                },
                { error ->
                    Log.d("errorApp", error.toString())
                }
            )
            request.add(stringRequest)
        }
    }
}