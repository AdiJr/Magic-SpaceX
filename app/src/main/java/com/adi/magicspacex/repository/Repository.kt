package com.adi.magicspacex.repository

import com.adi.magicspacex.repository.remote.SpacexService
import javax.inject.Inject

class Repository @Inject constructor(private val spacexService: SpacexService) {

    suspend fun getCompanyData() = spacexService.getCompanyData()

    suspend fun getLatestLaunch() = spacexService.getLatestLaunch()

    suspend fun getRockets() = spacexService.getRockets()
}