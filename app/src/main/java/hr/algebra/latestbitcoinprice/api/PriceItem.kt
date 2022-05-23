package hr.algebra.latestbitcoinprice.api

import com.google.gson.annotations.SerializedName

data class PriceItem(
    @SerializedName("time") val time : Time,
    @SerializedName("disclaimer") val disclaimer : String,
    @SerializedName("chartName") val chartName : String,
    @SerializedName("bpi") val bpi : Bpi
)
