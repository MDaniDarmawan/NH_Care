package com.example.nh_care.fragment.beranda

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.nh_care.activity.anakasuh.AnakActivity
import com.example.nh_care.activity.acara.AcaraActivity
import com.example.nh_care.activity.alokasi.AlokasiActivity
import com.example.nh_care.activity.program.ProgramActivity
import com.example.nh_care.activity.video.VideoActivity
import com.example.nh_care.activity.website.WebsiteActivity
import com.example.nh_care.databinding.FragmentBerandaBinding

class BerandaFragment : Fragment() {
    private lateinit var binding: FragmentBerandaBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {
        binding = FragmentBerandaBinding.inflate(inflater, container, false)

        binding.btnanak.setOnClickListener{
            val intent = Intent(requireActivity(), AnakActivity::class.java)
            startActivity(intent)
        }
        binding.btnacara.setOnClickListener{
            val intent = Intent(requireActivity(), AcaraActivity::class.java)
            startActivity(intent)
        }
        binding.btnprogram.setOnClickListener{
            val intent = Intent(requireActivity(), ProgramActivity::class.java)
            startActivity(intent)
        }
        binding.btnalokasi.setOnClickListener {
            val intent = Intent(requireActivity(), AlokasiActivity::class.java)
            startActivity(intent)
        }
        binding.btnvideo.setOnClickListener{
            val intent = Intent(requireActivity(), VideoActivity::class.java)
            startActivity(intent)
        }
        binding.btnwebsite.setOnClickListener{
            val intent = Intent(requireActivity(), WebsiteActivity::class.java)
            startActivity(intent)
        }
        binding.btnyt.setOnClickListener{
            openUrl("https://www.youtube.com/@nurulhusnajember")
        }
        return binding.root
    }
    private fun openUrl(link:String){
        val uri = Uri.parse(link)
        val intent = Intent(Intent.ACTION_VIEW, uri)
        startActivity(intent)
    }
}

