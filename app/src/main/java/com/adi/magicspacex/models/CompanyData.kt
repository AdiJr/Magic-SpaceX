package com.adi.magicspacex.models

data class CompanyData(
    val ceo: String,
    val coo: String,
    val cto: String,
    val cto_propulsion: String,
    val employees: Int,
    val founded: Int,
    val founder: String,
    val headquarters: Headquarters,
    val id: String,
    val launch_sites: Int,
    val links: Links,
    val name: String,
    val summary: String,
    val test_sites: Int,
    val valuation: Long,
    val vehicles: Int
)

data class Headquarters(
    val address: String,
    val city: String,
    val state: String
)

data class Links(
    val elon_twitter: String,
    val flickr: String,
    val twitter: String,
    val website: String
)