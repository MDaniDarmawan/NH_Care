package com.example.nh_care.activity.login

import android.os.Bundle
import android.widget.Toast
import android.content.Intent
import android.content.SharedPreferences
import android.util.Log
import androidx.activity.ComponentActivity
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.nh_care.activity.MainActivity
import com.example.nh_care.activity.register.RegisterActivity
import com.example.nh_care.databinding.ActivityLoginBinding
import org.json.JSONException
import org.json.JSONObject
import java.net.URLEncoder

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

        val url = "http://192.168.1.70/api-mysql-main/api-login.php"

        binding.tombolMasuk.setOnClickListener {
            val request: RequestQueue = Volley.newRequestQueue(applicationContext)

            val stringRequest = StringRequest(
                Request.Method.GET,
                "$url?email=${URLEncoder.encode(binding.etEmail.text.toString(), "UTF-8")}&password=${URLEncoder.encode(binding.etKataSandi.text.toString(), "UTF-8")}",
                { response ->
                    try {
                        val jsonResponse = JSONObject(response)
                        val status = jsonResponse.getString("status")
                        if (status == "success") {
                            val idDonatur = jsonResponse.getString("id_donatur")
                            saveID(idDonatur)

                            val intent = Intent(this, MainActivity::class.java)
                            startActivity(intent)
                        } else {
                            Toast.makeText(applicationContext, "Gagal login", Toast.LENGTH_LONG).show()
                        }
                    } catch (e: JSONException) {
                        Log.e("JSONError", "Error parsing JSON", e)
                    }
                },
                { error ->
                    Log.d("errorApp", error.toString())
                }
            )
            request.add(stringRequest)
        }
    }

    private fun saveID(idDonatur: String) {
        val preferences: SharedPreferences = getSharedPreferences("donatur_prefs", MODE_PRIVATE)
        val editor: SharedPreferences.Editor = preferences.edit()
        editor.putString("id_donatur", idDonatur)
        editor.apply()
    }
}
