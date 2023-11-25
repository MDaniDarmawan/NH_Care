package com.example.nh_care.activity.program

import android.graphics.BitmapFactory
import android.util.Base64
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.nh_care.R
import com.example.nh_care.databinding.ItemProgramListBinding

class ProgramAdapter(private var programList: List<Map<String, String>>) :
    RecyclerView.Adapter<ProgramAdapter.ProgramViewHolder>() {

    private var listener: OnItemClickListener? = null

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }

    fun setPrograms(programs: List<Map<String, String>>) {
        programList = programs
        notifyDataSetChanged()
    }

    inner class ProgramViewHolder(private val binding: ItemProgramListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    listener?.onItemClick(position)
                }
            }
        }

        fun bind(program: Map<String, String>) {
            val textView: TextView = binding.itemName
            val imageView: ImageView = binding.itemAvatar

            // Set the text from MapelModel to the TextView
            textView.text = program["judul"]

            // Load the image using Picasso into the ImageView if the path is not empty
            val imageBase64 = program["image"]
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProgramViewHolder {
        val binding = ItemProgramListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProgramViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProgramViewHolder, position: Int) {
        val currentItem = programList[position]
        holder.bind(currentItem)
    }

    override fun getItemCount() = programList.size
}
