package com.example.berandaberanda.activitity.alokasi

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.nh_care.databinding.ItemAlokasiGambarBinding
import com.example.nh_care.databinding.ItemAlokasiListBinding


const val ITEM_ALOKASI_GAMBAR = 0
const val ITEM_ALOKASI_LIST = 1

class AlokasiAdapter(private val mList: List<DataItem>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    inner class ItemAlokasiGambarViewHolder(private val binding: ItemAlokasiGambarBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindPosterView(dataItem: DataItem) {
            dataItem.poster?.let { binding.posterIv.setImageResource(it) }
            binding.movieTitleTv.text = dataItem.title
            binding.movieDescTv.text = dataItem.desc
        }
    }

    inner class ItemAlokasiListViewHolder(private val binding: ItemAlokasiListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindWithoutPosterView(dataItem: DataItem) {
            binding.movieTitleTv.text = dataItem.title
            dataItem.logo?.let { binding.logoIv.setImageResource(dataItem.logo) }
        }
    }

    override fun getItemViewType(position: Int): Int {

        if (mList[position].poster != null) {
            return ITEM_ALOKASI_GAMBAR
        } else {
            return ITEM_ALOKASI_LIST
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        if (viewType == ITEM_ALOKASI_GAMBAR) {
            val binding =
                ItemAlokasiGambarBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return ItemAlokasiGambarViewHolder(binding)

        } else {
            val binding =
                ItemAlokasiListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return ItemAlokasiListViewHolder(binding)
        }

    }

    override fun getItemCount(): Int {
        return mList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        if (getItemViewType(position) == ITEM_ALOKASI_GAMBAR) {
            (holder as ItemAlokasiGambarViewHolder).bindPosterView(mList[position])
        } else {
            (holder as ItemAlokasiListViewHolder).bindWithoutPosterView(mList[position])
        }
    }
}