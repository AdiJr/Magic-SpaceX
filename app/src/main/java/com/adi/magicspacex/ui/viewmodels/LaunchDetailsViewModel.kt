package com.adi.magicspacex.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.adi.magicspacex.models.launch.Launch
import com.adi.magicspacex.models.launchpad.Launchpad
import com.adi.magicspacex.models.rocket.Rocket
import com.adi.magicspacex.models.ship.Ship
import com.adi.magicspacex.repository.SpacexRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

data class LaunchDetailsUiState(
    val isLoading: Boolean = false,
    val launch: Launch? = null,
    val rocket: Rocket? = null,
    val launchpad: Launchpad? = null,
    val ship: Ship? = null,
    val exception: Exception? = null,
)

@HiltViewModel
class LaunchDetailsViewModel @Inject constructor(private val spacexRepository: SpacexRepository) :
    ViewModel() {
    private val _uiState = MutableStateFlow(LaunchDetailsUiState())
    val uiState = _uiState.asStateFlow()

    fun fetchLaunchById(launchId: String) {
        viewModelScope.launch {
            try {
                _uiState.update { it.copy(isLoading = true) }
                val launch = spacexRepository.fetchLaunchById(launchId)
                fetchLaunchDetails(launch)
                _uiState.update { it.copy(launch = launch, isLoading = false) }
            } catch (e: Exception) {
                _uiState.update { it.copy(exception = e, isLoading = false) }
                Timber.e(e, "Error in fetching launch by id")
            }
        }
    }

    private suspend fun fetchLaunchDetails(launch: Launch) {
        if (launch.rocket != null)
            fetchRocketById(launch.rocket)
        if (launch.launchpad != null)
            fetchLaunchpadById(launch.launchpad)
        if (launch.ships != null && launch.ships.isNotEmpty())
            fetchShipById(launch.ships.first())
    }

    private suspend fun fetchRocketById(rocketId: String) {
        try {
            val rocket = spacexRepository.fetchRocketById(rocketId)
            _uiState.update { it.copy(rocket = rocket) }
        } catch (e: Exception) {
            _uiState.update { it.copy(exception = e, isLoading = false) }
            Timber.e(e, "Error in fetching rocket by id")
        }
    }

    private suspend fun fetchLaunchpadById(launchpadId: String) {
        try {
            val launchpad = spacexRepository.fetchLaunchpadById(launchpadId)
            _uiState.update { it.copy(launchpad = launchpad) }
        } catch (e: Exception) {
            _uiState.update { it.copy(exception = e, isLoading = false) }
            Timber.e(e, "Error in fetching launchpad by id")
        }
    }

    private suspend fun fetchShipById(shipId: String) {
        try {
            val ship = spacexRepository.fetchShipById(shipId)
            _uiState.update { it.copy(ship = ship) }
        } catch (e: Exception) {
            _uiState.update { it.copy(exception = e, isLoading = false) }
            Timber.e(e, "Error in fetching ship by id")
        }
    }
}