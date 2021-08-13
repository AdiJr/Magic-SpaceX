package com.adi.magicspacex.repository.remote

import javax.inject.Inject

class SpacexService @Inject constructor(private val spacexClient: SpacexClient) {

    suspend fun getCompanyData() = spacexClient.getCompanyData()

}