package com.example.nh_care.fragment.layanan

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.example.nh_care.database.DbContract
import com.example.nh_care.databinding.FragmentLayananBinding
import org.json.JSONArray
import org.json.JSONException

class LayananFragment : Fragment() {

    private var _binding: FragmentLayananBinding? = null
    private val binding get() = _binding!!

    private lateinit var layananAdapter: LayananAdapter // Tambahkan adapter sebagai properti
    private val programList = mutableListOf<DataLayanan>() // Gunakan DataLayanan untuk menyimpan data

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLayananBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // Inisialisasi RecyclerView dan adapter
        layananAdapter = LayananAdapter(programList)
        binding.rvLayanan.adapter = layananAdapter
         binding.rvLayanan.layoutManager = LinearLayoutManager(requireContext())

        fetchProgramDataFromAPI() // Panggil fungsi untuk mengambil data dari MySQL

        return root
    }

    private fun fetchProgramDataFromAPI() {
        val urlDataProgram = DbContract.urlFaq

        val jsonArrayRequest = JsonArrayRequest(
            Request.Method.GET, urlDataProgram, null,
            { response ->
                try {
                    val fetchedProgramList = parsePrograms(response)
                    programList.clear()
                    programList.addAll(fetchedProgramList)
                    layananAdapter.notifyDataSetChanged() // Beritahu adapter bahwa data telah berubah
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            },
            { error ->
                error.printStackTrace()
            })

        // Gunakan Volley untuk mengirim permintaan
        Volley.newRequestQueue(requireContext()).add(jsonArrayRequest)
    }

    private fun parsePrograms(jsonArray: JSONArray): List<DataLayanan> {
        val layanans = mutableListOf<DataLayanan>()

        for (i in 0 until jsonArray.length()) {
            val programObject = jsonArray.getJSONObject(i)

            val pertanyaan = programObject.getString("pertanyaan")
            val jawaban = programObject.getString("jawaban")

            val layanan = DataLayanan(pertanyaan, jawaban)
            layanans.add(layanan)
        }

        return layanans
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

