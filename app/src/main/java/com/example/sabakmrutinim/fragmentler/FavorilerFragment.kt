package com.example.sabakmrutinim.fragmentler

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.sabakmrutinim.adapterler.FavorilerAdapter
import com.example.sabakmrutinim.veriTabani.Favorilerdao
import com.example.sabakmrutinim.veriTabani.VeriTabaniYardimcisi
import com.example.sabakmrutinim.databinding.FragmentFavorilerBinding

class FavorilerFragment : Fragment() {
    private lateinit var binding: FragmentFavorilerBinding
    private lateinit var adapter: FavorilerAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding= FragmentFavorilerBinding.inflate(inflater,container,false)
        val vt= VeriTabaniYardimcisi(requireContext())
        val dao= Favorilerdao()
        val favorilerListe=dao.favorilerUrunleriniGetir(vt)
        adapter= FavorilerAdapter(requireContext(), favorilerListe)
        binding.rvFavoriler.layoutManager= StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        binding.rvFavoriler.adapter=adapter
        return binding.root
    }

}