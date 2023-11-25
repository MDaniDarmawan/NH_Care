package com.example.berandaberanda.activity.acara

import android.os.Bundle
import android.widget.CalendarView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.example.berandaberanda.R
import org.json.JSONArray
import org.json.JSONException
import android.util.Log
import com.example.berandaberanda.database.DbContract
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class AcaraActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private var acaraAdapter: AcaraAdapter? = null
    private val acaraList = ArrayList<DataAcara>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_acara)

        recyclerView = findViewById(R.id.rv_acara)
        recyclerView.layoutManager = LinearLayoutManager(this)
        acaraAdapter = AcaraAdapter(acaraList)
        recyclerView.adapter = acaraAdapter

        val calendarView = findViewById<CalendarView>(R.id.calendarView)
        calendarView.setOnDateChangeListener { view, year, month, dayOfMonth ->
            val selectedDate = "$dayOfMonth/${month + 1}/$year"
            Toast.makeText(this, "Tanggal dipilih: $selectedDate", Toast.LENGTH_SHORT).show()
            // Di sini Anda bisa menambahkan logika untuk menangani perubahan tanggal
        }

        fetchDataFromAPI()
    }

    private fun fetchDataFromAPI() {
        val urlDataAcara = DbContract.urlAcara

        val jsonArrayRequest = JsonArrayRequest(
            Request.Method.GET, urlDataAcara, null,
            { response ->
                try {
                    val fetchedProgramList = parseAcara(response)
                    acaraList.clear()
                    acaraList.addAll(fetchedProgramList)
                    acaraAdapter?.setAcara(acaraList)
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

    private fun parseAcara(jsonArray: JSONArray): List<DataAcara> {
        val acara = mutableListOf<DataAcara>()

        for (i in 0 until jsonArray.length()) {
            try {
                val acaraObject = jsonArray.getJSONObject(i)

                val judul = acaraObject.getString("judul")
                val deskripsi = acaraObject.getString("deskripsi")
                val tanggalString = acaraObject.getString("tanggal_acara")

                val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
                val tanggalLocalDate = LocalDateTime.parse(tanggalString, formatter)

                val acaraModel = DataAcara(judul, deskripsi, tanggalLocalDate)
                acara.add(acaraModel)
            } catch (e: JSONException) {
                Log.e("JSON_ERROR", "Error parsing acara: " + e.message)
                e.printStackTrace()
            }
        }

        return acara
    }
}
