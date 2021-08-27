package com.adi.magicspacex.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.adi.magicspacex.models.launch.Launch
import com.adi.magicspacex.models.launchpad.Launchpad
import com.adi.magicspacex.models.rocket.Rocket
import com.adi.magicspacex.models.ship.Ship
import com.adi.magicspacex.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class LaunchDetailsViewModel @Inject constructor(private val repository: Repository) : ViewModel() {
    private val _launch = MutableLiveData<Launch?>()
    private val _rocket = MutableLiveData<Rocket?>()
    private val _launchpad = MutableLiveData<Launchpad?>()
    private val _ship = MutableLiveData<Ship?>()

    val launch: LiveData<Launch?> = _launch
    val rocket: LiveData<Rocket?> = _rocket
    val launchpad: LiveData<Launchpad?> = _launchpad
    val ship: LiveData<Ship?> = _ship

    fun fetchLaunchById(launchId: String) {
        viewModelScope.launch {
            try {
                _launch.postValue(repository.fetchLaunchById(launchId))
            } catch (e: Exception) {
                Timber.e(e, "Error in fetching launch data")
            }
        }
    }

    fun fetchRocketById(rocketId: String) {
        viewModelScope.launch {
            try {
                _rocket.postValue(repository.fetchRocketById(rocketId))
            } catch (e: Exception) {
                Timber.e(e, "Error in fetching rocket data")
            }
        }
    }

    fun fetchLaunchpadById(launchpadId: String) {
        viewModelScope.launch {
            try {
                _launchpad.postValue(repository.fetchLaunchpadById(launchpadId))
            } catch (e: Exception) {
                Timber.e(e, "Error in fetching rocket data")
            }
        }
    }

    fun fetchShipById(shipId: String) {
        viewModelScope.launch {
            try {
                _ship.postValue(repository.fetchShipById(shipId))
            } catch (e: Exception) {
                Timber.e(e, "Error in fetching rocket data")
            }
        }
    }
}