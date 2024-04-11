package com.example.aplikacjapogodowa

import android.content.Context
import kotlin.jvm.Volatile
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Ulubione::class], version = 1, exportSchema = false)
abstract class UlubioneDatabase : RoomDatabase()
{
    abstract fun ulubioneDao(): UlubioneDAO
    companion object
    {
        private const val DATABASE_NAME = "my_database"

        @Volatile
        private var INSTANCE: UlubioneDatabase? = null

        fun getInstance(context: Context): UlubioneDatabase =
            INSTANCE ?: synchronized(this)
            {
                INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
            }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(context.applicationContext,
                UlubioneDatabase::class.java, DATABASE_NAME)
                .build()
    }
}