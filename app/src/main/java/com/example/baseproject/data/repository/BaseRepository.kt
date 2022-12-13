package com.example.baseproject.data.repository

import com.example.baseproject.di.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
open class BaseRepository{
    @Inject @IoDispatcher protected lateinit var ioDispatcher: CoroutineDispatcher
}