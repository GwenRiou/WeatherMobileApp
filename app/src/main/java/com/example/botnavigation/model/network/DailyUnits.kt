package com.example.botnavigation.model.network

data class DailyUnits(
    val temperature_2m_max: String,
    val temperature_2m_min: String,
    val time: String,
    val weathercode: String
)