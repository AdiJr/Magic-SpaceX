package com.adi.magicspacex.models.launchpad

data class Launchpad(
    val details: String,
    val full_name: String,
    val id: String,
    val images: Images,
    val latitude: Float,
    val launch_attempts: Int,
    val launch_successes: Int,
    val launches: List<String>,
    val locality: String,
    val longitude: Float,
    val name: String,
    val region: String,
    val rockets: List<String>,
    val status: String,
    val timezone: String
)