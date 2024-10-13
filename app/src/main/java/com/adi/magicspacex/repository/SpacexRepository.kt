package com.adi.magicspacex.repository

import com.adi.magicspacex.models.companyInfo.CompanyInfo
import com.adi.magicspacex.models.dragon.Dragon
import com.adi.magicspacex.models.launch.Launch
import com.adi.magicspacex.models.launchpad.Launchpad
import com.adi.magicspacex.models.rocket.Rocket
import com.adi.magicspacex.models.ship.Ship
import com.adi.magicspacex.repository.remote.SpacexService
import javax.inject.Inject

interface SpacexRepository {
    suspend fun fetchLaunchById(launchId: String): Launch

    suspend fun fetchRocketById(rocketId: String): Rocket

    suspend fun fetchLaunchpadById(launchpadId: String): Launchpad

    suspend fun fetchShipById(shipId: String): Ship

    suspend fun fetchCompanyInfo(): CompanyInfo

    suspend fun fetchLatestLaunch(): Launch

    suspend fun fetchRockets(): List<Rocket>

    suspend fun fetchPastLaunches(): List<Launch>

    suspend fun fetchDragons(): List<Dragon>

    suspend fun fetchLaunchpads(): List<Launchpad>

    suspend fun fetchShips(): List<Ship>

    suspend fun fetchNextLaunch(): Launch
}

internal class SpacexRepositoryImpl @Inject constructor(
    private val spacexService: SpacexService,
) : SpacexRepository {

    override suspend fun fetchLaunchById(launchId: String): Launch {
        return spacexService.fetchLaunchById(launchId)
    }

    override suspend fun fetchRocketById(rocketId: String): Rocket {
        return spacexService.fetchRocketById(rocketId)
    }

    override suspend fun fetchLaunchpadById(launchpadId: String): Launchpad {
        return spacexService.fetchLaunchpadById(launchpadId)
    }

    override suspend fun fetchShipById(shipId: String): Ship {
        return spacexService.fetchShipById(shipId)
    }

    override suspend fun fetchCompanyInfo(): CompanyInfo {
        return spacexService.fetchCompanyInfo()
    }

    override suspend fun fetchLatestLaunch(): Launch {
        return spacexService.fetchLatestLaunch()
    }

    override suspend fun fetchRockets(): List<Rocket> {
        return spacexService.fetchRockets()
    }

    override suspend fun fetchPastLaunches(): List<Launch> {
        return spacexService.fetchPastLaunches()
    }

    override suspend fun fetchDragons(): List<Dragon> {
        return spacexService.fetchDragons()
    }

    override suspend fun fetchLaunchpads(): List<Launchpad> {
        return spacexService.fetchLaunchpads()
    }

    override suspend fun fetchShips(): List<Ship> {
        return spacexService.fetchShips()
    }

    override suspend fun fetchNextLaunch(): Launch {
        return spacexService.fetchNextLaunch()
    }
}
