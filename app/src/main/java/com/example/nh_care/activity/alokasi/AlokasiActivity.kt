package com.example.nh_care.activity.alokasi

import android.content.Intent
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.nh_care.R
import com.example.nh_care.database.DbContract
import com.example.nh_care.databinding.ActivityAlokasiBinding
import com.example.nh_care.databinding.ItemAlokasiListBinding
import com.example.nh_care.fragment.beranda.BerandaFragment
import org.json.JSONArray
import org.json.JSONException
import java.text.NumberFormat
import java.util.Locale

class AlokasiActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAlokasiBinding
    private lateinit var adapter: AlokasiAdapter
    private var totalDonasiSantunan: Double = 0.0
    private var totalDonasiPembangunan: Double = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAlokasiBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Setup RecyclerView
        setupRecyclerView()

        // Fetch total donasi and update the UI
        fetchDataTotalDonasi("http://192.168.1.15/api-mysql-main/api-getTotalSantunan.php") { totalDonasi ->
            totalDonasiSantunan = totalDonasi
            updateRecyclerView()
        }

        fetchDataTotalDonasi("http://192.168.1.15/api-mysql-main/api-getTotalPembangunan.php") { totalDonasi ->
            totalDonasiPembangunan = totalDonasi
            updateRecyclerView()
        }
    }

    private fun fetchDataTotalDonasi(url: String, callback: (Double) -> Unit) {
        // Membuat request JSON menggunakan Volley
        val stringRequest = StringRequest(
            Request.Method.GET, url,
            { response ->
                // Tanggapi respons string dari server
                try {
                    // Coba mengonversi respons menjadi angka
                    val totalDonasi = response.toDouble()

                    // Callback ke fungsi yang diberikan dengan total donasi
                    callback.invoke(totalDonasi)

                } catch (e: NumberFormatException) {
                    Log.e("AlokasiActivity", "Error parsing response to double: ${e.message}")
                }
            },
            { error ->
                // Tanggapi error dari server
                error.printStackTrace()
                Log.e("AlokasiActivity", "Volley error: ${error.message}")
            })

        // Tambahkan request ke antrian Volley
        Volley.newRequestQueue(this).add(stringRequest)
    }

    private fun updateRecyclerView() {
        // Format angka sebagai mata uang Rupiah
        val formattedTotalDonasiSantunan = formatToRupiah(totalDonasiSantunan)
        val formattedTotalDonasiPembangunan = formatToRupiah(totalDonasiPembangunan)

        // Update RecyclerView items with totalDonasi
        val updatedItemList = generateAlokasiList(formattedTotalDonasiSantunan, formattedTotalDonasiPembangunan)
        adapter.updateItemList(updatedItemList)
    }

    private fun formatToRupiah(totalDonasi: Double): String {
        val formatter = NumberFormat.getCurrencyInstance(Locale("id", "ID")) // Menggunakan locale Indonesia
        val formattedAmount = formatter.format(totalDonasi)

        // Menghapus simbol mata uang dan spasi dari format default
        return formattedAmount.replace("Rp", "Rp.")
    }

    private fun generateAlokasiList(totalDonasiSantunan: String, totalDonasiPembangunan: String): List<AlokasiItem> {
        val santunanItem = AlokasiItem("Santunan", "Deskripsi Santunan", R.drawable.comingsoon, totalDonasiSantunan)
        val pembangunanItem = AlokasiItem("Pembangunan", "Deskripsi Pembangunan", R.drawable.comingsoon, totalDonasiPembangunan)
        return listOf(santunanItem, pembangunanItem)
    }

    private fun setupRecyclerView() {
        adapter = AlokasiAdapter(emptyList(), onItemClickListener = { position ->
            // Implementasi logika ketika item diklik
            val intent = Intent(this, DetailAlokasiActivity::class.java)
            val currentItem = adapter.getItem(position)
            intent.putExtra("title", currentItem.title)
            intent.putExtra("description", currentItem.description)
            intent.putExtra("avatarResourceId", currentItem.avatarResourceId)
            intent.putExtra("totalDonasi", currentItem.totalDonasi)
            startActivity(intent)
        })
        binding.rvAlokasi.layoutManager = LinearLayoutManager(this)
        binding.rvAlokasi.adapter = adapter
    }

    data class AlokasiItem(val title: String, val description: String, val avatarResourceId: Int, val totalDonasi: String)

    class AlokasiAdapter(
        private var itemList: List<AlokasiItem>,
        private val onItemClickListener: (Int) -> Unit
    ) : RecyclerView.Adapter<AlokasiViewHolder>() {

        fun updateItemList(newItemList: List<AlokasiItem>) {
            itemList = newItemList
            notifyDataSetChanged()
        }

        fun getItem(position: Int): AlokasiItem {
            return itemList[position]
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlokasiViewHolder {
            val binding =
                ItemAlokasiListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return AlokasiViewHolder(binding, onItemClickListener)
        }

        override fun onBindViewHolder(holder: AlokasiViewHolder, position: Int) {
            val currentItem = itemList[position]
            holder.bind(currentItem)
        }

        override fun getItemCount(): Int {
            return itemList.size
        }
    }

    class AlokasiViewHolder(
        private val binding: ItemAlokasiListBinding,
        private val onItemClickListener: (Int) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: AlokasiItem) {
            binding.titleAlokasi.text = item.title
            binding.logoAlokasi.setImageResource(item.avatarResourceId)
            binding.nominalSantunan.text = item.totalDonasi // TextView untuk menampilkan total donasi
            binding.root.setOnClickListener {
                // Memanggil fungsi onItemClick ketika item diklik
                onItemClickListener.invoke(adapterPosition)
            }
        }
    }
}