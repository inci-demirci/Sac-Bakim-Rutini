package com.example.sabakmrutinim

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import com.example.sabakmrutinim.databinding.ActivityMainBinding
import com.example.sabakmrutinim.fragmentler.RutinFragment
import com.example.sabakmrutinim.utils.AlarmUtils

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private var navHostFragment: NavHostFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        val sp = getSharedPreferences("KULLANICI_PREFS", Context.MODE_PRIVATE)
        when (sp.getString("tema", "Aydınlık")) {
            "Aydınlık" -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            "Karanlık" -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        }
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val bildirimSaat = sp.getInt("bildirimSaat", -1)
        val bildirimDakika = sp.getInt("bildirimDakika", -1)
        val sacTipi = sp.getString("sacTipi", "normal") ?: "normal"

        if (bildirimSaat != -1 && bildirimDakika != -1) {
            // AlarmUtils içindeki fonksiyon senin haftalık alarm kurma fonksiyonun
            AlarmUtils.haftalikBakimAlarmlariKur(this, sacTipi, bildirimSaat, bildirimDakika)
        }

        navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        navController = navHostFragment!!.navController
        NavigationUI.setupWithNavController(binding.bottomNavigationView, navController)
        if (sacTipi != null) {
            when (sacTipi) {
                "kuru" -> navController.navigate(R.id.kuruSacFragment)
                "yağlı" -> navController.navigate(R.id.yagliSacFragment)
                "normal" -> navController.navigate(R.id.normalSacFragment)
            }
        } else {
            navController.navigate(R.id.rutinFragment)
        }

    }
    fun updateRutin() {
        val sacTipi = getSharedPreferences("KULLANICI_PREFS", MODE_PRIVATE).getString("sacTipi", "normal")
        when (sacTipi) {
            "kuru" -> navController.navigate(R.id.kuruSacFragment)
            "yağlı" -> navController.navigate(R.id.yagliSacFragment)
            "normal" -> navController.navigate(R.id.normalSacFragment)
        }
    }
}

