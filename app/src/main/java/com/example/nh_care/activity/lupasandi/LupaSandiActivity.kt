package com.example.nh_care.activity.lupasandi

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.nh_care.R
import com.example.nh_care.activity.login.LoginActivity
import com.example.nh_care.database.DbContract
import org.json.JSONException
import org.json.JSONObject

class LupaSandiActivity : AppCompatActivity() {
    private lateinit var btnCekKataSandi: Button
    private lateinit var nama: EditText
    private lateinit var noHp: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.lupa_password)

        btnCekKataSandi = findViewById(R.id.cekkatasandi)
        nama = findViewById(R.id.inputUsername)
        noHp = findViewById(R.id.inputNoHp)

        fetchDataForAPI()
    }

    private fun fetchDataForAPI() {
        val urllupaSandi = DbContract.urlKataSandi

        btnCekKataSandi.setOnClickListener {
            val inputNama = nama.text.toString()
            val inputNoHp = noHp.text.toString()

            Log.d("DataKirim", "Nama: $inputNama, No HP: $inputNoHp")

            val request = object : StringRequest(Method.POST, urllupaSandi,
                Response.Listener { response ->
                    try {
                        val jsonObject = JSONObject(response)
                        val success = jsonObject.getBoolean("success")
                        val message = jsonObject.getString("message")

                        if (success) {
                            val successMessage = "Email Berhasil dikirim! Silakan cek Email Anda."
                            showToast(successMessage)
                            moveToLoginActivity()
                        } else {
                            showToast(message)
                        }
                    } catch (e: JSONException) {
                        e.printStackTrace()
                        showToast("Terjadi kesalahan dalam pemrosesan data JSON.")
                    }
                },
                Response.ErrorListener { error: VolleyError ->
                    val errorMessage = "Terjadi kesalahan: ${error.message}"
                    showToast(errorMessage)
                }
            ) {
                @Throws(AuthFailureError::class)
                override fun getParams(): Map<String, String> {
                    val params: MutableMap<String, String> = HashMap()
                    params["nama"] = inputNama
                    params["no_hp"] = inputNoHp
                    return params
                }
            }

            Volley.newRequestQueue(this@LupaSandiActivity).add(request)
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(applicationContext, message, Toast.LENGTH_LONG).show()
    }

    private fun moveToLoginActivity() {
        val intent = Intent(this@LupaSandiActivity, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }
}
