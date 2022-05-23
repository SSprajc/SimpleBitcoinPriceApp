package hr.algebra.latestbitcoinprice.api

import retrofit2.Call
import retrofit2.http.GET

const val API_URL = "https://api.coindesk.com/"
interface PriceApi {
    @GET("v1/bpi/currentprice.json")
    fun fetchItems() : Call<PriceItem>
}