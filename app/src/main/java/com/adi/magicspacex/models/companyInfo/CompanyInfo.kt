package com.adi.magicspacex.models.companyInfo

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CompanyInfo(
    val headquarters: Headquarters,
    val id: String,
    val links: Links,
    val name: String,
    val summary: String,
)

@JsonClass(generateAdapter = true)
data class Headquarters(
    val address: String,
    val city: String,
    val state: String
)

@JsonClass(generateAdapter = true)
data class Links(
    val flickr: String,
    val twitter: String,
    val website: String
)