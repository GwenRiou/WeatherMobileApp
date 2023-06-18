package com.example.botnavigation.viewmodels

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.graphics.drawable.Drawable
import android.icu.number.IntegerWidth
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.botnavigation.R
import com.example.botnavigation.model.location.CurrentPosition
import com.example.botnavigation.model.network.CurrentWeather
import com.example.botnavigation.model.network.CurrentWeatherApiResponse.CurrentWeatherResponse
import com.example.botnavigation.model.network.ResponseApi
import com.example.botnavigation.model.network.WeatherApi
import kotlinx.coroutines.launch


class HomeViewModel : ViewModel() {

    // The internal MutableLiveData that stores the status of the most recent request
    private val _status = MutableLiveData<String>()

    // The external immutable LiveData for the request status
    val status: LiveData<String> = _status



    private val _position = MutableLiveData<CurrentPosition>()
    var position : MutableLiveData<CurrentPosition> = _position

    var data = MutableLiveData<ResponseApi>()
    var dataCurrentWeather = MutableLiveData<CurrentWeatherResponse>()


    //UI
    private val _text = MutableLiveData<String>()
    val text: MutableLiveData<String> = _text

    private val _temperature = MutableLiveData<String>()
    val temperature: MutableLiveData<String> = _temperature


    fun getCurrentWeatherData(pos : CurrentPosition) {
        viewModelScope.launch {
            try {
                _text.value = "Sucess"
                dataCurrentWeather.value =
                    position.value?.let {
                        WeatherApi.retrofitService.getCurrentWeather(it.latitude,it.longitude)
                    }

                Log.i("dataAPI", "${data.value}")
            } catch (e: Exception) {
                _text.value = "Fail to get info  ${e.message}"
                _status.value = "Failure: ${e.message}"
            }
        }
    }
    fun getWeatherData(pos : CurrentPosition) {
        viewModelScope.launch {
            try {
                _text.value = "Sucess"
                 data.value =
                     position.value?.let {
                         WeatherApi.retrofitService.getData(it.latitude,it.longitude)
                     }

                Log.i("dataAPI", "${data.value}")
            } catch (e: Exception) {
                _text.value = "Fail to get info  ${e.message}"
                _status.value = "Failure: ${e.message}"
            }
        }
    }

}