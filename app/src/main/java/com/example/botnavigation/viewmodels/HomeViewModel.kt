package com.example.botnavigation.viewmodels

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.botnavigation.R
import com.example.botnavigation.model.network.WeatherApi
import kotlinx.coroutines.launch


class HomeViewModel : ViewModel() {

    // The internal MutableLiveData that stores the status of the most recent request
    private val _status = MutableLiveData<String>()

    // The external immutable LiveData for the request status
    val status: LiveData<String> = _status

    private val _text = MutableLiveData<String>()
    val text: MutableLiveData<String> = _text

    private var _latitude = MutableLiveData<Double>()
    var latitude :MutableLiveData<Double> = _latitude

    private var _longitude = MutableLiveData<Double>()
    var longitude :MutableLiveData<Double> = _longitude
    init {
        getWeatherData()
    }

    private fun getWeatherData() {
        viewModelScope.launch {
            try {
                _text.value = "Sucess ${latitude}"
                val data = WeatherApi.retrofitService.getData()

            } catch (e: Exception) {
                _text.value = "Fail to get info"
                _status.value = "Failure: ${e.message}"
            }
        }
    }

}