package com.example.demo.ViewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.demo.Model.ChartResponse
import com.example.demo.Model.CryptoResponse
import com.example.demo.Model.CryptoResponseItem
import com.example.demo.Model.MarketResponse
import com.example.demo.Repository.cryptoRepository
import kotlinx.coroutines.launch
import retrofit2.Response

class cryptoViewModel(app: Application, val repo: cryptoRepository): AndroidViewModel(app) {

    private val _cryptoList= MutableLiveData<List<CryptoResponseItem>>()
    val cryptoList: MutableLiveData<List<CryptoResponseItem>> get()= _cryptoList

    private val _chartData= MutableLiveData<ChartResponse>()
    val chartData: MutableLiveData<ChartResponse> get() = _chartData

    private val _selectedCoin= MutableLiveData<CryptoResponseItem>()
    val selectedCoin: MutableLiveData<CryptoResponseItem> get()= _selectedCoin

    private val _marketData= MutableLiveData<MarketResponse>()
    val marketData: MutableLiveData<MarketResponse> get()= _marketData

    private val _error= MutableLiveData<String>()
    val error: MutableLiveData<String> get() = _error

    private fun handleCryptoListResponse(response: Response<CryptoResponse>){
        if(response.isSuccessful){
            response.body()?.let { cryptoResponse->
                _cryptoList.postValue(cryptoResponse)
            }
        }
        else{
            _error.postValue(response.message())
        }
    }

    private fun handleChartResponse(response: Response<ChartResponse>){
        if (response.isSuccessful){
            response.body()?.let{chartResponse->
                _chartData.postValue(chartResponse)
            }
        }
        else{
            _error.postValue(response.message())
        }
    }

    private fun handleMarketResponse(response: Response<MarketResponse>){
        if(response.isSuccessful){
            response.body()?.let{marketResponse->
                _marketData.postValue(marketResponse)
            }
        }
        else{
            _error.postValue(response.message())
        }
    }

     fun getCryptoList(currency:String, order:String,numberPP:Int){
        viewModelScope.launch{
            try{
                val response=repo.getCryptoList(currency,order, numberPP)
                handleCryptoListResponse(response)

            }
            catch (e:Exception){
                _error.postValue(e.message?: "Unknown error")
            }
        }
    }

    fun getChart(id:String,currency:String,days:String){
        viewModelScope.launch{
            try {
                val response= repo.getChart(id,currency,days)
                handleChartResponse(response)
            }
            catch (e:Exception){
                _error.postValue(e.message?:"Unknown error")
            }
        }
    }

    fun getMarketData(){
        viewModelScope.launch {
            try {
                val response= repo.getMarketData()
                handleMarketResponse(response)
            }
            catch (e: Exception){
                _error.postValue(e.message?:"Unknown error")
            }
        }
    }

    fun selectCoin(coin: CryptoResponseItem) {
        _selectedCoin.value = coin
    }
}