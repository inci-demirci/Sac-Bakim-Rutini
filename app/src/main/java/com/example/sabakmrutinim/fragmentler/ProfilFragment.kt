package com.example.sabakmrutinim.fragmentler

import android.app.AlarmManager
import android.app.AlertDialog
import android.app.PendingIntent
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.sabakmrutinim.MainActivity
import com.example.sabakmrutinim.R
import com.example.sabakmrutinim.utils.AlarmUtils
import com.google.android.material.snackbar.Snackbar
import java.util.Calendar
import java.util.Locale


class ProfilFragment : Fragment() {

    private lateinit var textViewSacTipi: TextView
    private lateinit var textViewBildirimSaati: TextView
    private lateinit var textViewHedef: TextView
    private lateinit var textViewOneriBaslik: TextView
    private lateinit var textViewOneriIcerik: TextView
    private lateinit var textViewTema: TextView
    private lateinit var cardOneriler: androidx.cardview.widget.CardView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_profil, container, false)

        textViewSacTipi = view.findViewById(R.id.textViewSacTipi)
        textViewBildirimSaati = view.findViewById(R.id.textViewBildirimSaati)
        textViewHedef = view.findViewById(R.id.textViewHedef)
        textViewOneriBaslik = view.findViewById(R.id.textViewOneriBaslik)
        textViewOneriIcerik=view.findViewById(R.id.textViewOneriIcerik)
        cardOneriler = view.findViewById(R.id.cardOneriler)
        textViewTema=view.findViewById(R.id.textViewTemaa)

        val layoutSacTipi = view.findViewById<LinearLayout>(R.id.layoutSacTipi)
        val layoutHedef = view.findViewById<LinearLayout>(R.id.layoutHedef)
        val layoutTema=view.findViewById<LinearLayout>(R.id.layoutTema)
        val layoutBildirimSaati = view.findViewById<LinearLayout>(R.id.layoutBildirimSaati)

        val sp = requireContext().getSharedPreferences("KULLANICI_PREFS", Context.MODE_PRIVATE)
        textViewSacTipi.text = sp.getString("sacTipi", "normal")
        textViewHedef.text = sp.getString("hedef", "Parlaklık")
        textViewTema.text = sp.getString("tema", "Aydınlık")
        val saat = sp.getInt("bildirimSaat", 9)
        val dakika = sp.getInt("bildirimDakika", 0)
        textViewBildirimSaati.text = String.format("%02d:%02d", saat, dakika)

        val secilenSacTipi = sp.getString("sacTipi", "normal") ?: "normal"
        AlarmUtils.haftalikBakimAlarmlariKur(requireContext(), secilenSacTipi, saat, dakika)

        // Saç tipi seçme
        layoutSacTipi.setOnClickListener {
            val secenekler = arrayOf("kuru", "yağlı", "normal")
            AlertDialog.Builder(requireContext())
                .setTitle("Saç Tipini Seç")
                .setItems(secenekler) { _, which ->
                    val secilen = secenekler[which]
                    textViewSacTipi.text = secilen
                    sp.edit().putString("sacTipi", secilen.lowercase(Locale.ROOT)).apply()
                    parentFragmentManager.setFragmentResult("sacTipiDegisti",
                        bundleOf("yeniSacTipi" to secilen.lowercase())
                    )
                    val intent = Intent(requireContext(), MainActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intent)
                    requireActivity().finish()


                }
                .show()
        }
        layoutTema.setOnClickListener {
            val secenekler = arrayOf("Aydınlık", "Karanlık")
            AlertDialog.Builder(requireContext())
                .setTitle("Tema Seç")
                .setItems(secenekler) { _, which ->
                    val secilen = secenekler[which]
                    textViewTema.text = secilen

                    val editor = sp.edit()
                    if (secilen == "Aydınlık") {
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                        editor.putString("tema", "Aydınlık")
                    } else {
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                        editor.putString("tema", "Karanlık")
                    }
                    editor.apply()

                    // Tema değişikliği için activity yenile
                    val intent = Intent(requireContext(), MainActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intent)
                    requireActivity().finish()
                }
                .show()
        }
        layoutBildirimSaati.setOnClickListener {
            val calendar = Calendar.getInstance()
            val saat = calendar.get(Calendar.HOUR_OF_DAY)
            val dakika = calendar.get(Calendar.MINUTE)

            TimePickerDialog(requireContext(), { _, selectedHour, selectedMinute ->
                val secilenSaat = String.format("%02d:%02d", selectedHour, selectedMinute)
                textViewBildirimSaati.text = secilenSaat

                // SharedPreferences'a kaydet
                sp.edit()
                    .putInt("bildirimSaat", selectedHour)
                    .putInt("bildirimDakika", selectedMinute)
                    .apply()

                // Alarm kur
                val secilenSacTipi = sp.getString("sacTipi", "normal") ?: "normal"
                sp.edit()
                    .putString("sacTipi", secilenSacTipi.lowercase())
                    .putInt("bildirimSaat", selectedHour)
                    .putInt("bildirimDakika", selectedMinute)
                    .apply()
                AlarmUtils.haftalikBakimAlarmlariKur(requireContext(), secilenSacTipi, selectedHour, selectedMinute)

                Snackbar.make(requireView(), "Bildirimler ayarlandı: $secilenSacTipi saç için $secilenSaat", Snackbar.LENGTH_SHORT).show()

            }, saat, dakika, true).show()
        }

        layoutHedef.setOnClickListener {
            val secenekler = arrayOf("Parlaklık", "Hacim","Saç Dökülmesini Önleme","Yağ Seviyesini Düzenleme",
                "Nem Dengeleme","Elektriklenmeyi Önleme","Kırık Onarım","Hızlı Uzama")
            AlertDialog.Builder(requireContext())
                .setTitle("Hedefini Seç")
                .setItems(secenekler) { _, which ->
                    val secilen = secenekler[which]
                    textViewHedef.text = secilen
                    sp.edit().putString("hedef", secilen).apply()
                    cardOneriler.visibility = View.VISIBLE
                    textViewOneriBaslik.text = "Öneriler - $secilen"
                    textViewOneriIcerik.text = getOnerilerForHedef(secilen)
                }
                .show()
        }
        return view
    }

    private fun getOnerilerForHedef(hedef: String): String {
        return when (hedef) {
            "Parlaklık" -> "•Soğuk suyla durulama: Son yıkama suyunu soğuk yap, saç kutiküllerini kapat.\n•Doğal yağ maskeleri: Argan, hindistan cevizi veya zeytinyağı haftada 1-2 kez uygula.\n•Isı koruyucu kullanımı: Fön veya düzleştirici öncesi koruyucu uygula.\n•Sülfatsız şampuan: Paraben ve sülfat içermeyen ürünler kullan."
            "Hacim" -> "•Katlı kesim tercih et: Özellikle ince telli saçlarda hacim kazandırır.\n•Baş aşağı kurutma: Saç diplerine hacim verir, düşük ısıda kurut.\n•Hacim veren şampuanlar: Hafif formüllü, silikon içermeyen ürünler kullan.\n•Beslenme: Biotin, çinko ve omega-3 içeren gıdalar (ceviz, somon, avokado) saç köklerini güçlendirir."
            "Saç Dökülmesini Önleme" -> "•Biotin ve çinko takviyesi: Eksikliği dökülmeye neden olabilir.\n•Masajla kan dolaşımını artır: Parmak uçlarıyla 5 dk günlük masaj.\n•Anti-dökülme serumları: MinoXsi gibi profesyonel serumlar etkili olabilir.\n•Stresten uzak dur: Kortizol artışı saç dökülmesini tetikler."
            "Yağ Seviyesini Düzenleme" -> "•Yıkama sıklığını dengele: Her gün yıkamak yağ üretimini artırabilir.\n•Kil maskesi (özellikle saç derisine): Yağ emici özelliğiyle denge sağlar.\n•Silikon içermeyen ürünler: Gözenekleri tıkamaz, sebum dengesi korunur."
            "Nem Dengeleme" -> "•Nemlendirici saç kremleri: Özellikle gliserin ve panthenol içerenler.\n•Gece bakım yağı: Hafif yapılı yağlarla gece nem takviyesi yap.\n•Yeterli su tüketimi: İçten nemlendirme ihmal edilmemeli."
            "Elektriklenmeyi Önleme" ->"•Doğal kıllı fırça kullan: Statik elektriği azaltır.\n•Nemlendirici spreyler: Aloe vera bazlı ürünler etkili.\n•Sentetik kumaşlardan kaçın: Şapka, yastık kılıfı gibi temas yüzeyleri önemli."
            "Kırık Onarım" ->"•Düzenli uç kesimi: 6-8 haftada bir kırıkları aldır.\n•Protein içerikli maskeler: Keratin ve elastin içeren ürünler kırıkları onarır.\n•Isıdan uzak dur: Düzleştirici ve maşa kullanımını azalt."
            "Hızlı Uzama" -> "•Kan dolaşımını artıran yağlar: Biberiye, nane yağı gibi uyarıcılar.\n•Düzenli saç masajı: Saç köklerini aktive eder.\n•Yavaş uzayan saçlar için kompleksler: La Rachel gibi bitkisel kompleksler etkili olabilir."
            else -> "Seçilen hedef için öneriler burada görünecek."
        }
    }
}

