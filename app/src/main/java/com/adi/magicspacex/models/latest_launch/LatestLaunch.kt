package com.adi.magicspacex.models.latest_launch

data class LatestLaunch(
    val capsules: List<Any>,
    val cores: List<Core>,
    val crew: List<Any>,
    val date_utc: String,
    val details: String,
    val fairings: Fairings,
    val flight_number: Int,
    val id: String,
    val launchpad: String,
    val links: Links,
    val name: String,
    val net: Boolean,
    val payloads: List<String>,
    val rocket: String,
    val ships: List<String>,
)