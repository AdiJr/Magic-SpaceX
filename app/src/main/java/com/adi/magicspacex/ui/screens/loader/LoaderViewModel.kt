package com.adi.magicspacex.ui.screens.loader

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.adi.magicspacex.utils.cancellationAwareTryCatch
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoaderViewModel @Inject constructor() : ViewModel() {

    private val _viewState = MutableStateFlow(ViewState.default())
    val viewState = _viewState.asStateFlow()

    init {
        viewModelScope.launch {
            cancellationAwareTryCatch(
                tryBlock = {
                    _viewState.update { it.copy(isLoading = true) }

                    delay(3000)
                    // TODO: fetch data

                    _viewState.update { it.copy(isLoading = false) }
                }, catchBlock = {
                    _viewState.update {
                        it.copy(
                            isLoading = false,
                            isError = true,
                        )
                    }
                }
            )
        }
    }
}

data class ViewState(
    val isLoading: Boolean,
    val isError: Boolean,
) {

    companion object {

        fun default(): ViewState {
            return ViewState(
                isLoading = false,
                isError = false,
            )
        }
    }
}
