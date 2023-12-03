package com.example.nh_care.activity.website

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.example.nh_care.R
import com.example.nh_care.database.DbContract
import org.json.JSONArray
import org.json.JSONException

class WebsiteActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var websiteAdapter: WebsiteAdapter
    private val websiteList = ArrayList<Map<String, String>>() // Menggunakan Map<String, String> untuk data website

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_website)

        recyclerView = findViewById(R.id.rv_website)
        recyclerView.layoutManager = LinearLayoutManager(this)

        websiteAdapter = WebsiteAdapter(websiteList)

        websiteAdapter.setOnItemClickListener(object : WebsiteAdapter.OnItemClickListener {

            override fun onItemClick(position: Int) {
                val currentItem = websiteList[position]
                val url = currentItem["url_website"]
                if (!url.isNullOrBlank()) {
                    openWebsite(url)
                } else {
                    showToast("URL tidak dapat diakses!")
                }
            }

            private fun showToast(message: String) {
                val context = applicationContext
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
            }
        })
        recyclerView.adapter = websiteAdapter

        fetchWebsiteDataFromAPI()
    }

    private fun openWebsite(url: String) {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(url)
        startActivity(intent)
    }

    private fun fetchWebsiteDataFromAPI() {
        val urlDataWebsite = "https://nhcare.tifc.myhost.id/nhcare/api/api-Nhcare.php?function=getWebsiteData"

        val jsonArrayRequest = JsonArrayRequest(
            Request.Method.GET, urlDataWebsite, null,
            { response ->
                try {
                    val fetchedWebsiteList = parseWebsites(response)
                    websiteList.clear()
                    websiteList.addAll(fetchedWebsiteList)
                    websiteAdapter.notifyDataSetChanged()
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

    private fun parseWebsites(jsonArray: JSONArray): List<Map<String, String>> {
        val websites = mutableListOf<Map<String, String>>()

        for (i in 0 until jsonArray.length()) {
            val websiteObject = jsonArray.getJSONObject(i)

            val name = websiteObject.getString("judul_website")
            val url = websiteObject.getString("url_website")
            val image = websiteObject.getString("img_website")

            val website = mapOf(
                "judul_website" to name,
                "url_website" to url,
                "img_website" to image
            )
            websites.add(website)
        }

        return websites
    }
}
