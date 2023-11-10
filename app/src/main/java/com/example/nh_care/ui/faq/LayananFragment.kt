package com.example.nh_care.ui.faq

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.nh_care.databinding.FragmentTanyaBinding

class LayananFragment : Fragment() {

    private var _binding: FragmentTanyaBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val tanyaViewModel =
            ViewModelProvider(this).get(LayananViewModel::class.java)

        _binding = FragmentTanyaBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.text
        tanyaViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
