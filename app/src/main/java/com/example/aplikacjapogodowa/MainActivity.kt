package com.example.aplikacjapogodowa

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*

class MainActivity : AppCompatActivity()
{
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val editTextMiasto = findViewById<EditText>(R.id.editTextMiasto)
        val buttonSprawdz = findViewById<Button>(R.id.buttonSprawdz)

        buttonSprawdz.setOnClickListener()
        {
            val miasto = editTextMiasto.text.toString();

            val intent = Intent(this, Pogoda::class.java)

            intent.putExtra("miasto", "$miasto")

            startActivity(intent)
        }
    }
}