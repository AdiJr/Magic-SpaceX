package com.adi.magicspacex.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.adi.magicspacex.models.companyInfo.CompanyInfo
import com.adi.magicspacex.models.dragon.Dragon
import com.adi.magicspacex.models.launch.Launch
import com.adi.magicspacex.models.launchpad.Launchpad
import com.adi.magicspacex.models.rocket.Rocket
import com.adi.magicspacex.models.ship.Ship
import com.adi.magicspacex.repository.SpacexRepository
import com.adi.magicspacex.utils.cancellationAwareTryCatch
import com.adi.magicspacex.utils.model.helpers.DataState
import com.adi.magicspacex.utils.model.helpers.State
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

    private val _viewStateFlow = MutableStateFlow<DataState<HomeViewState>>(State.Idle)
    val viewState = _viewStateFlow.asStateFlow()

    init {
        viewModelScope.launch {
            cancellationAwareTryCatch(
                tryBlock = {
                    _viewStateFlow.update { State.Loading }

                    val nextLaunch = spacexRepository.fetchNextLaunch()
                    val latestLaunch = spacexRepository.fetchLatestLaunch()
                    val pastLaunches = spacexRepository.fetchPastLaunches()
                    val rockets = spacexRepository.fetchRockets()
                    val dragons = spacexRepository.fetchDragons()
                    val launchpads = spacexRepository.fetchLaunchpads()
                    val ships = spacexRepository.fetchShips()
                    val companyInfo = spacexRepository.fetchCompanyInfo()

                    val viewState = HomeViewState(
                        nextLaunch = nextLaunch,
                        latestLaunch = latestLaunch,
                        pastLaunches = pastLaunches,
                        rockets = rockets,
                        dragons = dragons,
                        launchpads = launchpads,
                        ships = ships,
                        companyInfo = companyInfo,
                    )

                    _viewStateFlow.update {
                        DataState.Loaded(viewState)
                    }
                },
                catchBlock = { ex ->
                    _viewStateFlow.update { State.Error(ex) }

                    Timber.e(ex, "Error in fetching home screen data")
                }
            )
        }
    }
}

data class HomeViewState(
    val companyInfo: CompanyInfo,
    val latestLaunch: Launch,
    val rockets: List<Rocket>,
    val pastLaunches: List<Launch>,
    val dragons: List<Dragon>,
    val launchpads: List<Launchpad>,
    val ships: List<Ship>,
    val nextLaunch: Launch,
)