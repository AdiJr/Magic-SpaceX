package com.adi.magicspacex.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.adi.magicspacex.models.company_info.CompanyInfo
import com.adi.magicspacex.models.dragon.Dragon
import com.adi.magicspacex.models.launch.Launch
import com.adi.magicspacex.models.launchpad.Launchpad
import com.adi.magicspacex.models.rocket.Rocket
import com.adi.magicspacex.models.ship.Ship
import com.adi.magicspacex.repository.SpacexRepository
import com.adi.magicspacex.utils.cancellationAwareTryCatch
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val spacexRepository: SpacexRepository,
) : ViewModel() {

    private val _viewState = MutableStateFlow(HomeViewState.default())
    val viewState = _viewState.asStateFlow()

    init {
        viewModelScope.launch {
            cancellationAwareTryCatch(
                tryBlock = {
                    _viewState.update { it.copy(isLoading = true) }

                    val companyInfo = spacexRepository.fetchCompanyInfo()
                    val latestLaunch = spacexRepository.fetchLatestLaunch()
                    val rockets = spacexRepository.fetchRockets()
                    val pastLaunches = spacexRepository.fetchPastLaunches()
                    val dragons = spacexRepository.fetchDragons()
                    val launchpads = spacexRepository.fetchLaunchpads()
                    val ships = spacexRepository.fetchShips()
                    val nextLaunch = spacexRepository.fetchNextLaunch()

                    _viewState.update {
                        it.copy(
                            isLoading = false,
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

                },
                catchBlock = { ex ->
                    _viewState.update { it.copy(error = ex, isLoading = false) }

                    Timber.e(ex, "Error in fetching home screen data")
                }
            )
        }
    }
}

data class HomeViewState(
    val isLoading: Boolean,
    val companyInfo: CompanyInfo?,
    val error: Throwable?,
    val latestLaunch: Launch? = null,
    val rockets: List<Rocket> = emptyList(),
    val pastLaunches: List<Launch> = emptyList(),
    val dragons: List<Dragon> = emptyList(),
    val launchpads: List<Launchpad> = emptyList(),
    val ships: List<Ship> = emptyList(),
    val nextLaunch: Launch? = null,
) {

    companion object {

        fun default(): HomeViewState {
            return HomeViewState(
                isLoading = false,
                companyInfo = null,
                error = null,
                latestLaunch = null,
                rockets = emptyList(),
                pastLaunches = emptyList(),
                dragons = emptyList(),
                launchpads = emptyList(),
                ships = emptyList(),
                nextLaunch = null,
            )
        }
    }
}