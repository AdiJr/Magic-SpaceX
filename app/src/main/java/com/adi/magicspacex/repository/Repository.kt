package com.adi.magicspacex.repository

import com.adi.magicspacex.repository.remote.SpacexService
import javax.inject.Inject

class Repository @Inject constructor(private val spacexService: SpacexService) {

    suspend fun fetchCompanyData() = spacexService.fetchCompanyData()

    suspend fun fetchLatestLaunch() = spacexService.fetchLatestLaunch()

    suspend fun fetchRockets() = spacexService.fetchRockets()

    suspend fun fetchPastLaunches() = spacexService.fetchPastLaunches()

    suspend fun fetchDragons() = spacexService.fetchDragons()

    suspend fun fetchLaunchpads() = spacexService.fetchLaunchpads()
}