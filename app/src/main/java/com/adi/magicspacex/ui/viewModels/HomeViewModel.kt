package com.adi.magicspacex.ui.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.adi.magicspacex.models.company_info.CompanyInfo
import com.adi.magicspacex.models.dragon.Dragon
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

data class HomeUiState(
    val isLoading: Boolean = false,
    val companyInfo: CompanyInfo? = null,
    val exception: Exception? = null,
    val latestLaunch: Launch? = null,
    val rockets: List<Rocket> = emptyList(),
    val pastLaunches: List<Launch> = emptyList(),
    val dragons: List<Dragon> = emptyList(),
    val launchpads: List<Launchpad> = emptyList(),
    val ships: List<Ship> = emptyList(),
    val nextLaunch: Launch? = null,
)

@HiltViewModel
class HomeViewModel @Inject constructor(private val spacexRepository: SpacexRepository) :
    ViewModel() {
    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            fetchLatestLaunch()
            fetchPastLaunches()
            fetchRockets()
            fetchDragons()
            fetchLaunchpads()
            fetchShips()
            fetchCompanyInfo()
            fetchNextLaunch()
        }
    }

    private suspend fun fetchCompanyInfo() {
        try {
            _uiState.update { it.copy(isLoading = true) }
            val companyInfo = spacexRepository.fetchCompanyInfo()
            _uiState.update { it.copy(companyInfo = companyInfo, isLoading = false) }
        } catch (e: Exception) {
            _uiState.update { it.copy(exception = e, isLoading = false) }
            Timber.e(e, "Error in fetching company info")
        }
    }

    private suspend fun fetchLatestLaunch() {
        try {
            _uiState.update { it.copy(isLoading = true) }
            val latestLaunch = spacexRepository.fetchLatestLaunch()
            _uiState.update { it.copy(latestLaunch = latestLaunch, isLoading = false) }
        } catch (e: Exception) {
            _uiState.update { it.copy(exception = e, isLoading = false) }
            Timber.e(e, "Error in fetching latest launch")
        }
    }

    private suspend fun fetchRockets() {
        try {
            _uiState.update { it.copy(isLoading = true) }
            val rockets = spacexRepository.fetchRockets()
            _uiState.update { it.copy(rockets = rockets, isLoading = false) }
        } catch (e: Exception) {
            _uiState.update { it.copy(exception = e, isLoading = false) }
            Timber.e(e, "Error in fetching rockets")
        }
    }

    private suspend fun fetchPastLaunches() {
        try {
            _uiState.update { it.copy(isLoading = true) }
            val pastLaunches = spacexRepository.fetchPastLaunches()
            _uiState.update { it.copy(pastLaunches = pastLaunches, isLoading = false) }
        } catch (e: Exception) {
            _uiState.update { it.copy(exception = e, isLoading = false) }
            Timber.e(e, "Error in fetching past launches")
        }
    }

    private suspend fun fetchDragons() {
        try {
            _uiState.update { it.copy(isLoading = true) }
            val dragons = spacexRepository.fetchDragons()
            _uiState.update { it.copy(dragons = dragons, isLoading = false) }
        } catch (e: Exception) {
            _uiState.update { it.copy(exception = e, isLoading = false) }
            Timber.e(e, "Error in fetching dragons")
        }
    }

    private suspend fun fetchLaunchpads() {
        try {
            _uiState.update { it.copy(isLoading = true) }
            val launchpads = spacexRepository.fetchLaunchpads()
            _uiState.update { it.copy(launchpads = launchpads, isLoading = false) }
        } catch (e: Exception) {
            _uiState.update { it.copy(exception = e, isLoading = false) }
            Timber.e(e, "Error in fetching launchpads")
        }
    }

    private suspend fun fetchShips() {
        try {
            _uiState.update { it.copy(isLoading = true) }
            val ships = spacexRepository.fetchShips()
            _uiState.update { it.copy(ships = ships, isLoading = false) }
        } catch (e: Exception) {
            _uiState.update { it.copy(exception = e, isLoading = false) }
            Timber.e(e, "Error in fetching ships")
        }
    }

    private suspend fun fetchNextLaunch() {
        try {
            _uiState.update { it.copy(isLoading = true) }
            val nextLaunch = spacexRepository.fetchNextLaunch()
            _uiState.update { it.copy(nextLaunch = nextLaunch, isLoading = false) }
        } catch (e: Exception) {
            _uiState.update { it.copy(exception = e, isLoading = false) }
            Timber.e(e, "Error in fetching next launch")
        }
    }
}