package com.adi.magicspacex.ui.screens.launch

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.adi.magicspacex.models.launch.Launch
import com.adi.magicspacex.models.launchpad.Launchpad
import com.adi.magicspacex.models.rocket.Rocket
import com.adi.magicspacex.repository.SpacexRepository
import com.adi.magicspacex.utils.model.helpers.DataState
import com.adi.magicspacex.utils.model.helpers.State
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LaunchDetailsViewModel @Inject constructor(
    private val spacexRepository: SpacexRepository,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val _launchDetailsViewStateFlow =
        MutableStateFlow<DataState<LaunchDetailsViewState>>(State.Idle)
    val launchDetailsViewStateFlow = _launchDetailsViewStateFlow.asStateFlow()

    private val launchId = savedStateHandle.toRoute<LaunchDetails>().id

    init {
        fetchLaunchDetailsData()
    }

    fun fetchLaunchDetailsData() {
        viewModelScope.launch {
            try {
                _launchDetailsViewStateFlow.update { State.Loading }

                val launch = spacexRepository.fetchLaunchById(launchId)
                val rocket = spacexRepository.fetchRocketById(launch.rocket)
                val launchpad = spacexRepository.fetchLaunchpadById(launch.launchpad)

                val launchDetailsViewState = LaunchDetailsViewState(
                    launch = launch,
                    rocket = rocket,
                    launchpad = launchpad,
                )

                _launchDetailsViewStateFlow.update { DataState.Loaded(launchDetailsViewState) }
            } catch (ex: Exception) {
                ensureActive()
                _launchDetailsViewStateFlow.update { State.Error(ex) }
            }
        }
    }
}

data class LaunchDetailsViewState(
    val launch: Launch,
    val rocket: Rocket,
    val launchpad: Launchpad,
)