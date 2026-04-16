package com.example.demo.Api

import com.example.demo.Model.ChartResponse
import com.example.demo.Model.CryptoResponse
import com.example.demo.Model.MarketResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface cryptoApi {

    @GET("coins/markets")
    suspend fun getCryptoList(

        @Query("vs_currency")
        currency: String = "usd",
        @Query("order")
        order: String = "market_cap_desc",
        @Query("per_page")
        numberPerPage: Int,
        @Query("page")
        page: Int = 1,
        @Query("sparkline")
        sparkline: Boolean = false

    ): Response<CryptoResponse>

    @GET("coins/{id}/market_chart")
    suspend fun getMarketChart(
        @Path("id")
        id:String,
        @Query("vs_currency")
        currency: String,
        @Query("days")
        days:String
    ): Response<ChartResponse>

    @GET("global")
    suspend fun getMarketData(): Response<MarketResponse>
}