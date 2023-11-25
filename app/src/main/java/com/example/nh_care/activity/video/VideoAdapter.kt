package com.example.nh_care.activity.video

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.nh_care.R
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView
import java.util.regex.Pattern

class VideoAdapter(private val videos: ArrayList<VideoModel>) :
    RecyclerView.Adapter<VideoAdapter.VideoViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_video_list, parent, false)
        return VideoViewHolder(view)
    }

    override fun onBindViewHolder(holder: VideoViewHolder, position: Int) {
        val video = videos[position]
        holder.bind(video)
    }

    override fun getItemCount(): Int {
        return videos.size
    }

    class VideoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val videoTitle: TextView = itemView.findViewById(R.id.txtvideo)
        private val youTubePlayerView: YouTubePlayerView = itemView.findViewById(R.id.itemvideo)

        fun bind(video: VideoModel) {
            videoTitle.text = video.judul

            val videoId = extractVideoIdFromUrl(video.videoId)

            youTubePlayerView.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
                override fun onReady(youTubePlayer: YouTubePlayer) {
                    youTubePlayer.loadVideo(videoId, 0F)
                }
            })
        }

    private fun extractVideoIdFromUrl(videoUrl: String?): String {
            videoUrl?.let {
                val regex = "(?<=v=)[\\w-]+"
                val pattern = Pattern.compile(regex)
                val matcher = pattern.matcher(videoUrl)

                if (matcher.find()) {
                    return matcher.group()
                }
            }
            return "defaultVideoId"
        }
    }
}
