package com.adi.magicspacex.repository.remote

import com.adi.magicspacex.models.CompanyData
import retrofit2.http.GET

interface SpacexClient {

    @GET("company")
    suspend fun getCompanyData(): CompanyData

}