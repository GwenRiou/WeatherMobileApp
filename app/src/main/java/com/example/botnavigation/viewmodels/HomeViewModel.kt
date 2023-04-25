package com.example.botnavigation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.botnavigation.model.network.WeatherApi
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {

    // The internal MutableLiveData that stores the status of the most recent request
    private val _status = MutableLiveData<String>()

    // The external immutable LiveData for the request status
    val status: LiveData<String> = _status

    private val _text = MutableLiveData<String>()
    val text: LiveData<String> = _text

    init {
        getWeatherData()
    }

    private fun getWeatherData() {
        viewModelScope.launch {
            try {
                val data = WeatherApi.retrofitService.getData()
                _text.value = "Sucess ${data}"
            } catch (e: Exception) {
                _text.value = "Fail to get info"
                _status.value = "Failure: ${e.message}"
            }
        }
    }

}