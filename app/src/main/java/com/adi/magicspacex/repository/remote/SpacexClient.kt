package com.adi.magicspacex.repository.remote

import com.adi.magicspacex.models.company_info.CompanyInfo
import com.adi.magicspacex.models.dragon.Dragon
import com.adi.magicspacex.models.launch.Launch
import com.adi.magicspacex.models.launchpad.Launchpad
import com.adi.magicspacex.models.rocket.Rocket
import retrofit2.http.GET

interface SpacexClient {

    @GET("company")
    suspend fun fetchCompanyData(): CompanyInfo

    @GET("v5/launches/latest")
    suspend fun fetchLatestLaunch(): Launch

    @GET("v4/rockets")
    suspend fun fetchRockets(): List<Rocket>

    @GET("v5/launches/past")
    suspend fun fetchPastLaunches(): List<Launch>

    @GET("v4/dragons")
    suspend fun fetchDragons(): List<Dragon>

    @GET("v4/launchpads")
    suspend fun fetchLaunchpads(): List<Launchpad>
}