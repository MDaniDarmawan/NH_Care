package com.example.nh_care.fragment.layanan

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.nh_care.R

class LayananAdapter(private var mList: List<DataLayanan>) :
    RecyclerView.Adapter<LayananAdapter.LayananViewHolder>() {

    inner class LayananViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tanya: TextView = itemView.findViewById(R.id.pertanyaan)
        val jawab: TextView = itemView.findViewById(R.id.jawaban)
        val constraintLayout: ConstraintLayout = itemView.findViewById(R.id.constraintLayout)

        // Menghilangkan fungsi collapseExpandedView yang tidak diperlukan di sini
    }

    // Sisanya sama seperti yang Anda tulis sebelumnya

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LayananViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_layanan_list, parent, false)
        return LayananViewHolder(view)
    }

    override fun onBindViewHolder(holder: LayananViewHolder, position: Int) {
        val dataLayanan = mList[position]

        holder.tanya.text = dataLayanan.Pertanyaan
        holder.jawab.text = dataLayanan.Jawaban

        // Mengatur visibilitas berdasarkan isExpandable
        holder.jawab.visibility = if (dataLayanan.isExpandable) View.VISIBLE else View.GONE

        // Menangani klik pada setiap item untuk mengubah isExpandable
        holder.constraintLayout.setOnClickListener {
            dataLayanan.isExpandable = !dataLayanan.isExpandable
            notifyDataSetChanged()
        }
    }

    override fun getItemCount(): Int {
        return mList.size
    }
}
