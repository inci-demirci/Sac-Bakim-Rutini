package com.example.sabakmrutinim.fragmentler

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.example.sabakmrutinim.MainActivity
import com.example.sabakmrutinim.R
import com.example.sabakmrutinim.databinding.FragmentRutinBinding
import com.example.sabakmrutinim.utils.AlarmUtils

class RutinFragment : Fragment() {
    private lateinit var binding: FragmentRutinBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentRutinBinding.inflate(inflater, container, false)
        val sharedPref = requireContext().getSharedPreferences("KULLANICI_PREFS", Context.MODE_PRIVATE)

        binding.materialCardViewKuru.setOnClickListener {
            sharedPref.edit().putString("sacTipi", "kuru").apply()
            Navigation.findNavController(it).navigate(R.id.action_rutinFragment_to_kuruSacFragment)
        }
        binding.materialCardViewYagli.setOnClickListener {
            sharedPref.edit().putString("sacTipi", "yağlı").apply()
            Navigation.findNavController(it).navigate(R.id.action_rutinFragment_to_yagliSacFragment)
        }
        binding.materialCardViewNormal.setOnClickListener {
            sharedPref.edit().putString("sacTipi", "normal").apply()
            Navigation.findNavController(it).navigate(R.id.action_rutinFragment_to_normalSacFragment)
        }


        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        // Profil ekranından gelen saç tipi değişimini dinle
        parentFragmentManager.setFragmentResultListener(
            "sacTipiDegisti",
            viewLifecycleOwner
        ) { _, result ->
            val yeniSacTipi = result.getString("yeniSacTipi") ?: return@setFragmentResultListener
            val navController = Navigation.findNavController(requireView())
            when (yeniSacTipi) {
                "kuru" -> navController.navigate(R.id.kuruSacFragment)
                "yağlı" -> navController.navigate(R.id.yagliSacFragment)
                "normal" -> navController.navigate(R.id.normalSacFragment)
            }
        }
    }
}


