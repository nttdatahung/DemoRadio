package com.example.baseproject.data.local.daos

import androidx.room.*
import com.example.baseproject.data.model.RadioStation

@Dao
interface RadioStationDao {
    @Query("SELECT * FROM radio_station")
    suspend fun getAll(): List<RadioStation>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(station: RadioStation)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(stations: List<RadioStation>)
}