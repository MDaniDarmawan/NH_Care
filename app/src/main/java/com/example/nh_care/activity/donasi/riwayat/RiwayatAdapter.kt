package com.example.nh_care.activity.donasi.riwayat

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.nh_care.databinding.ItemRiwayatDonasiBinding
import java.text.NumberFormat
import java.util.Locale

class RiwayatAdapter (private var historiList: List<Map<String, String>>) :
        RecyclerView.Adapter<RiwayatAdapter.HistoriViewHolder>() {

        // Listener untuk menangkap klik pada item RecyclerView
        private var listener: OnItemClickListener? = null

        // Interface untuk mendefinisikan metode onItemClick
        interface OnItemClickListener {
            fun onItemClick(position: Int)
        }

        // Metode untuk menetapkan listener
        fun setOnItemClickListener(listener: OnItemClickListener) {
            this.listener = listener
        }

        // Metode untuk mengatur data histori dan memberi tahu adapter bahwa data telah berubah
        fun setHistori(Histori: List<Map<String, String>>) {
            historiList = Histori
            notifyDataSetChanged()
        }

        // Kelas inner ViewHolder untuk setiap item dalam RecyclerView
        inner class HistoriViewHolder(private val binding: ItemRiwayatDonasiBinding) :
            RecyclerView.ViewHolder(binding.root) {

            init {
                // Inisialisasi listener untuk menanggapi klik pada item RecyclerView
                binding.root.setOnClickListener {
                    val position = adapterPosition
                    if (position != RecyclerView.NO_POSITION) {
                        listener?.onItemClick(position)
                    }
                }
            }

            // Metode untuk mengikat data ke tampilan item RecyclerView
            fun bind(histori: Map<String, String>) {
                val textView: TextView = binding.itemJudul
                val textView2: TextView = binding.itemNominal
                val textView3: TextView = binding.itemDate

                // Set teks dari MapelModel ke TextView
                textView.text = histori["keterangan"]

                // Konversi gross_amount ke format mata uang Rupiah
                val grossAmount = histori["gross_amount"]?.toDoubleOrNull() ?: 0.0
                textView2.text = formatToRupiah(grossAmount)

                textView3.text = histori["settlement_time"]
            }

            private fun formatToRupiah(amount: Double): String {
                val formatter = NumberFormat.getCurrencyInstance(Locale("id", "ID")) // Menggunakan locale Indonesia
                val formattedAmount = formatter.format(amount)

                // Menghapus simbol mata uang dan spasi dari format default
                return formattedAmount.replace("Rp", "Rp.")
            }
        }

        // Metode untuk membuat ViewHolder baru
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoriViewHolder {
            // Inflate layout dari item RecyclerView menggunakan data binding
            val binding = ItemRiwayatDonasiBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return HistoriViewHolder(binding)
        }

        // Metode untuk mengikat data pada posisi tertentu ke tampilan holder
        override fun onBindViewHolder(holder: HistoriViewHolder, position: Int) {
            val currentItem = historiList[position]
            holder.bind(currentItem)
        }

        // Metode untuk mengembalikan jumlah item dalam daftar histori
        override fun getItemCount() = historiList.size
    }
