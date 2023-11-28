package com.example.nh_care.fragment.profile

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.nh_care.databinding.FragmentProfileBinding


class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        val root : View = binding.root

        sharedPreferences = requireContext().getSharedPreferences("donatur_prefs", Context      .MODE_PRIVATE)
        val namaDonatur: String? = sharedPreferences.getString("nama", "")
        val emailDonatur: String? = sharedPreferences.getString("email", "")
        val noHpDonatur: String? = sharedPreferences.getString("no_hp", "")

        val firstChar = namaDonatur?.take(1)
        binding.fotoprofil.text = firstChar

        binding.kotakNamaPengguna.setText(namaDonatur)
        binding.kotakEmail.setText(emailDonatur)
        binding.kotakNomorTelepon.setText(noHpDonatur)

        return root
    }
}

