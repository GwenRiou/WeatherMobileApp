package com.example.botnavigation.model.network

import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET

private const val BASE_URL ="https://api.open-meteo.com/"

private val retrofit = Retrofit.Builder()
    .addConverterFactory(ScalarsConverterFactory.create())
    .baseUrl(BASE_URL)
    .build()

interface WeatherApiService {
    @GET("v1/forecast?latitude=48.85&longitude=2.35&hourly=temperature_2m")
    suspend fun getData() : String

}
object WeatherApi{
    val retrofitService: WeatherApiService by lazy { retrofit.create(WeatherApiService::class.java) }
}