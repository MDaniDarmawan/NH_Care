package com.example.nh_care.activity.anakasuh

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.nh_care.R
import com.example.nh_care.activity.anakasuh.DataAnak
import com.google.android.material.imageview.ShapeableImageView

class AnakAdapter(private var anakListFull: List<DataAnak>) : RecyclerView.Adapter<AnakAdapter.AnakViewHolder>(), Filterable {

    var anakListFiltered: List<DataAnak> = anakListFull.toList() // Salin daftar lengkap sebagai daftar yang akan difilter

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    private var listener: OnItemClickListener? = null

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }

    inner class AnakViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val itemName: TextView = itemView.findViewById(R.id.itemName1)
        val itemAvatar: ShapeableImageView = itemView.findViewById(R.id.itemAvatar1)
        val itemName2: TextView = itemView.findViewById(R.id.itemName2)
        val itemAvatar2: ShapeableImageView = itemView.findViewById(R.id.itemAvatar2)
        val colom1: LinearLayout = itemView.findViewById(R.id.colom1)
        val colom2: LinearLayout = itemView.findViewById(R.id.colom2)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnakViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_anakasuh_list, parent, false)
        return AnakViewHolder(view)
    }

    override fun onBindViewHolder(holder: AnakViewHolder, position: Int) {
        // onBindViewHolder implementation...
        val firstItemIndex = position * 2
        val secondItemIndex = position * 2 + 1

        val hasFirstItem = firstItemIndex < anakListFiltered.size
        val hasSecondItem = secondItemIndex < anakListFiltered.size

        if (hasFirstItem) {
            val firstItem = anakListFiltered[firstItemIndex]

            holder.colom1.visibility = View.VISIBLE
            holder.itemName.text = firstItem.Nama
            holder.itemAvatar.setImageBitmap(firstItem.img_anak)

            holder.colom1.setOnClickListener {
                listener?.onItemClick(firstItemIndex)
            }
        } else {
            holder.colom1.visibility = View.INVISIBLE
            holder.colom1.setOnClickListener(null)
            if (hasSecondItem) {
            }
        }

        if (hasSecondItem) {
            val secondItem = anakListFiltered[secondItemIndex]

            holder.colom2.visibility = View.VISIBLE
            holder.itemName2.text = secondItem.Nama
            holder.itemAvatar2.setImageBitmap(secondItem.img_anak)

            holder.colom2.setOnClickListener {
                listener?.onItemClick(secondItemIndex)
            }
        } else {
            holder.colom2.visibility = View.INVISIBLE
            holder.colom2.setOnClickListener(null)
            if (hasFirstItem && !hasSecondItem) {
            }
        }

        // Memeriksa jika hanya ada data ganjil
        if (hasFirstItem && !hasSecondItem) {
            holder.colom2.visibility = View.GONE // Menyembunyikan kolom kanan jika hanya ada data ganjil
        }
    }

    override fun getItemCount(): Int {
        val itemCount = anakListFiltered.size
        return if (itemCount % 2 == 0) {
            itemCount / 2
        } else {
            itemCount / 2 + 1
        }
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val filteredList = mutableListOf<DataAnak>()

                if (constraint.isNullOrEmpty()) {
                    filteredList.addAll(anakListFull) // Jika pencarian kosong, tampilkan daftar lengkap
                } else {
                    val filterPattern = constraint.toString().toLowerCase().trim()

                    for (anak in anakListFull) {
                        if (anak.Nama.toLowerCase().contains(filterPattern) ||
                            anak.Kelas.toLowerCase().contains(filterPattern) ||
                            anak.Sekolah.toLowerCase().contains(filterPattern) ||
                            anak.Deskripsi.toLowerCase().contains(filterPattern)
                        ) {
                            filteredList.add(anak)
                        }
                    }
                }

                val results = FilterResults()
                results.values = filteredList
                return results
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                results?.values?.let {
                    anakListFiltered = it as List<DataAnak> // Update anakListFiltered sesuai dengan hasil filter
                    notifyDataSetChanged() // Perbarui tampilan RecyclerView
                }
            }
        }
    }

    fun setAnakList(anakList: List<DataAnak>) {
        this.anakListFull = anakList.toList()
        anakListFiltered = anakList.toList() // Saat daftar anak diperbarui, perbarui anakListFiltered juga
        notifyDataSetChanged()
    }
}
