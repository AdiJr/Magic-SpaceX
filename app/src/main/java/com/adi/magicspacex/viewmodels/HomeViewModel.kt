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
import com.adi.magicspacex.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val repository: Repository) : ViewModel() {
    private val _companyData = MutableLiveData<CompanyInfo?>()
    private val _latestLaunch = MutableLiveData<Launch?>()
    private val _rockets = MutableLiveData<List<Rocket>?>()
    private val _pastLaunches = MutableLiveData<List<Launch>?>()
    private val _dragons = MutableLiveData<List<Dragon>?>()
    private val _launchpads = MutableLiveData<List<Launchpad>?>()
    private val _latestLaunchLoaded = MutableLiveData(false)
    private val _pastLaunchesLoaded = MutableLiveData(false)
    private val _rocketsLoaded = MutableLiveData(false)
    private val _dragonsLoaded = MutableLiveData(false)
    private val _launchpadsLoaded = MutableLiveData(false)

    val companyData: LiveData<CompanyInfo?> = _companyData
    val launch: LiveData<Launch?> = _latestLaunch
    val rockets: LiveData<List<Rocket>?> = _rockets
    val pastLaunches: LiveData<List<Launch>?> = _pastLaunches
    val dragons: LiveData<List<Dragon>?> = _dragons
    val launchpads: LiveData<List<Launchpad>?> = _launchpads

    val latestLaunchLoaded: LiveData<Boolean> = _latestLaunchLoaded
    val pastLaunchesLoaded: LiveData<Boolean> = _pastLaunchesLoaded
    val rocketsLoaded: LiveData<Boolean> = _rocketsLoaded
    val dragonsLoaded: LiveData<Boolean> = _dragonsLoaded
    val launchpadsLoaded: LiveData<Boolean> = _launchpadsLoaded

    init {
        fetchLatestLaunch()
        fetchPastLaunches()
        fetchRockets()
        fetchDragons()
        fetchLaunchpads()
    }

    fun fetchCompanyData() {
        viewModelScope.launch {
            try {
                _companyData.postValue(repository.fetchCompanyData())
            } catch (e: Exception) {
                Timber.e(e, "Error in fetching company data")
            }
        }
    }

    private fun fetchLatestLaunch() {
        viewModelScope.launch {
            try {
                _latestLaunch.postValue(repository.fetchLatestLaunch())
                _latestLaunchLoaded.postValue(true)
            } catch (e: Exception) {
                Timber.e(e, "Error in fetching latest launch data")
            }
        }
    }

    private fun fetchRockets() {
        viewModelScope.launch {
            try {
                _rockets.postValue(repository.fetchRockets())
                _rocketsLoaded.postValue(true)
            } catch (e: Exception) {
                Timber.e(e, "Error in fetching rockets data")
            }
        }
    }

    private fun fetchPastLaunches() {
        viewModelScope.launch {
            try {
                _pastLaunches.postValue(repository.fetchPastLaunches())
                _pastLaunchesLoaded.postValue(true)
            } catch (e: Exception) {
                Timber.e(e, "Error in fetching past launches data")
            }
        }
    }

    private fun fetchDragons() {
        viewModelScope.launch {
            try {
                _dragons.postValue(repository.fetchDragons())
                _dragonsLoaded.postValue(true)
            } catch (e: Exception) {
                Timber.e(e, "Error in fetching past launches data")
            }
        }
    }

    private fun fetchLaunchpads() {
        viewModelScope.launch {
            try {
                _launchpads.postValue(repository.fetchLaunchpads())
                _launchpadsLoaded.postValue(true)
            } catch (e: Exception) {
                Timber.e(e, "Error in fetching launchpads data")
            }
        }
    }
}