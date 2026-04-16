package com.example.demo.Model

data class Data(
    val active_cryptocurrencies: Int,
    val ended_icos: Int,
    val market_cap_change_percentage_24h_usd: Double,
    val market_cap_percentage: MarketCapPercentage,
    val markets: Int,
    val ongoing_icos: Int,
    val total_market_cap: TotalMarketCap,
    val total_volume: TotalVolume,
    val upcoming_icos: Int,
    val updated_at: Int,
    val volume_change_percentage_24h_usd: Double
)