package com.adi.magicspacex.repository

import com.adi.magicspacex.models.company_info.CompanyInfo
import com.adi.magicspacex.models.dragon.Dragon
import com.adi.magicspacex.models.launch.Launch
import com.adi.magicspacex.models.launchpad.Launchpad
import com.adi.magicspacex.models.rocket.Rocket
import com.adi.magicspacex.models.ship.Ship
import com.adi.magicspacex.repository.remote.SpacexService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

interface SpacexRepository {

    suspend fun fetchSpacexData()

    fun spacexDataFlow(): StateFlow<SpacexData?>

    suspend fun fetchLaunchById(launchId: String)

    suspend fun fetchRocketById(rocketId: String)

    suspend fun fetchLaunchpadById(launchpadId: String)

    suspend fun fetchShipById(shipId: String)
}

internal class SpacexRepositoryImpl @Inject constructor(
    private val spacexService: SpacexService,
) : SpacexRepository {

    private val _spacexDataStateFlow = MutableStateFlow<SpacexData?>(null)

    override suspend fun fetchSpacexData() {
        val companyInfo = spacexService.fetchCompanyInfo()
        val latestLaunch = spacexService.fetchLatestLaunch()
        val rockets = spacexService.fetchRockets()
        val pastLaunches = spacexService.fetchPastLaunches()
        val dragons = spacexService.fetchDragons()
        val launchpads = spacexService.fetchLaunchpads()
        val ships = spacexService.fetchShips()
        val nextLaunch = spacexService.fetchNextLaunch()

        _spacexDataStateFlow.update {
            SpacexData(
                companyInfo = companyInfo,
                latestLaunch = latestLaunch,
                rockets = rockets,
                pastLaunches = pastLaunches,
                dragons = dragons,
                launchpads = launchpads,
                ships = ships,
                nextLaunch = nextLaunch,
            )
        }
    }

    override fun spacexDataFlow() = _spacexDataStateFlow

    override suspend fun fetchLaunchById(launchId: String) {
        spacexService.fetchLaunchById(launchId)
    }

    override suspend fun fetchRocketById(rocketId: String) {
        spacexService.fetchRocketById(rocketId)
    }

    override suspend fun fetchLaunchpadById(launchpadId: String) {
        spacexService.fetchLaunchpadById(launchpadId)
    }

    override suspend fun fetchShipById(shipId: String) {
        spacexService.fetchShipById(shipId)
    }
}

data class SpacexData(
    val companyInfo: CompanyInfo?,
    val latestLaunch: Launch? = null,
    val rockets: List<Rocket> = emptyList(),
    val pastLaunches: List<Launch> = emptyList(),
    val dragons: List<Dragon> = emptyList(),
    val launchpads: List<Launchpad> = emptyList(),
    val ships: List<Ship> = emptyList(),
    val nextLaunch: Launch? = null,
)