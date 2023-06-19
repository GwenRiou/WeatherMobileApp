package com.example.botnavigation.ui.notifications

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class NotificationsViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "weather icon by titusurya \n Half moon icons created by Freepik"
    }
    val text: LiveData<String> = _text
}