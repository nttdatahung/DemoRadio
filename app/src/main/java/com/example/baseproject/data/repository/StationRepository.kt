package com.example.baseproject.data.repository

import com.example.baseproject.data.local.daos.RadioStationDao
import com.example.baseproject.data.model.RadioStation
import com.example.baseproject.data.remote.base.Result
import com.example.baseproject.data.remote.datasource.StationRemoteDataSource
import com.example.baseproject.utils.Utils
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StationRepository @Inject constructor(
    private val stationRemoteDataSource: StationRemoteDataSource,
    private val radioStationDao: RadioStationDao
) :BaseRepository(){
    suspend fun getStations() = withContext(ioDispatcher){
        stationRemoteDataSource.getStations(500)
    }

    suspend fun getAllStations() = withContext(ioDispatcher){
        stationRemoteDataSource.getAllStations()
    }

    suspend fun getStationsByCountryCode(countryCode: String) = withContext(ioDispatcher){
        stationRemoteDataSource.getStationsByCountryCode(countryCode)
    }

    suspend fun searchStation(countryCode: String, order: String, limit: Int) = withContext(ioDispatcher){
        stationRemoteDataSource.searchStation(countryCode, order, limit)
    }

    suspend fun getRecommendStations(): Result<List<RadioStation>> = withContext(ioDispatcher){
        stationRemoteDataSource.getStationsByCountryCode(Utils.getLocalCountryCode())
    }

    suspend fun saveToDb(stations: List<RadioStation>) = withContext(ioDispatcher){
        radioStationDao.insertAll(stations)
    }
}