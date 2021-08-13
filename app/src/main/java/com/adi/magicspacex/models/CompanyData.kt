package com.adi.magicspacex.models

import com.google.gson.annotations.SerializedName

data class CompanyData(
    @SerializedName("name") val name: String,
    @SerializedName("founder") val founder: String
)
