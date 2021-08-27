package com.adi.magicspacex.repository.remote

import javax.inject.Inject

class SpacexService @Inject constructor(private val spacexClient: SpacexClient) {

    suspend fun fetchCompanyInfo() = spacexClient.fetchCompanyInfo()

    suspend fun fetchLatestLaunch() = spacexClient.fetchLatestLaunch()

    suspend fun fetchRockets() = spacexClient.fetchRockets()

    suspend fun fetchPastLaunches() = spacexClient.fetchPastLaunches()

    suspend fun fetchDragons() = spacexClient.fetchDragons()

    suspend fun fetchLaunchpads() = spacexClient.fetchLaunchpads()

    suspend fun fetchShips() = spacexClient.fetchShips()

    suspend fun fetchNextLaunch() = spacexClient.fetchNextLaunch()

    suspend fun fetchLaunchById(launchId: String) = spacexClient.fetchLaunchById(launchId)

    suspend fun fetchRocketById(rocketId: String) = spacexClient.fetchRocketById(rocketId)

    suspend fun fetchLaunchpadById(launchpadId: String) =
        spacexClient.fetchLaunchpadById(launchpadId)

    suspend fun fetchShipById(shipId: String) = spacexClient.fetchShipById(shipId)
}