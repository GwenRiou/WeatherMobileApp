package com.example.botnavigation.model.network

data class Daily(
    val temperature_2m_max: List<Double>,
    val temperature_2m_min: List<Double>,
    val time: List<String>,
    val weathercode: List<Int>
)