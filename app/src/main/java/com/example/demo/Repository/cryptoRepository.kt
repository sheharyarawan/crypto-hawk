package com.example.demo.Repository

import com.example.demo.Api.RetrofitInstance
import com.example.demo.Database.CryptoDatabase
import com.example.demo.Model.ChartResponse
import com.example.demo.Model.CryptoResponse
import com.example.demo.Model.MarketResponse
import retrofit2.Response

class cryptoRepository(val db: CryptoDatabase) {

    suspend fun getCryptoList(currency: String, order:String , numberPP:Int): Response<CryptoResponse>{
        return RetrofitInstance.api
            .getCryptoList(currency,order, numberPP , page = 1, sparkline=false)
    }
    suspend fun getChart(id:String, currency: String, days:String): Response<ChartResponse>{
        return RetrofitInstance.api.getMarketChart(id,currency, days)
    }

    suspend fun getMarketData(): Response<MarketResponse>{
        return RetrofitInstance.api.getMarketData()
    }

    suspend fun addToFav(){

    }
}