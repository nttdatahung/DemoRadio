package com.example.baseproject.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.baseproject.data.local.daos.EmailDao
import com.example.baseproject.data.local.daos.AccountDao
import com.example.baseproject.data.local.daos.RadioStationDao
import com.example.baseproject.data.local.entities.AccountEntity
import com.example.baseproject.data.model.EmailObject
import com.example.baseproject.data.model.RadioStation

@Database(entities = [AccountEntity::class, EmailObject::class, RadioStation::class], version = 1)
abstract class RoomDB: RoomDatabase() {
    abstract fun accountDao(): AccountDao
    abstract fun emailDao(): EmailDao
    abstract fun radioStationDao(): RadioStationDao
}