package com.adi.magicspacex.repository.remote

import retrofit2.http.GET

interface SpacexClient {

    @GET("company")
    suspend fun getCompanyData()

}