package com.example.baseproject.data.remote.api

import com.example.baseproject.data.local.entities.AccountEntity
import com.example.baseproject.data.model.MarPhoto
import com.example.baseproject.data.model.RadioStation
import retrofit2.Response
import retrofit2.http.*

interface RadioApi {

    @GET("photos")
    suspend fun getPhotos(): Response<List<MarPhoto>>

    @POST("login")
    suspend fun login(@Body user: AccountEntity): Response<String>

    @GET("list_mail")
    suspend fun getListMails(@Header("authorization") token: String,
    @Query("page") pageNumber: Int)

    @GET("stations")
    suspend fun getStations(@Query("limit") limit: Int): Response<List<RadioStation>>

    @GET("stations")
    suspend fun getAllStations(): Response<List<RadioStation>>

    @GET("stations/bycountrycodeexact/{code}")
    suspend fun getStationsByCountryCode(
        @Path("code") countryCode: String,
        @Query("limit") limit:Int
    ): Response<List<RadioStation>>

    @GET("stations/search")
    suspend fun searchStation(
        @Query("order") order: String,
        @Query("limit") limit: Int,
        @Query("reverse") reverse: String
    ): Response<List<RadioStation>>
}