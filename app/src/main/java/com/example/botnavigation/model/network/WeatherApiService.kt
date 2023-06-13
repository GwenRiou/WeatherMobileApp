package com.example.botnavigation.model.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private const val BASE_URL ="https://api.open-meteo.com/"
private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()


interface WeatherApiService {
    @GET("v1/forecast?daily=weathercode,temperature_2m_max,temperature_2m_min&current_weather=true&timezone=Europe%2FBerlin")
    suspend fun getData(
        @Query("latitude") lat: Double,
        @Query("longitude") long: Double
    ) : ResponseApi

}
object WeatherApi{
    val retrofitService: WeatherApiService by lazy { retrofit.create(WeatherApiService::class.java) }
}