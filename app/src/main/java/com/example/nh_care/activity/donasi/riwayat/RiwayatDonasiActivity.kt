package com.example.nh_care.activity.donasi.riwayat

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.example.nh_care.activity.MainActivity
import com.example.nh_care.databinding.ActivityRiwayatDonasiBinding
import org.json.JSONArray
import org.json.JSONException

class RiwayatDonasiActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRiwayatDonasiBinding
    private lateinit var recyclerView: RecyclerView
    private var historiAdapter: RiwayatAdapter? = null
    private val historiList = ArrayList<Map<String, String>>() // Menggunakan Map<String, String> sesuai dengan adapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRiwayatDonasiBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Inisialisasi adapter dan RecyclerView
        historiAdapter = RiwayatAdapter(historiList)
        recyclerView = binding.rvRiwayatdonasi

        // Set layout manager dan adapter untuk RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = historiAdapter

        // Set listener untuk menangani klik item pada RecyclerView
        historiAdapter?.setOnItemClickListener(object : RiwayatAdapter.OnItemClickListener {
            override fun onItemClick(position: Int) {
                // Menggunakan Intent untuk berpindah ke DetailHistoriDonasi
                val intent = Intent(this@RiwayatDonasiActivity, DetailRiwayatDonasi::class.java)

                // Mengambil data dari item yang diklik
                val currentItem = historiList[position]
                intent.putExtra("orderID", currentItem["order_id"])
                intent.putExtra("namaDon", currentItem["nama_donatur"])
                intent.putExtra("judul", currentItem["keterangan"])
                intent.putExtra("jumlah", currentItem["gross_amount"])
                intent.putExtra("tanggal", currentItem["settlement_time"])

                // Menjalankan intent untuk membuka DetailHistoriDonasi
                startActivity(intent)
            }
        })

        // Mengatur listener untuk tombol kembali
        binding.btnbackriwayat.setOnClickListener {
            // Menggunakan Intent untuk berpindah ke MainActivity
            val intent = Intent(this@RiwayatDonasiActivity, MainActivity::class.java)
            startActivity(intent)
        }

        // Memanggil metode untuk mengambil data dari API lokal
        fetchHistoriDataFromAPI()
    }

    // Metode untuk mengambil data dari API lokal menggunakan Volley
    private fun fetchHistoriDataFromAPI() {
        // Mendapatkan ID Donatur dari Shared Preferences
        val sharedPreferences = getSharedPreferences("donatur_prefs", Context.MODE_PRIVATE)
        val idDonatur = sharedPreferences.getString("id_donatur", "")

        // Pastikan ID Donatur tidak kosong sebelum melakukan permintaan
        if (idDonatur.isNullOrEmpty()) {
            Log.e("ID_DONATUR_ERROR", "ID Donatur kosong atau null")
            return
        }

        // URL API dengan penambahan parameter id_donatur
        val urlDataHistori = "http://192.168.1.15/api-mysql-main/api-historiDonasi.php?id_donatur=$idDonatur"

        val jsonArrayRequest = JsonArrayRequest(
            Request.Method.GET, urlDataHistori, null,
            { response ->
                try {
                    // Parsing data dan mengupdate adapter
                    val fetchedHistoriList = parseHistori(response)
                    historiList.clear()
                    historiList.addAll(fetchedHistoriList)
                    historiAdapter?.setHistori(historiList)
                } catch (e: JSONException) {
                    Log.e("JSON_ERROR", "Error: " + e.message)
                    e.printStackTrace()
                }
            },
            { error ->
                Log.e("VOLLEY_ERROR", "Error: " + error.message)
                error.printStackTrace()
            })

        // Menambahkan request ke Queue Volley
        Volley.newRequestQueue(this).add(jsonArrayRequest)
    }

    // Metode untuk parsing data JSON ke dalam List<Map<String, String>>
    private fun parseHistori(jsonArray: JSONArray): List<Map<String, String>> {
        val historis = mutableListOf<Map<String, String>>()

        for (i in 0 until jsonArray.length()) {
            val historiObject = jsonArray.getJSONObject(i)

            // Mendapatkan data dari JSON
            val orderID = historiObject.getString("order_id")
            val namaDon = historiObject.getString("nama_donatur")
            val judul = historiObject.getString("keterangan")
            val jumlah = historiObject.getString("gross_amount")
            val tanggal = historiObject.getString("settlement_time")

            // Membuat objek Map dan menambahkannya ke dalam List
            val histori = mapOf(
                "order_id" to orderID,
                "nama_donatur" to namaDon,
                "keterangan" to judul,
                "gross_amount" to jumlah,
                "settlement_time" to tanggal
            )
            historis.add(histori)
        }

        return historis
    }
}
