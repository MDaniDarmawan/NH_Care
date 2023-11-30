package com.example.nh_care.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.nh_care.R
import com.example.nh_care.databinding.ActivityMainBinding
import com.example.nh_care.fragment.beranda.BerandaFragment
import com.example.nh_care.fragment.layanan.LayananFragment
import com.example.nh_care.fragment.profile.ProfileFragment


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        replaceFragment(BerandaFragment())

        binding.navView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.navigation_layanan -> replaceFragment(LayananFragment()) // Pilihan "Home" dipilih, ganti tampilan dengan fragmen Home
                R.id.navigation_beranda -> replaceFragment(BerandaFragment()) // Pilihan "Profile" dipilih, ganti tampilan dengan fragmen Profile
                R.id.navigation_profile -> replaceFragment(ProfileFragment()) // Pilihan "Favorite" dipilih, ganti tampilan dengan fragmen Favorite
                else -> {
                    // Handle pilihan lainnya (jika ada)
                }
            }
            true // Mengembalikan nilai true untuk menunjukkan bahwa perubahan item telah ditangani
        }
    }

    // Fungsi untuk mengganti fragmen dalam layout
    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout, fragment) // Ganti fragmen dalam FrameLayout dengan yang baru
        fragmentTransaction.commit() // Terapkan perubahan fragmen
    }
}








//        val navView: BottomNavigationView = binding.navView
//
//        val navController = findNavController(R.id.nav_host_fragment_activity_main)
//        // Passing each menu ID as a set of Ids because each
//        // menu should be considered as top level destinations.
//        val appBarConfiguration = AppBarConfiguration(
//            setOf(
//                R.id.navigation_profile, R.id.navigation_beranda, R.id.navigation_layanan
//            )
//        )
//        setupActionBarWithNavController(navController, appBarConfiguration)
//        navView.setupWithNavController(navController)
//    }
//}