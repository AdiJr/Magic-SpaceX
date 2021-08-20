package com.adi.magicspacex.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.adi.magicspacex.models.company_info.CompanyInfo
import com.adi.magicspacex.models.dragon.Dragon
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
class HomeViewModel @Inject constructor(private val repository: Repository) : ViewModel() {
    private val _companyInfo = MutableLiveData<CompanyInfo?>()
    private val _latestLaunch = MutableLiveData<Launch?>()
    private val _rockets = MutableLiveData<List<Rocket>?>()
    private val _pastLaunches = MutableLiveData<List<Launch>?>()
    private val _dragons = MutableLiveData<List<Dragon>?>()
    private val _launchpads = MutableLiveData<List<Launchpad>?>()
    private val _ships = MutableLiveData<List<Ship>?>()
    private val _nextLaunch = MutableLiveData<Launch?>()

    val companyInfo: LiveData<CompanyInfo?> = _companyInfo
    val launch: LiveData<Launch?> = _latestLaunch
    val rockets: LiveData<List<Rocket>?> = _rockets
    val pastLaunches: LiveData<List<Launch>?> = _pastLaunches
    val dragons: LiveData<List<Dragon>?> = _dragons
    val launchpads: LiveData<List<Launchpad>?> = _launchpads
    val ships: LiveData<List<Ship>?> = _ships
    val nextLaunch: LiveData<Launch?> = _nextLaunch

    init {
        fetchLatestLaunch()
        fetchPastLaunches()
        fetchRockets()
        fetchDragons()
        fetchLaunchpads()
        fetchShips()
        fetchCompanyInfo()
        fetchNextLaunch()
    }

    private fun fetchCompanyInfo() {
        viewModelScope.launch {
            try {
                _companyInfo.postValue(repository.fetchCompanyInfo())
            } catch (e: Exception) {
                Timber.e(e, "Error in fetching company data")
            }
        }
    }

    private fun fetchLatestLaunch() {
        viewModelScope.launch {
            try {
                _latestLaunch.postValue(repository.fetchLatestLaunch())
            } catch (e: Exception) {
                Timber.e(e, "Error in fetching latest launch data")
            }
        }
    }

    private fun fetchRockets() {
        viewModelScope.launch {
            try {
                _rockets.postValue(repository.fetchRockets())
            } catch (e: Exception) {
                Timber.e(e, "Error in fetching rockets data")
            }
        }
    }

    private fun fetchPastLaunches() {
        viewModelScope.launch {
            try {
                _pastLaunches.postValue(repository.fetchPastLaunches())
            } catch (e: Exception) {
                Timber.e(e, "Error in fetching past launches data")
            }
        }
    }

    private fun fetchDragons() {
        viewModelScope.launch {
            try {
                _dragons.postValue(repository.fetchDragons())
            } catch (e: Exception) {
                Timber.e(e, "Error in fetching past launches data")
            }
        }
    }

    private fun fetchLaunchpads() {
        viewModelScope.launch {
            try {
                _launchpads.postValue(repository.fetchLaunchpads())
            } catch (e: Exception) {
                Timber.e(e, "Error in fetching launchpads data")
            }
        }
    }

    private fun fetchShips() {
        viewModelScope.launch {
            try {
                _ships.postValue(repository.fetchShips())
            } catch (e: Exception) {
                Timber.e(e, "Error in fetching launchpads data")
            }
        }
    }

    private fun fetchNextLaunch() {
        viewModelScope.launch {
            try {
                _nextLaunch.postValue(repository.fetchNextLaunch())
            } catch (e: Exception) {
                Timber.e(e, "Error in fetching launchpads data")
            }
        }
    }
}