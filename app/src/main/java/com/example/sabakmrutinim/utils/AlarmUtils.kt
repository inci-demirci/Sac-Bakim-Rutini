package com.example.sabakmrutinim.utils

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.example.sabakmrutinim.BildirimReceiver
import java.util.Calendar

object AlarmUtils {

    // Saç tipine göre haftalık bakım planı
    private val bakimPlanlari = mapOf(
        "yağlı" to mapOf(
            Calendar.MONDAY to "Arındırıcı Temizlik",
            Calendar.TUESDAY to "Hafif Maske/Tonik",
            Calendar.WEDNESDAY to "Dinlendirme+Kuru Şampuan",
            Calendar.THURSDAY to "Derin Temizlik+Nem Dengesi",
            Calendar.FRIDAY to "Maske Günü",
            Calendar.SATURDAY to "Isısız Şekillendirme+Sprey",
            Calendar.SUNDAY to "Detoks+Yenileme"
        ),
        "kuru" to mapOf(
            Calendar.MONDAY to "Temizleme+Nemlendirme",
            Calendar.TUESDAY to "Besleyici Maske",
            Calendar.WEDNESDAY to "Dinlendirme+Yağ Bakımı",
            Calendar.THURSDAY to "Temizleme+Hacim",
            Calendar.FRIDAY to "Yoğun Nem Maskesi",
            Calendar.SATURDAY to "Isısız Şekillendirme",
            Calendar.SUNDAY to "Detoks+Nem"
        ),
        "normal" to mapOf(
            Calendar.MONDAY to "Nazik Temizleme+Nem",
            Calendar.TUESDAY to "Besleyici Maske",
            Calendar.WEDNESDAY to "Dinlendirme Günü",
            Calendar.THURSDAY to "Temizleme+Hacim",
            Calendar.FRIDAY to "Yoğun Nemlendirme",
            Calendar.SATURDAY to "Isısız Şekillendirme",
            Calendar.SUNDAY to "Detoks+Canlandırma"
        )
    )

    fun haftalikBakimAlarmlariKur(context: Context, sacTipi: String, saat: Int, dakika: Int) {
        val plan = bakimPlanlari[sacTipi.lowercase()] ?: return
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        for ((gun, mesaj) in plan) {
            val calendar = Calendar.getInstance().apply {
                set(Calendar.DAY_OF_WEEK, gun)
                set(Calendar.HOUR_OF_DAY, saat)
                set(Calendar.MINUTE, dakika)
                set(Calendar.SECOND, 0)
                set(Calendar.MILLISECOND, 0)
                if (before(Calendar.getInstance())) add(Calendar.WEEK_OF_YEAR, 1)
            }

            val intent = Intent(context, BildirimReceiver::class.java).apply {
                putExtra("mesaj", mesaj)
            }

            val pendingIntent = PendingIntent.getBroadcast(
                context,
                gun, // Her gün için farklı requestCode
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )

            alarmManager.setExactAndAllowWhileIdle(
                AlarmManager.RTC_WAKEUP,
                calendar.timeInMillis,
                pendingIntent
            )
        }
    }
}