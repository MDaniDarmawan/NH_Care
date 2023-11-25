package com.example.nh_care.activity.acara

import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.nh_care.activity.acara.DataAcara
import com.example.nh_care.databinding.ItemAcaraListBinding
import java.time.format.DateTimeFormatter

class AcaraAdapter(private var acaraList: List<DataAcara>) :
    RecyclerView.Adapter<AcaraAdapter.AcaraViewHolder>() {

    private var listener: OnItemClickListener? = null

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }

    fun setAcara(acara: List<DataAcara>) {
        acaraList = acara
        notifyDataSetChanged()
    }

    inner class AcaraViewHolder(private val binding: ItemAcaraListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    listener?.onItemClick(position)
                }
            }
        }

        @RequiresApi(Build.VERSION_CODES.O)
        fun bind(acara: DataAcara) {
            val judulView: TextView = binding.itemJudul
            val descView: TextView = binding.itemDesc
            val dateView: TextView = binding.itemDate

            judulView.text = acara.judulacara
            descView.text = acara.descacara
            dateView.text = acara.tanggal.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AcaraViewHolder {
        val binding = ItemAcaraListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AcaraViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AcaraViewHolder, position: Int) {
        val currentItem = acaraList[position]
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            holder.bind(currentItem)
        }
    }

    override fun getItemCount() = acaraList.size
}
