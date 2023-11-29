package com.example.nh_care.activity.alokasi

import android.content.Intent
import android.os.Bundle
import android.util.Base64
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.example.berandaberanda.activitity.alokasi.AlokasiAdapter
import com.example.nh_care.R
import com.example.nh_care.database.DbContract
import com.example.nh_care.databinding.ActivityAlokasiBinding
import com.example.nh_care.fragment.beranda.BerandaFragment
import org.json.JSONArray
import org.json.JSONException

class AlokasiActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAlokasiBinding
    private lateinit var recyclerView: RecyclerView
    private var alokasiAdapter: AlokasiAdapter? = null
    private val alokasiList = ArrayList<Map<String, String>>() // Using Map<String, String> as per the adapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAlokasiBinding.inflate(layoutInflater)
        setContentView(binding.root)

        alokasiAdapter = AlokasiAdapter(alokasiList)
        recyclerView = binding.rvAlokasi

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = alokasiAdapter

        // Set listener to handle item clicks
        alokasiAdapter?.setOnItemClickListener(object : AlokasiAdapter.OnItemClickListener {
            override fun onItemClick(position: Int) {
                val intent = Intent(this@AlokasiActivity, DetailAlokasiActivity::class.java)
                val currentItem = alokasiList[position]
                intent.putExtra("judul", currentItem["judul"])
                intent.putExtra("deskripsi", currentItem["deskripsi"])
                val imageBase64 = currentItem["image"]
                if (!imageBase64.isNullOrBlank()) {
                    val imageBytes = Base64.decode(imageBase64, Base64.DEFAULT)
                    intent.putExtra("img_alokasi", imageBytes)
                }
                startActivity(intent)
            }
        })

        binding.btnbackalokasi.setOnClickListener {
            val fragmentManager: FragmentManager = supportFragmentManager
            val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
            val berandaFragment = BerandaFragment()
            fragmentTransaction.replace(R.id.nav_host_fragment_activity_main, berandaFragment)
            fragmentTransaction.commit()
        }

        // Call the method to fetch data from the local API
        fetchAlokasiDataFromAPI()
    }

    private fun fetchAlokasiDataFromAPI() {
        val urlDataAlokasi = DbContract.urlAlokasi

        val jsonArrayRequest = JsonArrayRequest(
            Request.Method.GET, urlDataAlokasi, null,
            { response ->
                try {
                    val fetchedAlokasiList = parseAlokasi(response)
                    alokasiList.clear()
                    alokasiList.addAll(fetchedAlokasiList)
                    alokasiAdapter?.setAlokasi(alokasiList)
                } catch (e: JSONException) {
                    Log.e("JSON_ERROR", "Error: " + e.message)
                    e.printStackTrace()
                }
            },
            { error ->
                Log.e("VOLLEY_ERROR", "Error: " + error.message)
                error.printStackTrace()
            })

        Volley.newRequestQueue(this).add(jsonArrayRequest)
    }

    private fun parseAlokasi(jsonArray: JSONArray): List<Map<String, String>> {
        val alokasik = mutableListOf<Map<String, String>>()

        for (i in 0 until jsonArray.length()) {
            val alokasiObject = jsonArray.getJSONObject(i)

            val judul = alokasiObject.getString("judul")
            val deskripsi = alokasiObject.getString("deskripsi")
            val imageAlokasiBase64 = alokasiObject.getString("img_alokasi")

            val alokasi = mapOf(
                "judul" to judul,
                "deskripsi" to deskripsi,
                "image" to imageAlokasiBase64
            )
            alokasik.add(alokasi)
        }

        return alokasik
    }
}
