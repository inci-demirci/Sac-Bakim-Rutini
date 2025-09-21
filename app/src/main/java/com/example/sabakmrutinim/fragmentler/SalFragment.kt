package com.example.sabakmrutinim.fragmentler

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sabakmrutinim.SacRutiniViewModel
import com.example.sabakmrutinim.adapterler.UrunAdapter
import com.example.sabakmrutinim.databinding.FragmentSalBinding
import kotlin.getValue

class SalFragment : Fragment() {
    private lateinit var binding: FragmentSalBinding
    private lateinit var adapter: UrunAdapter
    private var sacTipi: String?=null
    private val viewModel: SacRutiniViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sacTipi = arguments?.getString("sacTipi") ?: "kuru"
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding= FragmentSalBinding.inflate(inflater ,container, false)
        binding.recyclerViewSali.layoutManager = LinearLayoutManager(requireContext())
        val urunListesi=viewModel.getGunlukUrunler(sacTipi!!,"salı")
        adapter = UrunAdapter(
            requireContext(), urunListesi,
            { urun ->
                urun.urunFav = !urun.urunFav
                adapter.notifyDataSetChanged()
            },

            { urun ->
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(urun.urunLink))
                startActivity(intent)
            }
        )

        binding.recyclerViewSali.adapter = adapter
        binding.textViewBaslik.text=when(sacTipi){
            "kuru"->"Besleyici Maske"
            "normal"->"Besleyici Maske"
            "yağlı"->"Hafif Maske/Tonik"
            else -> ""
        }
        return binding.root

    }



}
