package com.example.baseproject.data.model


import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "radio_station")
data class RadioStation(
    @PrimaryKey
    @SerializedName("changeuuid")
    val changeuuid: String = "",

    @SerializedName("stationuuid")
    val stationuuid: String? = null,

    @SerializedName("serveruuid")
    val serveruuid: String? = null,

    @SerializedName("name")
    val name: String? = null,

    @SerializedName("url")
    val url: String? = null,

    @SerializedName("url_resolved")
    val urlResolved: String? = null,

    @SerializedName("homepage")
    val homepage: String? = null,

    @SerializedName("favicon")
    val favicon: String? = null,

    @SerializedName("tags")
    val tags: String? = null,

    @SerializedName("country")
    val country: String? = null,

    @SerializedName("countrycode")
    val countrycode: String? = null,

    @SerializedName("iso_3166_2")
    val iso31662: String? = null,

    @SerializedName("state")
    val state: String? = null,

    @SerializedName("language")
    val language: String? = null,

    @SerializedName("languagecodes")
    val languagecodes: String? = null,

    @SerializedName("votes")
    val votes: Int? = null,

    @SerializedName("lastchangetime")
    val lastchangetime: String? = null,

    @SerializedName("lastchangetime_iso8601")
    val lastchangetimeIso8601: String? = null,

    @SerializedName("codec")
    val codec: String? = null,

    @SerializedName("bitrate")
    val bitrate: Int? = null,

    @SerializedName("hls")
    val hls: Int? = null,

    @SerializedName("lastcheckok")
    val lastcheckok: Int? = null,

    @SerializedName("lastchecktime")
    val lastchecktime: String? = null,

    @SerializedName("lastchecktime_iso8601")
    val lastchecktimeIso8601: String? = null,

    @SerializedName("lastcheckoktime")
    val lastcheckoktime: String? = null,

    @SerializedName("lastcheckoktime_iso8601")
    val lastcheckoktimeIso8601: String? = null,

    @SerializedName("lastlocalchecktime")
    val lastlocalchecktime: String? = null,

    @SerializedName("lastlocalchecktime_iso8601")
    val lastlocalchecktimeIso8601: String? = null,

    @SerializedName("clicktimestamp")
    val clicktimestamp: String? = null,

    @SerializedName("clicktimestamp_iso8601")
    val clicktimestampIso8601: String? = null,

    @SerializedName("clickcount")
    val clickcount: Int? = null,

    @SerializedName("clicktrend")
    val clicktrend: Int? = null,

    @SerializedName("ssl_error")
    val sslError: Int? = null,

    @SerializedName("geo_lat")
    val geoLat: Double? = null,

    @SerializedName("geo_long")
    val geoLong: Double? = null,

    @SerializedName("has_extended_info")
    val hasExtendedInfo: Boolean? = null
)