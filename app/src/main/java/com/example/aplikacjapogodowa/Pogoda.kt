package com.example.aplikacjapogodowa

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
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

        val textViewLokalizacja = findViewById<TextView>(R.id.textViewLokalizacja)
        val textViewCzas = findViewById<TextView>(R.id.textViewCzas)
        val textViewTemp = findViewById<TextView>(R.id.textViewTemp)
        val textViewWschod  = findViewById<TextView>(R.id.textViewWschod)
        val textViewZachod = findViewById<TextView>(R.id.textViewZachod)

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

                val temp = main.getString("temp") + "°C"
                textViewTemp.text = temp

                val wschod: Long = sys.getLong("sunrise")
                textViewWschod.text = SimpleDateFormat("hh:mm a", Locale.ENGLISH).format(Date(wschod * 1000))

                val zachod: Long = sys.getLong("sunset")
                textViewZachod.text = SimpleDateFormat("hh:mm a", Locale.ENGLISH).format(Date(zachod * 1000))

                //dodac dodatkowe informacje - temp min/max, odczuwalna, wiatr itd.
            }
            catch (e: Exception)
            {
                //toast o nieudanym pobraniu danych
            }
        }

    }

}