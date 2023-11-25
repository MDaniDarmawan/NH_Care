package com.example.nh_care.activity.anakasuh
import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.nh_care.R
import android.widget.Filter
import android.widget.Filterable
import com.example.nh_care.activity.anakasuh.DataAnak
import com.example.nh_care.databinding.ItemAnakasuhListBinding
import com.google.android.material.imageview.ShapeableImageView


class AnakAdapter(private var anakList: List<DataAnak>) :
    RecyclerView.Adapter<AnakAdapter.AnakViewHolder>() {

    private var listener: OnItemClickListener? = null

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }
    fun setAnakList(list: List<DataAnak>) {
        anakList = list
        notifyDataSetChanged()
    }

    inner class AnakViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val itemName: TextView = itemView.findViewById(R.id.itemName1)
        val itemAvatar: ShapeableImageView = itemView.findViewById(R.id.itemAvatar1)
        val itemName2: TextView = itemView.findViewById(R.id.itemName2)
        val itemAvatar2: ShapeableImageView = itemView.findViewById(R.id.itemAvatar2)

        init {
            // Pindahkan inisialisasi onClickListener ke dalam AnakViewHolder
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    listener?.onItemClick(position)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnakViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_anakasuh_list, parent, false)
        return AnakViewHolder(view)
    }

    override fun onBindViewHolder(holder: AnakViewHolder, position: Int) {
        val currentAnak = anakList[position]

        holder.itemName.text = currentAnak.Nama
        holder.itemName2.text = currentAnak.Nama

        // Assign the images to the ImageViews
        val bitmapImage = currentAnak.img_anak
        holder.itemAvatar.setImageBitmap(bitmapImage)
        holder.itemAvatar2.setImageBitmap(bitmapImage)
    }

    override fun getItemCount(): Int {
        return anakList.size
    }

}
