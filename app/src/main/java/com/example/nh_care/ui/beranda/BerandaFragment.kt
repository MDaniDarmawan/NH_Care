package com.example.nh_care.ui.beranda

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.nh_care.R
import com.example.nh_care.activity.alokasi.AlokasiActivity
import com.example.nh_care.databinding.FragmentBerandaBinding

class BerandaFragment : Fragment() {
    private lateinit var binding: FragmentBerandaBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {
        binding = FragmentBerandaBinding.inflate(inflater,container, false)

            binding.btnalokasi.setOnClickListener {
                val intent = Intent(requireActivity(), AlokasiActivity::class.java)
                startActivity(intent)
            }
            return binding.root
        }
    }
