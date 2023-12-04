package com.example.nh_care.activity.acara

import android.content.Intent
import android.os.Bundle
import android.widget.CalendarView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.example.nh_care.activity.MainActivity
import com.example.nh_care.databinding.ActivityAcaraBinding
import org.json.JSONArray
import org.json.JSONException
import java.text.SimpleDateFormat
import java.util.*

class AcaraActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAcaraBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var acaraAdapter: AcaraAdapter
    private val acaraList = mutableListOf<DataAcara>()
    private val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAcaraBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnbackacara.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        recyclerView = binding.rvAcara
        recyclerView.layoutManager = LinearLayoutManager(this)
        acaraAdapter = AcaraAdapter(acaraList)
        recyclerView.adapter = acaraAdapter

        val calendarView: CalendarView = binding.calendarView

        calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            val selectedDate = Calendar.getInstance()
            selectedDate.set(year, month, dayOfMonth)
            fetchDataForDate(selectedDate)
        }

        // Set the current date as the default selected date
        val currentDate = Calendar.getInstance()
        calendarView.date = currentDate.timeInMillis

        // Fetch data for the current date initially
        fetchDataForDate(currentDate)
    }

    private fun fetchDataForDate(selectedDate: Calendar) {
        val formattedDate = dateFormat.format(selectedDate.time)
        val urlDataAcara = "https://nhcare.tifc.myhost.id/nhcare/api/api-acara.php?tanggal_acara=$formattedDate"
        val jsonArrayRequest = JsonArrayRequest(
            Request.Method.GET, urlDataAcara, null,
            { response ->
                try {
                    val fetchedAcaraList = parseAcara(response)
                    acaraAdapter.setAcara(fetchedAcaraList)
                    acaraAdapter.filterByDate(selectedDate)
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            },
            { error ->
                Toast.makeText(this, "Error: ${error.message}", Toast.LENGTH_SHORT).show()
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

                val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                val tanggal = sdf.parse(tanggalString)

                if (tanggal != null) {
                    val calendar = Calendar.getInstance()
                    calendar.time = tanggal
                    val acaraModel = DataAcara(judul, deskripsi, calendar)
                    acara.add(acaraModel)
                }
            } catch (e: JSONException) {
                e.printStackTrace()
            }
        }

        return acara
    }
}


