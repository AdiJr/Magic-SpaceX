package com.adi.magicspacex.models.dragon

data class Thruster(
    val amount: Int,
    val fuel_1: String,
    val fuel_2: String,
    val isp: Int,
    val pods: Int,
    val thrust: Thrust,
    val type: String
)