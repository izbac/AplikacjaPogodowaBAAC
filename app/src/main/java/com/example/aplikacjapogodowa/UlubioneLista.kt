package com.example.aplikacjapogodowa

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings.Global
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import androidx.appcompat.app.AlertDialog
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UlubioneLista : AppCompatActivity()
{
    private lateinit var ulubioneDAO : UlubioneDAO
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ulubione_lista)

        val db = UlubioneDatabase.getInstance(this)
        ulubioneDAO = db.ulubioneDao();

        val listViewUlubione = findViewById<ListView>(R.id.listViewUlubione)

        GlobalScope.launch {
            val ulubione = ulubioneDAO.getAll()

            val adapter = ArrayAdapter(this@UlubioneLista, android.R.layout.simple_list_item_1, ulubione.map
            {
                    ulub -> "${ulub.nazwa}"
            })

            withContext(Dispatchers.Main)
            {
                listViewUlubione.adapter = adapter
            }
        }

        val buttonDodaj = findViewById<Button>(R.id.buttonDodajUlubione)

        val builder = AlertDialog.Builder(this)

        buttonDodaj.setOnClickListener()
        {

            builder.setTitle("Wprowadź nazwę miasta")

            val input = EditText(this)
            builder.setView(input)

            builder.setPositiveButton("OK")
            {
                dialog, _ -> val nazwa = input.text.toString()

                if(nazwa != null)
                {
                    var newUlubione = Ulubione(nazwa = nazwa)
                    GlobalScope.launch {
                        ulubioneDAO.insert(newUlubione)

                        val ulubione = ulubioneDAO.getAll()

                        val adapter = ArrayAdapter(this@UlubioneLista, android.R.layout.simple_list_item_1, ulubione.map
                        {
                                ulub -> "${ulub.nazwa}"
                        })

                        withContext(Dispatchers.Main)
                        {
                            listViewUlubione.adapter = adapter
                        }
                    }
                }

                dialog.dismiss()
            }

            builder.setNegativeButton("Anuluj")
            {
                dialog, _ -> dialog.dismiss()
            }
            builder.show()
        }

        listViewUlubione.setOnItemLongClickListener { _, _, position, _ ->
            builder.setMessage("Czy na pewno chcesz usunąć?")

            builder.setPositiveButton("Tak")
            {
                dialog, _ ->
                GlobalScope.launch {
                    val pozycja = ulubioneDAO.getAll()[position]
                    ulubioneDAO.delete(pozycja)

                    val ulubione = ulubioneDAO.getAll()

                    val adapter = ArrayAdapter(this@UlubioneLista, android.R.layout.simple_list_item_1, ulubione.map
                    {
                            ulub -> "${ulub.nazwa}"
                    })

                    withContext(Dispatchers.Main)
                    {
                        listViewUlubione.adapter = adapter
                    }
                }
                dialog.dismiss()
            }
            builder.setNegativeButton("Anuluj")
            {
                dialog, _ -> dialog.dismiss()
            }
            builder.show()
            true
        }

        listViewUlubione.setOnItemClickListener { parent, view, position, id ->
            val pozycja = parent.getItemAtPosition(position).toString()

            val miasto = pozycja

            val intent = Intent(this, Pogoda::class.java)
            intent.putExtra("miasto", miasto)

            startActivity(intent)
        }

    }
}