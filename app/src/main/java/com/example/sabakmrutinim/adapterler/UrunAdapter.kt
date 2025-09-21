package com.example.sabakmrutinim.adapterler

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.sabakmrutinim.veriTabani.Favorilerdao
import com.example.sabakmrutinim.R
import com.example.sabakmrutinim.Urunler
import com.example.sabakmrutinim.veriTabani.VeriTabaniYardimcisi


class UrunAdapter(private val mContext: Context, private val urunListesi:List<Urunler>, private val onFavoriClick: (Urunler) -> Unit,
                  private val onLinkClick: (Urunler) -> Unit): RecyclerView.Adapter<UrunAdapter.cardTasarimTutucu>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): cardTasarimTutucu {
        val tasarim= LayoutInflater.from(mContext).inflate(R.layout.card_urun_tasarim,parent,false)
        return cardTasarimTutucu(tasarim)

    }

    override fun onBindViewHolder(holder: cardTasarimTutucu, position: Int) {
        val urun=urunListesi[position]
        holder.textViewUrunAd.text=urun.urunAd
        holder.textViewUrunAciklama.text=urun.urunAciklama
        holder.imageViewUrunResim.setImageResource(mContext.resources.getIdentifier(urun.urunResim,"drawable",mContext.packageName))


        val vt= VeriTabaniYardimcisi(mContext)
        holder.buttonFavori.setOnClickListener {
            onFavoriClick(urun)
            val basarili= Favorilerdao().favorilereUrunEkle(vt, urun.urunAd,urun.urunResim)
            if(basarili== Favorilerdao().favorilereUrunEkle(vt, urun.urunAd,urun.urunResim)){
                Toast.makeText(mContext,"Ürün favorilere eklendi", Toast.LENGTH_SHORT).show()
            }



        }
        holder.buttonLink.setOnClickListener { onLinkClick(urun) }



    }

    override fun getItemCount(): Int {
        return urunListesi.size
    }

    inner class cardTasarimTutucu(view: View):RecyclerView.ViewHolder(view){
        var imageViewUrunResim: ImageView
        var textViewUrunAd: TextView
        var textViewUrunAciklama: TextView
        var buttonLink: Button
        var buttonFavori: ImageButton

        init {
            imageViewUrunResim= view.findViewById(R.id.imageViewUrunResim)
            textViewUrunAd= view.findViewById(R.id.textViewUrunAd)
            textViewUrunAciklama= view.findViewById(R.id.textViewUrunAciklama)
            buttonLink= view.findViewById(R.id.buttonLink)
            buttonFavori= view.findViewById(R.id.buttonFavori)
        }

    }


}