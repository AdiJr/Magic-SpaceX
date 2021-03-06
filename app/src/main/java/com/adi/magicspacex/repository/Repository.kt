package com.adi.magicspacex.repository

import com.adi.magicspacex.repository.remote.SpacexService
import javax.inject.Inject

class Repository @Inject constructor(private val spacexService: SpacexService) {

    suspend fun fetchCompanyInfo() = spacexService.fetchCompanyInfo()

    suspend fun fetchLatestLaunch() = spacexService.fetchLatestLaunch()

    suspend fun fetchRockets() = spacexService.fetchRockets()

    suspend fun fetchPastLaunches() = spacexService.fetchPastLaunches()

    suspend fun fetchDragons() = spacexService.fetchDragons()

    suspend fun fetchLaunchpads() = spacexService.fetchLaunchpads()

    suspend fun fetchShips() = spacexService.fetchShips()

    suspend fun fetchNextLaunch() = spacexService.fetchNextLaunch()

    suspend fun fetchLaunchById(launchId: String) = spacexService.fetchLaunchById(launchId)

    suspend fun fetchRocketById(rocketId: String) = spacexService.fetchRocketById(rocketId)

    suspend fun fetchLaunchpadById(launchpadId: String) =
        spacexService.fetchLaunchpadById(launchpadId)

    suspend fun fetchShipById(shipId: String) = spacexService.fetchShipById(shipId)
}