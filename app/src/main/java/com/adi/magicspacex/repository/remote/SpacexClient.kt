package com.adi.magicspacex.repository.remote

import com.adi.magicspacex.models.company_info.CompanyInfo
import com.adi.magicspacex.models.latest_launch.LatestLaunch
import com.adi.magicspacex.models.rockets.Rocket
import retrofit2.http.GET

interface SpacexClient {

    @GET("company")
    suspend fun getCompanyData(): CompanyInfo

    @GET("v5/launches/latest")
    suspend fun getLatestLaunch(): LatestLaunch

    @GET("v4/rockets")
    suspend fun getRockets(): Rocket

}