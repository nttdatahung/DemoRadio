package com.example.baseproject.di

import android.content.Context
import androidx.room.Room
import com.example.baseproject.data.local.RoomDB
import com.example.baseproject.data.local.daos.EmailDao
import com.example.baseproject.data.local.daos.AccountDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    @Singleton
    fun provideRoomDB(@ApplicationContext appContext: Context): RoomDB{
        return Room.databaseBuilder(appContext, RoomDB::class.java, "my_room_db")
            .build()
    }

    @Provides
    fun provideUserDao(roomDB: RoomDB): AccountDao{
        return roomDB.accountDao()
    }

    @Provides
    fun provideEmailDao(roomDB: RoomDB): EmailDao{
        return roomDB.emailDao()
    }
}