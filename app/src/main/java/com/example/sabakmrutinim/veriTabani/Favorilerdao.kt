package com.example.sabakmrutinim.veriTabani

import android.content.ContentValues

class Favorilerdao {
    fun favorilereUrunEkle(vt: VeriTabaniYardimcisi,  urun_ad: String, urun_resim: String) {
        val db = vt.writableDatabase
        val values = ContentValues()
        values.put("urun_ad", urun_ad)
        values.put("urun_resim", urun_resim)
        val cursor=db.rawQuery("SELECT * FROM urunler WHERE urun_ad=?", arrayOf(urun_ad))
        if(cursor.count==0){
            db.insertOrThrow("urunler", null, values)
            db.close()
        }

    }
    fun favorilerUrunSil(vt: VeriTabaniYardimcisi, urun_id: Int): Boolean {
        val db = vt.writableDatabase
        val sonuc = db.delete("urunler", "urun_id=?", arrayOf(urun_id.toString()))
        db.close()
        return sonuc > 0
    }
    fun favorilerUrunleriniGetir(vt: VeriTabaniYardimcisi): ArrayList<FavorilerDataClass> {
        val favorilerListe = ArrayList<FavorilerDataClass>()
        val db = vt.writableDatabase
        val cursor = db.rawQuery("SELECT * FROM urunler", null)
        while (cursor.moveToNext()) {
            val favoriler = FavorilerDataClass(
                cursor.getInt(cursor.getColumnIndexOrThrow("urun_id")),
                cursor.getString(cursor.getColumnIndexOrThrow("urun_resim")),
                cursor.getString(cursor.getColumnIndexOrThrow("urun_ad"))
            )
            favorilerListe.add(favoriler)
        }
        return favorilerListe


    }
}