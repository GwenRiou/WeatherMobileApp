package com.example.botnavigation.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.botnavigation.model.location.CurrentPosition
import com.example.botnavigation.model.network.CurrentWeatherApiResponse.CurrentWeatherResponse
import com.example.botnavigation.model.network.DailyWeatherApiResponse.DailyWeatherResponse
import com.example.botnavigation.model.network.WeatherApi
import kotlinx.coroutines.launch

class DashboardViewModel : ViewModel() {

    enum class WeatherApiStatus { LOADING, ERROR, DONE }

    // The internal MutableLiveData that stores the status of the most recent request
    private val _status = MutableLiveData<WeatherApiStatus>()

    // The external immutable LiveData for the request status
    val status: LiveData<WeatherApiStatus> = _status

    private val _position = MutableLiveData<CurrentPosition>()
    var position : MutableLiveData<CurrentPosition> = _position

    var dataDailyWeather = MutableLiveData<DailyWeatherResponse>()

    private val _text = MutableLiveData<String>()
    val text: MutableLiveData<String> = _text
    fun getDailyWeatherData(pos: CurrentPosition){
        viewModelScope.launch {
            _status.value = DashboardViewModel.WeatherApiStatus.LOADING
            try {
                _text.value = "Sucess"
                dataDailyWeather.value =
                    position.value?.let {
                        WeatherApi.retrofitService.getDailyWeather(it.latitude,it.longitude)
                    }
                _status.value = DashboardViewModel.WeatherApiStatus.DONE
                Log.i("dataAPI", "${dataDailyWeather.value}")
            } catch (e: Exception) {
                _text.value = "Fail to get info  ${e.message}"
                _status.value = DashboardViewModel.WeatherApiStatus.ERROR
            }
        }
    }
}