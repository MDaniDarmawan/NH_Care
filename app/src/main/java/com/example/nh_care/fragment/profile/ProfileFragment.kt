package com.example.nh_care.fragment.profile

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.nh_care.activity.login.LoginActivity
import com.example.nh_care.databinding.FragmentProfileBinding
import org.json.JSONException
import org.json.JSONObject


class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        val root: View = binding.root

        sharedPreferences =
            requireContext().getSharedPreferences("donatur_prefs", Context.MODE_PRIVATE)
        val idDonatur = sharedPreferences.getString("id_donatur", "")
        val namaDonatur: String? = sharedPreferences.getString("nama", "")
        val emailDonatur: String? = sharedPreferences.getString("email", "")
        val noHpDonatur: String? = sharedPreferences.getString("no_hp", "")

//        val firstChar = namaDonatur?.take(1)
//        binding.fotoprofil.text = firstChar

        binding.kotakNamaPengguna.setText(namaDonatur)
        binding.kotakEmail.setText(emailDonatur)
        binding.kotakNomorTelepon.setText(noHpDonatur)


        binding.tombolEdit.setOnClickListener {

            val url = "http://192.168.1.15/api-mysql-main/api-updateProfile.php?id_donatur=$idDonatur"


            val requestQueue: RequestQueue = Volley.newRequestQueue(requireContext())

            val stringRequest = object : StringRequest(
                Method.POST, url,
                { response ->
                    try {
                        val jsonObject = JSONObject(response)
                        val status = jsonObject.getString("status")
                        val message = jsonObject.getString("message")

                        if (status == "success") {
                            // Jika pembaruan berhasil, tampilkan pesan sukses
                            Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()

                        } else {
                            // Jika pembaruan gagal, tampilkan pesan error
                            Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
                        }
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                },
                { error ->
                    error.printStackTrace()
                }
            ) {
                override fun getParams(): MutableMap<String, String> {
                    val params = HashMap<String, String>()
                    params["nama"] = binding.kotakNamaPengguna.text.toString()
                    params["email"] = binding.kotakEmail.text.toString()
                    params["password"] = binding.kotakKataSandi.text.toString()
                    params["no_hp"] = binding.kotakNomorTelepon.text.toString()
                    params["alamat"] = binding.kotakAlamat.text.toString()
                    params["jenis_kelamin"] = binding.kotakJk.text.toString()
                    return params
                }
            }
            requestQueue.add(stringRequest)
        }
        binding.tombolKeluar.setOnClickListener {
            // Menghapus status login dari SharedPreferences
            val editor = sharedPreferences.edit()
            editor.putBoolean("is_logged_in", false)
            editor.apply()

            // Navigasi kembali ke LoginActivity
            val intent = Intent(requireContext(), LoginActivity::class.java)
            startActivity(intent)
            requireActivity().finish() // Menutup aktivitas saat logout
        }
        return root
    }
}

