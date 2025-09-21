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
import com.example.sabakmrutinim.databinding.FragmentPerBinding
import kotlin.getValue

class PerFragment : Fragment() {
    private lateinit var binding: FragmentPerBinding
    private lateinit var adapter: UrunAdapter
    private var sacTipi: String?=null
    private val viewModel: SacRutiniViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sacTipi = arguments?.getString("sacTipi") ?: "kuru"
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentPerBinding.inflate(inflater, container, false)
        binding.recyclerViewPersembe.layoutManager = LinearLayoutManager(requireContext())

        val urunListesi=viewModel.getGunlukUrunler(sacTipi!!,"perşembe")
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
        binding.recyclerViewPersembe.adapter=adapter
        binding.textViewBaslik.text=when(sacTipi){
            "kuru"->"Temizleme+Hacim"
            "normal"->"Temizleme+Hacim"
            "yağlı"->"Derin Temizlik+Nem Dengesi"
            else -> ""
        }
        return binding.root
    }
}