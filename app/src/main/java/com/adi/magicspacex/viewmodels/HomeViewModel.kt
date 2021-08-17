package com.adi.magicspacex.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.adi.magicspacex.models.company_info.CompanyInfo
import com.adi.magicspacex.models.dragon.Dragon
import com.adi.magicspacex.models.launch.Launch
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

    val companyData: LiveData<CompanyInfo?> = _companyData
    val launch: LiveData<Launch?> = _latestLaunch
    val rockets: LiveData<List<Rocket>?> = _rockets
    val pastLaunches: LiveData<List<Launch>?> = _pastLaunches
    val dragons: LiveData<List<Dragon>?> = _dragons

    fun fetchCompanyData() {
        viewModelScope.launch {
            try {
                _companyData.postValue(repository.fetchCompanyData())
            } catch (e: Exception) {
                Timber.e(e, "Error in fetching company data")
            }
        }
    }

    fun fetchLatestLaunch() {
        viewModelScope.launch {
            try {
                _latestLaunch.postValue(repository.fetchLatestLaunch())
            } catch (e: Exception) {
                Timber.e(e, "Error in fetching latest launch data")
            }
        }
    }

    fun fetchRockets() {
        viewModelScope.launch {
            try {
                _rockets.postValue(repository.fetchRockets())
            } catch (e: Exception) {
                Timber.e(e, "Error in fetching rockets data")
            }
        }
    }

    fun fetchPastLaunches() {
        viewModelScope.launch {
            try {
                _pastLaunches.postValue(repository.fetchPastLaunches())
            } catch (e: Exception) {
                Timber.e(e, "Error in fetching past launches data")
            }
        }
    }

    fun fetchDragons() {
        viewModelScope.launch {
            try {
                _dragons.postValue(repository.fetchDragons())
            } catch (e: Exception) {
                Timber.e(e, "Error in fetching past launches data")
            }
        }
    }
}