package com.adi.magicspacex.models.rocket

data class FirstStage(
    val burn_time_sec: Any,
    val engines: Int,
    val fuel_amount_tons: Float,
    val reusable: Boolean,
    val thrust_sea_level: ThrustSeaLevelX,
    val thrust_vacuum: ThrustVacuumX
)