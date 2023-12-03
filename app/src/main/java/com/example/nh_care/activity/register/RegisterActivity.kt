package com.example.nh_care.activity.register

import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.KeyEvent
import android.view.View
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.nh_care.R
import com.example.nh_care.activity.MainActivity
import com.example.nh_care.activity.login.LoginActivity
import com.example.nh_care.database.DbContract
import com.example.nh_care.databinding.ActivityRegisterBinding
import com.google.android.material.textfield.TextInputLayout
import org.json.JSONException
import org.json.JSONObject

class RegisterActivity : AppCompatActivity(), View.OnFocusChangeListener,
    View.OnKeyListener {

    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnbacklogin.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        val registerUrl = "https://nhcare.tifc.myhost.id/nhcare/api/api-Nhcare.php?function=registerDonatur"

        binding.btndaftar.setOnClickListener {
            if (binding.etNamaLengkap.text.toString().isEmpty() || binding.etEmail.text.toString().isEmpty() || binding.etKataSandi.text.toString().isEmpty() || binding.etHp.text.toString().isEmpty()) {
                Toast.makeText(applicationContext, "Lengkapi data terlebih dahulu", Toast.LENGTH_LONG).show()
            } else {
                val request: RequestQueue = Volley.newRequestQueue(applicationContext)
                val strRequest = object : StringRequest(
                    Request.Method.POST,
                    registerUrl,
                    { response ->
                        try {
                            Log.d("JSONResponse", response)

                            val jsonResponse = JSONObject(response)
                            val status = jsonResponse.getString("status")
                            if (status == "success") {
                                // Inside your activity or fragment
                                Toast.makeText(applicationContext, "Registrasi berhasil", Toast.LENGTH_SHORT).show()
                                val intent = Intent(this, LoginActivity::class.java)
                                startActivity(intent)
                            } else {
                                Toast.makeText(applicationContext, response, Toast.LENGTH_LONG).show()
                            }
                        } catch (e: JSONException) {
                            e.printStackTrace()
                        }
                    },
                    { error ->
                        Log.d("ErrorApps", error.toString())
                        Toast.makeText(applicationContext, "An error occurred", Toast.LENGTH_LONG).show()
                    }
                ) {
                    override fun getParams(): MutableMap<String, String> {
                        val params = HashMap<String, String>()
                        params["nama"] = binding.etNamaLengkap.text.toString()
                        params["email"] = binding.etEmail.text.toString()
                        params["password"] = binding.etKataSandi.text.toString()
                        params["no_hp"] = binding.etHp.text.toString()
                        return params
                    }
                }
                request.add(strRequest)
            }
        }

        binding.etNamaLengkap.onFocusChangeListener = this
        binding.etEmail.onFocusChangeListener = this
        binding.etHp.onFocusChangeListener = this
        binding.etKataSandi.onFocusChangeListener = this
        binding.etKonfirmasiKataSandi.onFocusChangeListener = this

    }

    private fun showError(binding: TextInputLayout, errorMessage: String) {
        binding.apply {
            isErrorEnabled = true
            error = errorMessage
        }
    }

    private fun clearError(binding: TextInputLayout) {
        binding.isErrorEnabled = false
    }

    private fun validateFullName(): Boolean {
        val value = binding.etNamaLengkap.text.toString()
        if (value.isEmpty()) {
            showError(binding.tilNamaLengkap, "Nama lengkap diperlukan")
            return false
        }
        clearError(binding.tilNamaLengkap)
        return true
    }

    private fun validateEmail(): Boolean {
        val value = binding.etEmail.text.toString()
        if (value.isEmpty()) {
            showError(binding.tilEmail, "Email diperlukan")
            return false
        } else if (!Patterns.EMAIL_ADDRESS.matcher(value).matches()) {
            showError(binding.tilEmail, "Email tidak valid")
            return false
        }
        clearError(binding.tilEmail)
        return true
    }

    private fun validateHp(): Boolean {
        val value = binding.etHp.text.toString()
        if (value.isEmpty()) {
            showError(binding.tilHp, "Isi Nomor Hp terlebih dahulu")
            return false
        } else if (value.length < 11) {
            showError(binding.tilHp, "Nomor Hp tidak valid")
            return false
        }
        clearError(binding.tilHp)
        return true
    }

    private fun validatePassword(): Boolean {
        val value = binding.etKataSandi.text.toString()
        if (value.isEmpty()) {
            showError(binding.tilKataSandi, "Isi Password terlebih dahulu")
            return false
        } else if (value.length < 8) {
            showError(binding.tilKataSandi, "Password Minimal 8 Karakter")
            return false
        }
        clearError(binding.tilKataSandi)
        return true
    }

    private fun validateConfirmPassword(): Boolean {
        val value = binding.etKonfirmasiKataSandi.text.toString()
        if (value.isEmpty()) {
            showError(binding.tilKataSandi, "isi konfirmasi Password")
            return false
        } else if (value.length < 8) {
            showError(binding.tilKonfirmasiKataSandi, "Password konfirmasi Minimal 8 Karakter")
            return false
        }
        clearError(binding.tilKonfirmasiKataSandi)
        return true
    }

    private fun validatePasswordAndConfirmPassword(): Boolean {
        val password = binding.etKataSandi.text.toString()
        val confirmPassword = binding.etKonfirmasiKataSandi.text.toString()
        if (password != confirmPassword) {
            showError(binding.tilKonfirmasiKataSandi, "Password tidak sesuai")
            return false
        }
        clearError(binding.tilKonfirmasiKataSandi)
        return true
    }

    override fun onFocusChange(view: View?, hasFocus: Boolean) {
        if (view != null) {
            when (view.id) {
                R.id.et_nama_lengkap -> {
                    if (hasFocus && binding.tilNamaLengkap.isErrorEnabled) {
                        clearError(binding.tilNamaLengkap)
                    } else {
                        validateFullName()
                    }
                }

                R.id.et_email -> {
                    if (hasFocus && binding.tilEmail.isErrorEnabled) {
                        clearError(binding.tilEmail)
                    } else {
                        validateEmail()
                    }
                }

                R.id.et_hp -> {
                    if (hasFocus && binding.tilHp.isErrorEnabled) {
                        clearError(binding.tilHp)
                    } else {
                        validateHp()
                    }
                }

                R.id.et_kata_sandi -> {
                    if (hasFocus && binding.tilKataSandi.isErrorEnabled) {
                        clearError(binding.tilKataSandi)
                    } else {
                        if (validatePassword() && binding.etKonfirmasiKataSandi.text!!.isNotEmpty() &&
                            validateConfirmPassword() && validatePasswordAndConfirmPassword()
                        ) {
                            if (binding.tilKonfirmasiKataSandi.isErrorEnabled) {
                                clearError(binding.tilKonfirmasiKataSandi)
                            }
                            binding.tilKonfirmasiKataSandi.apply {
                                setStartIconDrawable(R.drawable.baseline_check_circle_24)
                                setStartIconTintList(ColorStateList.valueOf(Color.GREEN))
                            }
                        }
                    }
                }

                R.id.et_konfirmasi_kata_sandi -> {
                    if (hasFocus && binding.tilKonfirmasiKataSandi.isErrorEnabled) {
                        clearError(binding.tilKonfirmasiKataSandi)
                    } else {
                        if (validateConfirmPassword() && validatePassword() && validatePasswordAndConfirmPassword()) {
                            if (binding.tilKonfirmasiKataSandi.isErrorEnabled) {
                                clearError(binding.tilKonfirmasiKataSandi)
                            }
                            binding.tilKonfirmasiKataSandi.apply {
                                setStartIconDrawable(R.drawable.baseline_check_circle_24)
                                setStartIconTintList(ColorStateList.valueOf(Color.GREEN))
                            }
                        }
                    }
                }
            }
        }
    }

    override fun onKey(view: View?, event: Int, keyEvent: KeyEvent?): Boolean {
        // Handle key events, e.g., Enter key
        return false
    }
}
