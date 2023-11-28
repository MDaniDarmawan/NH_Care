package com.example.nh_care.activity.acara

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.nh_care.databinding.ItemAcaraListBinding
import java.text.SimpleDateFormat
import java.util.*

class AcaraAdapter(private var acaraList: List<DataAcara>) :
    RecyclerView.Adapter<AcaraAdapter.AcaraViewHolder>() {

    private var listener: OnItemClickListener? = null
    private var filteredAcaraList: List<DataAcara> = emptyList()

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }

    fun setAcara(acara: List<DataAcara>) {
        acaraList = acara
        filterByDate(Calendar.getInstance())
    }

    fun filterByDate(selectedDate: Calendar) {
        filteredAcaraList = if (selectedDate != null) {
            acaraList.filter {
                val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                val formattedDate = sdf.format(it.tanggal.time)
                formattedDate == sdf.format(selectedDate.time)
            }
        } else {
            acaraList // Show all data if selectedDate is null
        }
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

        fun bind(acara: DataAcara) {
            val judulView: TextView = binding.itemJudul
            val descView: TextView = binding.itemDesc
            val dateView: TextView = binding.itemDate

            judulView.text = acara.judulacara
            descView.text = acara.descacara

            val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            val formattedDate = sdf.format(acara.tanggal.time)
            dateView.text = formattedDate
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AcaraViewHolder {
        val binding = ItemAcaraListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AcaraViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AcaraViewHolder, position: Int) {
        val currentItem = filteredAcaraList[position]
        holder.bind(currentItem)
    }

    override fun getItemCount() = filteredAcaraList.size
}

