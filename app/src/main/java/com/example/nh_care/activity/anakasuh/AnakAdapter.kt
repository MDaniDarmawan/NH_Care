package com.example.nh_care.activity.anakasuh

import android.graphics.Bitmap
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


class AnakAdapter(private var anakListFull: List<DataAnak>) : RecyclerView.Adapter<AnakAdapter.AnakViewHolder>(),
    Filterable {

    private var anakListFiltered: List<DataAnak> = anakListFull

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

        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val firstItemIndex = position * 2
                    val secondItemIndex = position * 2 + 1

                    if (firstItemIndex < anakListFull.size) {
                        listener?.onItemClick(firstItemIndex)
                    }
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnakViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_anakasuh_list, parent, false)
        return AnakViewHolder(view)
    }


    override fun onBindViewHolder(holder: AnakViewHolder, position: Int) {
        val firstItemIndex = position * 2
        val secondItemIndex = position * 2 + 1

        val hasFirstItem = firstItemIndex < anakListFull.size
        val hasSecondItem = secondItemIndex < anakListFull.size


        if (hasFirstItem) {
            val firstItem = anakListFull[firstItemIndex]

            holder.colom1.visibility = View.VISIBLE
            holder.itemName.text = firstItem.Nama
            holder.itemAvatar.setImageBitmap(firstItem.img_anak)

            holder.colom1.setOnClickListener {
                listener?.onItemClick(firstItemIndex)
            }
        } else {
            holder.colom1.visibility = View.GONE
        }

        if (hasSecondItem) {
            val secondItem = anakListFull.getOrNull(secondItemIndex)

            if (secondItem != null) {
                holder.colom2.visibility = View.VISIBLE
                holder.itemName2.text = secondItem.Nama
                holder.itemAvatar2.setImageBitmap(secondItem.img_anak)

                holder.colom2.setOnClickListener {
                    listener?.onItemClick(secondItemIndex)
                }
            } else {
                holder.colom2.visibility = View.GONE
                holder.colom2.setOnClickListener(null) // Menghapus listener jika tidak ada data
            }
        } else {
            holder.colom2.visibility = View.GONE
            holder.colom2.setOnClickListener(null) // Menghapus listener jika di luar indeks
        }
    }



    override fun getItemCount(): Int {
        // Kembalikan jumlah item dalam data list
        return anakListFull.size
    }


    fun setAnakList(anakList: List<DataAnak>) {
        anakListFull = anakList
        anakListFiltered = anakList
        notifyDataSetChanged()
    }
    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val filteredList = mutableListOf<DataAnak>()
                val searchQuery = constraint?.toString()?.toLowerCase()

                if (searchQuery.isNullOrBlank()) {
                    anakListFiltered = anakListFull
                } else {
                    for (anak in anakListFull) {
                        if (anak.Nama.toLowerCase().contains(searchQuery)) {
                            filteredList.add(anak)
                        }
                    }
                    anakListFiltered = filteredList
                }

                val filterResults = FilterResults()
                filterResults.values = anakListFiltered
                return filterResults
            }

            @Suppress("UNCHECKED_CAST")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                anakListFiltered = results?.values as? List<DataAnak> ?: listOf()
                notifyDataSetChanged()
            }
        }
    }
}

