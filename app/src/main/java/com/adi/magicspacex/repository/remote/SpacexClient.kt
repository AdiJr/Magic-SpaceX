package com.adi.magicspacex.repository.remote

import com.adi.magicspacex.models.company_info.CompanyInfo
import com.adi.magicspacex.models.dragon.Dragon
import com.adi.magicspacex.models.launch.Launch
import com.adi.magicspacex.models.launchpad.Launchpad
import com.adi.magicspacex.models.rocket.Rocket
import com.adi.magicspacex.models.ship.Ship
import retrofit2.http.GET
import retrofit2.http.Path

interface SpacexClient {

    @GET("v4/company")
    suspend fun fetchCompanyInfo(): CompanyInfo

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

    @GET("v4/ships")
    suspend fun fetchShips(): List<Ship>

    @GET("v5/launches/next")
    suspend fun fetchNextLaunch(): Launch

    @GET("v5/launches/{id}")
    suspend fun fetchLaunchById(@Path("id") launchId: String): Launch

    @GET("v4/rockets/{id}")
    suspend fun fetchRocketById(@Path("id") rocketId: String): Rocket

    @GET("v4/launchpads/{id}")
    suspend fun fetchLaunchpadById(@Path("id") launchpadId: String): Launchpad

    @GET("v4/ships/{id}")
    suspend fun fetchShipById(@Path("id") shipId: String): Ship
}