package com.example.nh_care.activity.website

import android.graphics.BitmapFactory
import android.util.Base64
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.nh_care.R
import com.example.nh_care.databinding.ItemWebsiteListBinding

class WebsiteAdapter(private val websiteList: List<Map<String, String>>) :
    RecyclerView.Adapter<WebsiteAdapter.WebsiteViewHolder>() {

    private var listener: OnItemClickListener? = null

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }

    inner class WebsiteViewHolder(private val binding: ItemWebsiteListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    listener?.onItemClick(position)
                }
            }
        }

        fun bind(website: Map<String, String>) {
            val textWeb: TextView = binding.itemWebsite
            val textUrl: TextView = binding.itemUrl
            val imageView: ImageView = binding.itemAvatar

            textWeb.text = website["judul_website"]
            textUrl.text = website["url_website"]

            val imageBase64 = website["img_website"]
            if (!imageBase64.isNullOrBlank()) {
                val imageBytes = Base64.decode(imageBase64, Base64.DEFAULT)
                val decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
                imageView.setImageBitmap(decodedImage)
            } else {
                // Handle the case where the base64-encoded string is empty or null, for example, set a placeholder image
                imageView.setImageResource(R.drawable.comingsoon)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WebsiteViewHolder {
        val binding = ItemWebsiteListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return WebsiteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: WebsiteViewHolder, position: Int) {
        val currentItem = websiteList[position]
        holder.bind(currentItem)
    }

    override fun getItemCount() = websiteList.size
}
