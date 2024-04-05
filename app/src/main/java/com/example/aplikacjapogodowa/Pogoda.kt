package com.example.aplikacjapogodowa

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*

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
    }
}