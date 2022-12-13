package com.example.baseproject.data.remote.datasource

import com.example.baseproject.data.model.RadioStation
import com.example.baseproject.data.remote.api.RadioApi
import com.example.baseproject.data.remote.base.BaseRemoteService
import com.example.baseproject.data.remote.base.Result
import retrofit2.Response
import retrofit2.http.Path
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StationRemoteDataSource @Inject constructor(
    private val radioApi: RadioApi
): BaseRemoteService() {
    suspend fun getStations(limit: Int): Result<List<RadioStation>>{
        return callApi { radioApi.getStations(limit) }
    }

    suspend fun getAllStations(): Result<List<RadioStation>>{
        return callApi { radioApi.getAllStations() }
    }

    suspend fun getStationsByCountryCode(countryCode: String): Result<List<RadioStation>>{
        return callApi { radioApi.getStationsByCountryCode(countryCode, 50) }
    }

    suspend fun searchStation(countryCode: String, order: String, limit: Int): Result<List<RadioStation>>{
        return callApi { radioApi.searchStation(order, limit, "true") }
    }
}