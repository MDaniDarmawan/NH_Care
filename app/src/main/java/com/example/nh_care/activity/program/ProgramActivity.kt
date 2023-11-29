package com.example.nh_care.activity.program

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.example.nh_care.R
import com.example.nh_care.database.DbContract
import android.graphics.BitmapFactory
import android.util.Base64
import android.util.Log
import org.json.JSONArray
import org.json.JSONException

class ProgramActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private var programAdapter: ProgramAdapter? = null
    private val programList = ArrayList<Map<String, String>>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_program)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        programAdapter = ProgramAdapter(programList)
        recyclerView = findViewById(R.id.rv_program)

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = programAdapter

        programAdapter?.setOnItemClickListener(object : ProgramAdapter.OnItemClickListener {
            override fun onItemClick(position: Int) {
                val intent = Intent(this@ProgramActivity, DetailProgramActivity::class.java)
                val currentItem = programList[position]
                intent.putExtra("judul", currentItem["judul"])
                intent.putExtra("deskripsi", currentItem["deskripsi"])
                val imageBase64 = currentItem["image"]
                if (!imageBase64.isNullOrBlank()) {
                    val imageBytes = Base64.decode(imageBase64, Base64.DEFAULT)
                    intent.putExtra("img_program", imageBytes)
                }
                startActivity(intent)
            }
        })

        fetchProgramDataFromAPI()
    }

    private fun fetchProgramDataFromAPI() {
        val urlDataProgram = DbContract.urlProgram

        val jsonArrayRequest = JsonArrayRequest(
            Request.Method.GET, urlDataProgram, null,
            { response ->
                try {
                    val fetchedProgramList = parsePrograms(response)
                    programList.clear()
                    programList.addAll(fetchedProgramList)
                    programAdapter?.setPrograms(programList)
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

    private fun parsePrograms(jsonArray: JSONArray): List<Map<String, String>> {
        val programs = mutableListOf<Map<String, String>>()

        for (i in 0 until jsonArray.length()) {
            val programObject = jsonArray.getJSONObject(i)

            val judul = programObject.getString("judul")
            val deskripsi = programObject.getString("deskripsi")
            val imageProgramBase64 = programObject.getString("img_program")

            val program = mapOf(
                "judul" to judul,
                "deskripsi" to deskripsi,
                "image" to imageProgramBase64
            )
            programs.add(program)
        }

        return programs
    }
}
