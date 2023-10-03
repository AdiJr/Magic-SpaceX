package com.adi.magicspacex.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.adi.magicspacex.repository.SpacexData
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

    private val _viewStateFlow = MutableStateFlow<DataState<SpacexData?>>(State.Idle)
    val viewState = _viewStateFlow.asStateFlow()

    init {
        viewModelScope.launch {
            cancellationAwareTryCatch(
                tryBlock = {
                    _viewStateFlow.update { State.Loading }

                    val spacexData = spacexRepository.spacexDataFlow()

                    _viewStateFlow.update {
                        DataState.Loaded(spacexData.value)
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