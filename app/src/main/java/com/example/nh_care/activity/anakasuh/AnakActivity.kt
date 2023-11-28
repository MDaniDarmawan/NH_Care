package com.example.nh_care.activity.anakasuh

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.example.nh_care.database.DbContract
import com.example.nh_care.databinding.ActivityAnakasuhBinding
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import android.widget.SearchView
import com.example.nh_care.R
import org.json.JSONArray
import org.json.JSONException

class AnakActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAnakasuhBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var anakAdapter: AnakAdapter
    private var anakList = listOf<DataAnak>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAnakasuhBinding.inflate(layoutInflater)
        setContentView(binding.root)

        recyclerView = binding.rvAnak
        anakAdapter = AnakAdapter(anakList)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = anakAdapter

        anakAdapter.setOnItemClickListener(object : AnakAdapter.OnItemClickListener {
            override fun onItemClick(position: Int) {
                val currentItem = anakList[position]

                val intent = Intent(this@AnakActivity, DetailAnakActivity::class.java)
                intent.putExtra("nama", currentItem.Nama)
                intent.putExtra("kelas", currentItem.Kelas)
                intent.putExtra("nama_sekolah", currentItem.Sekolah)
                intent.putExtra("deskripsi", currentItem.Deskripsi)
                // Pastikan `img_anak` sesuai dengan Parcelable jika diperlukan
                intent.putExtra("img_anak", currentItem.img_anak)
                startActivity(intent)
            }
        })
//        val searchView = binding.svAnak
//        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
//            override fun onQueryTextSubmit(query: String?): Boolean {
//                return false
//            }
//
//            override fun onQueryTextChange(newText: String?): Boolean {
//                anakAdapter.filter.filter(newText)
//                return false
//            }
//        })

        fetchAnakDataFromAPI()
    }

    private fun fetchAnakDataFromAPI() {
        val urlDataAnak = DbContract.urlAnak

        val jsonArrayRequest = JsonArrayRequest(
            Request.Method.GET, urlDataAnak, null,
            { response ->
                try {
                    anakList = parseAnak(response)
                    anakAdapter.setAnakList(anakList)
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            },
            { error ->
                error.printStackTrace()
            })

        Volley.newRequestQueue(this).add(jsonArrayRequest)
    }

    private fun parseAnak(jsonArray: JSONArray): List<DataAnak> {
        val anakData = mutableListOf<DataAnak>()

        for (i in 0 until jsonArray.length()) {
            val anakObject = jsonArray.getJSONObject(i)

            val Nama = anakObject.getString("nama")
            val Kelas = anakObject.getString("kelas")
            val Sekolah = anakObject.getString("nama_sekolah")
            val Deskripsi = anakObject.getString("deskripsi")
            val imageAnakBase64 = anakObject.getString("img_anak")

            val decodedImage: Bitmap? = decodeBase64ToBitmap(imageAnakBase64)

            val anak = DataAnak(
                Nama = Nama,
                Kelas = Kelas,
                Sekolah = Sekolah,
                Deskripsi = Deskripsi,
                img_anak = decodedImage
            )

            anakData.add(anak)
        }

        return anakData
    }

    // Fungsi untuk mengonversi string base64 ke objek Bitmap
    private fun decodeBase64ToBitmap(base64String: String): Bitmap? {
        val decodedBytes: ByteArray = Base64.decode(base64String, Base64.DEFAULT)
        return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.size)
    }
}
