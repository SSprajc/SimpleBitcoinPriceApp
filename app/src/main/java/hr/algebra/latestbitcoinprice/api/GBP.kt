package hr.algebra.latestbitcoinprice.api

import com.google.gson.annotations.SerializedName

data class GBP(
    @SerializedName("code") val code : String,
    @SerializedName("symbol") val symbol : String,
    @SerializedName("rate") val rate : String,
    @SerializedName("description") val description : String,
    @SerializedName("rate_float") val rate_float : Double
)
