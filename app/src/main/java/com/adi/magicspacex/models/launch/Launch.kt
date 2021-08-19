package com.adi.magicspacex.models.launch

data class Launch(
    val capsules: List<Any>,
    val cores: List<Core>,
    val crew: List<Any>,
    val date_utc: String,
    val details: String,
    val fairings: Fairings,
    val flight_number: Int,
    val id: String,
    val launchpad: String,
    val links: LaunchLinks,
    val name: String,
    val payloads: List<String>,
    val rocket: String,
    val ships: List<String>,
)