//package com.example.nh_care.activity.register
//
//import android.content.res.ColorStateList
//import android.graphics.Color
//import androidx.appcompat.app.AppCompatActivity
//import android.os.Bundle
//import android.util.Patterns
//import android.view.KeyEvent
//import android.view.LayoutInflater
//import android.view.View
//import android.widget.Toast
//import com.android.volley.AuthFailureError
//import com.android.volley.Request
//import com.android.volley.Response
//import com.android.volley.toolbox.StringRequest
//import com.android.volley.toolbox.Volley
//import com.example.nh_care.R
//import com.example.nh_care.databinding.ActivityRegisterBinding
//import com.example.nh_care.database.DbContract
//import com.google.android.material.textfield.TextInputLayout
//
//class RegisterActivity : AppCompatActivity(), View.OnClickListener, View.OnFocusChangeListener,
//    View.OnKeyListener {
//
//    private lateinit var binding: ActivityRegisterBinding
//    private lateinit var api: DbContract
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        binding = ActivityRegisterBinding.inflate(LayoutInflater.from(this))
//        setContentView(binding.root)
//
//        api = DbContract()
//
//        binding.btndaftar.setOnClickListener {
//            val nama = binding.etNamaLengkap.text.toString()
//            val email = binding.etEmail.text.toString()
//            val password = binding.etKataSandi.text.toString()
//
//            if (nama.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty()) {
//
//                val stringRequest: StringRequest =
//                    object : StringRequest(
//                        Request.Method.POST,
//                        api.urlRegister,
//                        Response.Listener { response ->
//                            Toast.makeText(applicationContext, response, Toast.LENGTH_SHORT).show()
//                            //startActivity(Intent(applicationContext, LoginActivity::class.java))
//                        },
//                        Response.ErrorListener { error ->
//                            Toast.makeText(
//                                applicationContext,
//                                error.toString(),
//                                Toast.LENGTH_SHORT
//                            ).show()
//                        }
//                    ) {
//                        @Throws(AuthFailureError::class)
//                        override fun getParams(): Map<String, String> {
//                            return mapOf(
//                                "nama" to nama,
//                                "email" to email,
//                                "password" to password
//                            )
//                        }
//                    }
//                val requestQueue = Volley.newRequestQueue(applicationContext)
//                requestQueue.add(stringRequest)
//
//            } else {
//                Toast.makeText(
//                    applicationContext,
//                    "Ada Data Yang Masih Kosong",
//                    Toast.LENGTH_SHORT
//                ).show()
//            }
//        }
//
//        binding.etNamaLengkap.onFocusChangeListener = this
//        binding.etEmail.onFocusChangeListener = this
//        binding.etHp.onFocusChangeListener = this
//        binding.etKataSandi.onFocusChangeListener = this
//        binding.etKonfirmasiKataSandi.onFocusChangeListener = this
//
//    }
//
//    private fun showError(binding: TextInputLayout, errorMessage: String) {
//        binding.apply {
//            isErrorEnabled = true
//            error = errorMessage
//        }
//    }
//
//    private fun clearError(binding: TextInputLayout) {
//        binding.isErrorEnabled = false
//    }
//
//    private fun validateFullName(): Boolean {
//        val value = binding.etNamaLengkap.text.toString()
//        if (value.isEmpty()) {
//            showError(binding.tilNamaLengkap, "Nama lengkap diperlukan")
//            return false
//        }
//        clearError(binding.tilNamaLengkap)
//        return true
//    }
//
//    private fun validateEmail(): Boolean {
//        val value = binding.etEmail.text.toString()
//        if (value.isEmpty()) {
//            showError(binding.tilEmail, "Email diperlukan")
//            return false
//        } else if (!Patterns.EMAIL_ADDRESS.matcher(value).matches()) {
//            showError(binding.tilEmail, "Email tidak valid")
//            return false
//        }
//        clearError(binding.tilEmail)
//        return true
//    }
//
//    private fun validateHp(): Boolean {
//        val value = binding.etHp.text.toString()
//        if (value.isEmpty()) {
//            showError(binding.tilHp, "Isi Nomor Hp terlebih dahulu")
//            return false
//        } else if (value.length < 13) {
//            showError(binding.tilHp, "Nomor Hp Minimal 12 Angka")
//            return false
//        }
//        clearError(binding.tilHp)
//        return true
//    }
//
//    private fun validatePassword(): Boolean {
//        val value = binding.etKataSandi.text.toString()
//        if (value.isEmpty()) {
//            showError(binding.tilKataSandi, "Isi Password terlebih dahulu")
//            return false
//        } else if (value.length < 8) {
//            showError(binding.tilKataSandi, "Password Minimal 8 Karakter")
//            return false
//        }
//        clearError(binding.tilKataSandi)
//        return true
//    }
//
//    private fun validateConfirmPassword(): Boolean {
//        val value = binding.etKonfirmasiKataSandi.text.toString()
//        if (value.isEmpty()) {
//            showError(binding.tilKataSandi, "isi konfirmasi Password")
//            return false
//        } else if (value.length < 8) {
//            showError(binding.tilKonfirmasiKataSandi, "Password konfirmasi Minimal 8 Karakter")
//            return false
//        }
//        clearError(binding.tilKonfirmasiKataSandi)
//        return true
//    }
//
//    private fun validatePasswordAndConfirmPassword(): Boolean {
//        val password = binding.etKataSandi.text.toString()
//        val confirmPassword = binding.etKonfirmasiKataSandi.text.toString()
//        if (password != confirmPassword) {
//            showError(binding.tilKonfirmasiKataSandi, "Password tidak sesuai")
//            return false
//        }
//        clearError(binding.tilKonfirmasiKataSandi)
//        return true
//    }
//
//    override fun onClick(view: View?) {
//        // Handle button clicks or other UI interactions here
//    }
//    override fun onFocusChange(view: View?, hasFocus: Boolean) {
//        if (view != null) {
//            when (view.id) {
//                R.id.et_nama_lengkap -> {
//                    if (hasFocus && binding.tilNamaLengkap.isErrorEnabled) {
//                        clearError(binding.tilNamaLengkap)
//                    } else {
//                        validateFullName()
//                    }
//                }
//
//                R.id.et_email -> {
//                    if (hasFocus && binding.tilEmail.isErrorEnabled) {
//                        clearError(binding.tilEmail)
//                    } else {
//                        validateEmail()
//                    }
//                }
//
//                R.id.et_kata_sandi -> {
//                    if (hasFocus && binding.tilKataSandi.isErrorEnabled) {
//                        clearError(binding.tilKataSandi)
//                    } else {
//                        if (validatePassword() && binding.etKonfirmasiKataSandi.text!!.isNotEmpty() &&
//                            validateConfirmPassword() && validatePasswordAndConfirmPassword()
//                        ) {
//                            if (binding.tilKonfirmasiKataSandi.isErrorEnabled) {
//                                clearError(binding.tilKonfirmasiKataSandi)
//                            }
//                            binding.tilKonfirmasiKataSandi.apply {
//                                setStartIconDrawable(R.drawable.baseline_check_circle_24)
//                                setStartIconTintList(ColorStateList.valueOf(Color.GREEN))
//                            }
//                        }
//                    }
//                }
//
//                R.id.et_konfirmasi_kata_sandi -> {
//                    if (hasFocus && binding.tilKonfirmasiKataSandi.isErrorEnabled) {
//                        clearError(binding.tilKonfirmasiKataSandi)
//                    } else {
//                        if (validateConfirmPassword() && validatePassword() && validatePasswordAndConfirmPassword()) {
//                            if (binding.tilKonfirmasiKataSandi.isErrorEnabled) {
//                                clearError(binding.tilKonfirmasiKataSandi)
//                            }
//                            binding.tilKonfirmasiKataSandi.apply {
//                                setStartIconDrawable(R.drawable.baseline_check_circle_24)
//                                setStartIconTintList(ColorStateList.valueOf(Color.GREEN))
//                            }
//                        }
//                    }
//                }
//            }
//        }
//    }
//
//    override fun onKey(view: View?, event: Int, keyEvent: KeyEvent?): Boolean {
//        // Handle key events, e.g., Enter key
//        return false
//    }
//}
//
