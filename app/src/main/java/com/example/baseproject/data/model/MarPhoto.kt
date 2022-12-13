package com.example.baseproject.data.model

import com.squareup.moshi.Json

data class MarPhoto(
    val id: String,
    @Json(name = "img_src") val imgUrl: String
)