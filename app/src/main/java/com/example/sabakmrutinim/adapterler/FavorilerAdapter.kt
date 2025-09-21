package com.example.sabakmrutinim.adapterler

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.sabakmrutinim.veriTabani.FavorilerDataClass
import com.example.sabakmrutinim.veriTabani.Favorilerdao
import com.example.sabakmrutinim.R
import com.example.sabakmrutinim.veriTabani.VeriTabaniYardimcisi

class FavorilerAdapter(private val mContext: Context, private val favorilerListesi:MutableList<FavorilerDataClass>):RecyclerView.Adapter<FavorilerAdapter.cardFavorilerTasarimTutucu>()   {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): cardFavorilerTasarimTutucu {
        val tasarim= LayoutInflater.from(mContext).inflate(R.layout.card_favoriler_tasarim,parent,false)
        return cardFavorilerTasarimTutucu(tasarim)

    }

    override fun onBindViewHolder(holder: cardFavorilerTasarimTutucu, position: Int) {
        val urun=favorilerListesi[position]
        holder.textViewFav.text=urun.favoriler_ad
        holder.imageViewFav.setImageResource(mContext.resources.getIdentifier(urun.favoriler_resim,"drawable",mContext.packageName))
        holder.buttonFavKaldır.setOnClickListener {
            val favoriler=favorilerListesi[position]
            val vt= VeriTabaniYardimcisi(mContext)
            val dao= Favorilerdao()
            val silindiMi=dao.favorilerUrunSil(vt,favoriler.favoriler_id)
            if (silindiMi){
                ürünSil(position)
                Toast.makeText(mContext,"Ürün favorilerden silindi", Toast.LENGTH_SHORT).show()


            }

        }

    }

    override fun getItemCount(): Int {
        return favorilerListesi.size
    }
    fun ürünSil(position: Int){
        favorilerListesi.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position,favorilerListesi.size)
    }

    inner class cardFavorilerTasarimTutucu(view: View): RecyclerView.ViewHolder(view){
        var imageViewFav: ImageView=view.findViewById(R.id.imageViewFav)
        var textViewFav: TextView=view.findViewById(R.id.textViewFav)
        var buttonFavKaldır: Button=view.findViewById(R.id.buttonFavKaldır)

        init {
            imageViewFav=view.findViewById(R.id.imageViewFav)
            textViewFav=view.findViewById(R.id.textViewFav)
            buttonFavKaldır=view.findViewById(R.id.buttonFavKaldır)

        }

    }

}