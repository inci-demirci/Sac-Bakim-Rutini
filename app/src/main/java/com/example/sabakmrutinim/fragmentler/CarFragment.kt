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
import com.example.sabakmrutinim.databinding.FragmentCarBinding
import kotlin.getValue

class CarFragment : Fragment() {
    private lateinit var binding: FragmentCarBinding
    private lateinit var adapter: UrunAdapter
    private var sacTipi: String?=null
    private val viewModel: SacRutiniViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sacTipi = arguments?.getString("sacTipi") ?: "kuru"

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentCarBinding.inflate(inflater, container, false)
        binding.recyclerViewCarsamba.layoutManager = LinearLayoutManager(requireContext())

        val urunListesi=viewModel.getGunlukUrunler(sacTipi!!,"çarşamba")
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
        binding.recyclerViewCarsamba.adapter=adapter
        binding.textViewBaslik.text=when(sacTipi){
            "kuru"->"Dinlendirme+Yağ Bakımı"
            "normal"->"Dinlendirme Günü"
            "yağlı"->"Dinlendirme+Kuru Şampuan"
            else -> " "
        }
        return binding.root
    }
}