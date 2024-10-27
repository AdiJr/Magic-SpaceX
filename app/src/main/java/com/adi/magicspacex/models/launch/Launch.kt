package com.adi.magicspacex.models.launch

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Launch(
    @field:Json(name = "date_utc")
    val launchDate: String,

    val details: String,

    @field:Json(name = "flight_number")
    val flightNumber: Int,

    val id: String,

    val launchpad: String,

    val links: LaunchLinks,

    val name: String,

    val payloads: List<String>,

    val rocket: String,

    val ships: List<String>,
)