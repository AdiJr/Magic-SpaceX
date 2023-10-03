package com.adi.magicspacex.ui.screens.loader

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.adi.magicspacex.repository.SpacexRepository
import com.adi.magicspacex.utils.cancellationAwareTryCatch
import com.adi.magicspacex.utils.model.helpers.DataState
import com.adi.magicspacex.utils.model.helpers.State
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoaderViewModel @Inject constructor(
    private val spacexRepository: SpacexRepository,
) : ViewModel() {

    private val _viewState = MutableStateFlow<DataState<Unit>>(State.Idle)
    val viewState = _viewState.asStateFlow()

    init {
        viewModelScope.launch {
            cancellationAwareTryCatch(
                tryBlock = {
                    _viewState.update { State.Loading }

                    spacexRepository.fetchSpacexData()

                    _viewState.update { DataState.Loaded(Unit) }
                }, catchBlock = { ex ->
                    _viewState.update { State.Error(ex) }
                }
            )
        }
    }
}