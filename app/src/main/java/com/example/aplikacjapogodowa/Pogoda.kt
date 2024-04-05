package com.example.aplikacjapogodowa

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import org.w3c.dom.Text
import java.lang.Exception
import java.net.URL
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class Pogoda : AppCompatActivity()
{
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pogoda)

        val apiKey = "d863df7803cd85b7d407d171a5b7611e"

        val miasto = intent.getStringExtra("miasto")

        val textViewMiasto = findViewById<TextView>(R.id.textViewMiasto)
        textViewMiasto.text = miasto

        val textViewCzas = findViewById<TextView>(R.id.textViewCzas)
        val textViewLokalizacja = findViewById<TextView>(R.id.textViewLokalizacja)
        val textViewPogoda = findViewById<TextView>(R.id.textViewPogoda)
        val textViewTemp = findViewById<TextView>(R.id.textViewTemp)
        val textViewOdczuwalna = findViewById<TextView>(R.id.textViewOdczuwalna)
        val textViewTempMin = findViewById<TextView>(R.id.textViewTempMin)
        val textViewTempMax = findViewById<TextView>(R.id.textViewTempMax)
        val textViewWschod  = findViewById<TextView>(R.id.textViewWschod)
        val textViewZachod = findViewById<TextView>(R.id.textViewZachod)
        val textViewWiatr = findViewById<TextView>(R.id.textViewWiatr)
        val textViewWilgotnosc = findViewById<TextView>(R.id.textViewWilgotnosc)
        val textViewZachmurzenie = findViewById<TextView>(R.id.textViewZachmurzenie)


        GlobalScope.launch(Dispatchers.Main)
        {
            try
            {
                val odp = withContext(Dispatchers.IO)
                {
                    URL("https://api.openweathermap.org/data/2.5/weather?q=$miasto&units=metric&appid=$apiKey").readText(Charsets.UTF_8)
                }

                val json = JSONObject(odp)
                val main = json.getJSONObject("main")
                val sys = json.getJSONObject("sys")

                val czas: Long = json.getLong("dt")
                textViewCzas.text = SimpleDateFormat("dd/MM/yyyy, hh:mm a", Locale.ENGLISH).format(Date(czas * 1000))

                val lokalizacja = json.getString("name") + ", " + sys.getString("country")
                textViewLokalizacja.text = lokalizacja

                val pogodaArr = json.getJSONArray("weather")
                val pogoda = pogodaArr.getJSONObject(0)
                textViewPogoda.text = pogoda.getString("main")

                val temp = main.getString("temp") + "°C"
                textViewTemp.text = temp

                val odczuwalna = main.getString("feels_like") + "°C"
                textViewOdczuwalna.text = odczuwalna

                val tempMin = main.getString("temp_min") + "°C"
                textViewTempMin.text = tempMin

                val tempMax = main.getString("temp_max") + "°C"
                textViewTempMax.text = tempMax

                val wschod: Long = sys.getLong("sunrise")
                textViewWschod.text = SimpleDateFormat("hh:mm a", Locale.ENGLISH).format(Date(wschod * 1000))

                val zachod: Long = sys.getLong("sunset")
                textViewZachod.text = SimpleDateFormat("hh:mm a", Locale.ENGLISH).format(Date(zachod * 1000))

                val wiatr = json.getJSONObject("wind")
                textViewWiatr.text = wiatr.getString("speed") + " km/h"

                val wilgotnosc = main.getString("humidity") + "%"
                textViewWilgotnosc.text = wilgotnosc

                val zachmurzenie = json.getJSONObject("clouds")
                textViewZachmurzenie.text = zachmurzenie.getString("all") + "%"
            }
            catch (e: Exception)
            {
                //toast o nieudanym pobraniu danych
            }
        }

    }

}