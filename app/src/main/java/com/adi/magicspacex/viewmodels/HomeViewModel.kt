package com.adi.magicspacex.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.adi.magicspacex.models.CompanyData
import com.adi.magicspacex.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val repository: Repository) : ViewModel() {
    private val _companyData = MutableLiveData<CompanyData?>()
    val companyData: LiveData<CompanyData?> = _companyData

    fun getCompanyData() {
        viewModelScope.launch {
            try {
                _companyData.value = repository.getCompanyData()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}