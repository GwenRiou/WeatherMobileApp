package com.example.botnavigation.model.location

import android.util.Log

class CurrentLocation private constructor(
    val latitude:Double,
    val longitude : Double
) {
    companion object {
        @Volatile
        private var instance: CurrentLocation? = null

        fun getInstance(
            lat : Double,
            long : Double
        ): CurrentLocation {
            if (instance == null) {
                synchronized(this) {
                    if (instance == null) {
                        instance = CurrentLocation(lat,long)
                        Log.i("Currentlocation", "${lat}")
                    }
                }
            }
            return instance!!
        }
        fun getInstance(): CurrentLocation {
            if (instance == null) {
                synchronized(this) {
                    if (instance == null) {
                        instance = CurrentLocation(0.000,0.000)// error
                    }
                }
            }
            return instance!!
        }
    }
}

