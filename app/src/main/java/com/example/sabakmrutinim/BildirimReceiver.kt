package com.example.sabakmrutinim

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat

class BildirimReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent ){
        val mesaj=intent.getStringExtra("mesaj")?:"Bakım Zamanı"
        val notificationManager=context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val channelId="bakim_kanali"

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(channelId, "Bakım Bildirimleri", NotificationManager.IMPORTANCE_HIGH)
            notificationManager.createNotificationChannel(channel)
        }
        val notification = NotificationCompat.Builder(context, channelId)
            .setContentTitle("✨ Bugünkü Bakım ✨")
            .setContentText(mesaj)
            .setSmallIcon(R.drawable.resim)
            .build()

        notificationManager.notify(mesaj.hashCode(), notification)
    }
}