package com.adi.magicspacex.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.adi.magicspacex.models.company_info.CompanyInfo
import com.adi.magicspacex.models.latest_launch.LatestLaunch
import com.adi.magicspacex.models.rockets.Rocket
import com.adi.magicspacex.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val repository: Repository) : ViewModel() {
    private val _companyData = MutableLiveData<CompanyInfo?>()
    private val _latestLaunch = MutableLiveData<LatestLaunch?>()
    private val _rockets = MutableLiveData<Rocket?>()

    val companyData: LiveData<CompanyInfo?> = _companyData
    val latestLaunch: LiveData<LatestLaunch?> = _latestLaunch
    val rockets: LiveData<Rocket?> = _rockets

    fun getCompanyData() {
        viewModelScope.launch {
            try {
                _companyData.postValue(repository.getCompanyData())
            } catch (e: Exception) {
                Timber.e(e, "Error in getting company data")
            }
        }
    }

    fun getLatestLaunch() {
        viewModelScope.launch {
            try {
                _latestLaunch.postValue(repository.getLatestLaunch())
            } catch (e: Exception) {
                Timber.e(e, "Error in getting latest launch data")
            }
        }
    }

    fun getRockets() {
        viewModelScope.launch {
            try {
                _rockets.postValue(repository.getRockets())
            } catch (e: Exception) {
                Timber.e(e, "Error in getting rockets data")
            }
        }
    }
}