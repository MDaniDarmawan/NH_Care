package com.example.berandaberanda.activitity.alokasi

import android.graphics.BitmapFactory
import android.util.Base64
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.nh_care.R
import com.example.nh_care.databinding.ItemAlokasiListBinding


class AlokasiAdapter(private var alokasiList: List<Map<String, String>>) :
    RecyclerView.Adapter<AlokasiAdapter.AlokasiViewHolder>() {

    private var listener: OnItemClickListener? = null

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }

    fun setAlokasi(Alokasi: List<Map<String, String>>) {
        alokasiList = Alokasi
        notifyDataSetChanged()
    }

    inner class AlokasiViewHolder(private val binding: ItemAlokasiListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    listener?.onItemClick(position)
                }
            }
        }

        fun bind(alokasi: Map<String, String>) {
            val textView: TextView = binding.titleAlokasi
            val imageView: ImageView = binding.logoAlokasi

            // Set the text from MapelModel to the TextView
            textView.text = alokasi["judul"]

            // Load the image using Picasso into the ImageView if the path is not empty
            val imageBase64 = alokasi["image"]
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlokasiViewHolder {
        val binding = ItemAlokasiListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AlokasiViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AlokasiViewHolder, position: Int) {
        val currentItem = alokasiList[position]
        holder.bind(currentItem)
    }

    override fun getItemCount() = alokasiList.size
}