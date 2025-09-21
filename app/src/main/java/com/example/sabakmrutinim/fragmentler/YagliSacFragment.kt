package com.example.sabakmrutinim.fragmentler

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.sabakmrutinim.databinding.FragmentYagliSacBinding
import com.google.android.material.tabs.TabLayoutMediator
import kotlin.getValue

class YagliSacFragment : Fragment() {
    private lateinit var binding: FragmentYagliSacBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentYagliSacBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun createGunlukFragment(fragment: Fragment, sacTipi: String): Fragment {
        fragment.arguments = Bundle().apply {
            putString("sacTipi", sacTipi)
        }
        return fragment

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val sharedPref = requireContext().getSharedPreferences("KULLANICI_PREFS", Context.MODE_PRIVATE)
        val sacTipi = sharedPref.getString("sacTipi", "yağlı") ?: "yağlı"
        val fragments = listOf(
            createGunlukFragment(PztFragment(), sacTipi),
            createGunlukFragment(SalFragment(), sacTipi),
            createGunlukFragment(CarFragment(), sacTipi),
            createGunlukFragment(PerFragment(), sacTipi),
            createGunlukFragment(CumFragment(), sacTipi),
            createGunlukFragment(CtsFragment(), sacTipi),
            createGunlukFragment(PazFragment(), sacTipi)
        )
        val days = listOf("PZT", "SAL", "ÇAR", "PER", "CUM", "CTS", "PAZ")
        binding.viewPager.adapter = object : FragmentStateAdapter(this) {
            override fun getItemCount() = fragments.size
            override fun createFragment(position: Int) = fragments[position]
        }

        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = days[position]
        }.attach()
    }
}