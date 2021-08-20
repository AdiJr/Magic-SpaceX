package com.adi.magicspacex.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.adi.magicspacex.models.launch.Launch
import com.adi.magicspacex.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class LaunchDetailsViewModel @Inject constructor(private val repository: Repository) : ViewModel() {
    private val _launch = MutableLiveData<Launch?>()

    val launch: LiveData<Launch?> = _launch

    fun fetchLaunchById(launchId: String) {
        viewModelScope.launch {
            try {
                _launch.postValue(repository.fetchLaunchById(launchId))
            } catch (e: Exception) {
                Timber.e(e, "Error in fetching launch data")
            }
        }
    }
}