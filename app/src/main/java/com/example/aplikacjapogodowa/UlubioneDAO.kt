package com.example.aplikacjapogodowa

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface UlubioneDAO
{
    @Insert
    suspend fun insert(Ulubione: Ulubione)

    @Query("SELECT * FROM Ulubione")
    suspend fun getAll(): List<Ulubione>

    @Delete
    suspend fun delete(Ulubione: Ulubione)
    @Query("DELETE FROM Ulubione WHERE id = :id")
    suspend fun deleteById(id: Int)
}