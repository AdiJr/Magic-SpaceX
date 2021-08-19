package com.adi.magicspacex.repository.remote

import javax.inject.Inject

class SpacexService @Inject constructor(private val spacexClient: SpacexClient) {

    suspend fun fetchCompanyData() = spacexClient.fetchCompanyData()

    suspend fun fetchLatestLaunch() = spacexClient.fetchLatestLaunch()

    suspend fun fetchRockets() = spacexClient.fetchRockets()

    suspend fun fetchPastLaunches() = spacexClient.fetchPastLaunches()

    suspend fun fetchDragons() = spacexClient.fetchDragons()

    suspend fun fetchLaunchpads() = spacexClient.fetchLaunchpads()

    suspend fun fetchShips() = spacexClient.fetchShips()
}