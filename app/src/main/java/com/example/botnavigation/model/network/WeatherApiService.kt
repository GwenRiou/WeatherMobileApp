package com.example.botnavigation.model.network

import com.example.botnavigation.model.network.CurrentWeatherApiResponse.CurrentWeatherResponse
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private const val BASE_URL ="https://api.open-meteo.com/"
//private const val BASE_URL = "http://api.weatherapi.com/"
private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()


interface WeatherApiService {

    ///v1/forecast?latitude=52.52&longitude=13.41&current_weather=true
    @GET("v1/forecast?current_weather=true")
    suspend fun getCurrentWeather(
        @Query("latitude") lat: Double,
        @Query("longitude") long: Double
    ) : CurrentWeatherResponse
    @GET("v1/forecast?daily=weathercode,temperature_2m_max,temperature_2m_min&current_weather=true&timezone=Europe%2FBerlin")
    suspend fun getData(
        @Query("latitude") lat: Double,
        @Query("longitude") long: Double
    ) : ResponseApi

    //@GET("
// v1/current.json?key=da53895e6cc04faea29130610231306 &q=48.8567,2.3508&aqi=no")
    //suspend fun getData(
       // @Query("q") pos: String,
    //) : ResponseApi
}
object WeatherApi{
    val retrofitService: WeatherApiService by lazy { retrofit.create(WeatherApiService::class.java) }
}