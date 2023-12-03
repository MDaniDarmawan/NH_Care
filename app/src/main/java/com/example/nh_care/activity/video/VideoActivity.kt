package com.example.nh_care.activity.video

import android.os.Bundle
import android.util.Log
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

class VideoActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private var videoAdapter: VideoAdapter? = null
    private val videoList = ArrayList<VideoModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video)

        videoAdapter = VideoAdapter(videoList)
        recyclerView = findViewById(R.id.rv_video)

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = videoAdapter

        fetchProgramDataFromAPI()
    }

    private fun fetchProgramDataFromAPI() {
        val urlVideoList = "https://nhcare.tifc.myhost.id/nhcare/api/api-Nhcare.php?function=getVideoData"

        val jsonArrayRequest = JsonArrayRequest(
            Request.Method.GET, urlVideoList, null,
            { response ->
                try {
                    val videoList = parseVideos(response)
                    handleVideoList(videoList)
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

    private fun parseVideos(jsonArray: JSONArray): List<VideoModel> {
        val videos = mutableListOf<VideoModel>()

        for (i in 0 until jsonArray.length()) {
            val videoObject = jsonArray.getJSONObject(i)

            val title = videoObject.getString("judul_video")
            val url = videoObject.getString("url_video")

            val video = VideoModel(url, title)
            videos.add(video)
        }

        return videos
    }

    private fun handleVideoList(videoList: List<VideoModel>) {
        this.videoList.addAll(videoList)
        videoAdapter?.notifyDataSetChanged()
    }
}
