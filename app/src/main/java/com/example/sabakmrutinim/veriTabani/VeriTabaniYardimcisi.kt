package com.example.sabakmrutinim.veriTabani

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class VeriTabaniYardimcisi(context: Context ): SQLiteOpenHelper(context,"sacbakimrutinim.sql",null,1) {
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL("CREATE TABLE IF NOT EXISTS urunler(urun_id INTEGER PRIMARY KEY AUTOINCREMENT, urun_ad TEXT, urun_resim TEXT);")


    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        db?.execSQL("DROP TABLE IF EXISTS urunler")
        onCreate(db)

    }
}