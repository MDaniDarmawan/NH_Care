package com.example.nh_care.fragment.beranda

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.nh_care.activity.anakasuh.AnakActivity
import com.example.nh_care.activity.acara.AcaraActivity
import com.example.nh_care.activity.alokasi.AlokasiActivity
import com.example.nh_care.activity.donasi.DonasiActivity
import com.example.nh_care.activity.program.ProgramActivity
import com.example.nh_care.activity.video.VideoActivity
import com.example.nh_care.activity.website.WebsiteActivity
import com.example.nh_care.databinding.FragmentBerandaBinding
import java.text.NumberFormat
import java.util.Locale

class BerandaFragment : Fragment() {
    private lateinit var binding: FragmentBerandaBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {
        binding = FragmentBerandaBinding.inflate(inflater, container, false)

        fetchDataTotalDonasi()

        binding.btndonasi.setOnClickListener{
            val intent = Intent(requireActivity(), DonasiActivity::class.java)
            startActivity(intent)
        }
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
    private fun fetchDataTotalDonasi() {
        val url = "http://192.168.1.15/api-mysql-main/api-getTotalDonasi.php"

        // Membuat request JSON menggunakan Volley
        val stringRequest = StringRequest(
            Request.Method.GET, url,
            { response ->
                // Tanggapi respons string dari server
                try {
                    // Coba mengonversi respons menjadi angka
                    val totalDonasi = response.toDouble()

                    // Format angka sebagai mata uang Rupiah
                    val formattedTotalDonasi = formatToRupiah(totalDonasi)

                    // Set teks pada TextView totaldonasi
                    binding.totaldonasi.text = formattedTotalDonasi
                    Log.d("DashboardFragment", "Total donasi: $formattedTotalDonasi")

                } catch (e: NumberFormatException) {
                    Log.e("DashboardFragment", "Error parsing response to double: ${e.message}")
                }
            },
            { error ->
                // Tanggapi error dari server
                error.printStackTrace()
                Log.e("DashboardFragment", "Volley error: ${error.message}")
            })

        // Tambahkan request ke antrian Volley
        Volley.newRequestQueue(requireContext()).add(stringRequest)
    }

    // Fungsi untuk mengubah angka menjadi format mata uang Rupiah
    private fun formatToRupiah(totalDonasi: Double): String {
        val formatter = NumberFormat.getCurrencyInstance(Locale("id", "ID")) // Menggunakan locale Indonesia
        val formattedAmount = formatter.format(totalDonasi)

        // Menghapus simbol mata uang dan spasi dari format default
        return formattedAmount.replace("Rp", "Rp.")
    }
}

