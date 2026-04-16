package com.example.demo.Model

data class ChartResponse(
    val market_caps: List<List<Double>>,
    val prices: List<List<Double>>,
    val total_volumes: List<List<Double>>
)