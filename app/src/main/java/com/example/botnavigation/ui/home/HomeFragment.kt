package com.example.botnavigation.ui.home

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationRequest
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.constraintlayout.motion.widget.Debug.getLocation
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import com.example.botnavigation.R

import com.example.botnavigation.databinding.FragmentHomeBinding
import com.example.botnavigation.model.location.CurrentPosition
import com.example.botnavigation.viewmodels.HomeViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices

class HomeFragment : Fragment() {

    private val homeViewModel: HomeViewModel by viewModels()

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
         val preferences: SharedPreferences =  requireContext().getSharedPreferences("shared_prefs", Context.MODE_PRIVATE)
        //val lat = preferences.getFloat("latitude", 0.0F)
        //val lon = preferences.getFloat("longitude", 0.0F)
        val binding = FragmentHomeBinding.inflate(inflater)
        // Allows Data Binding to Observe LiveData with the lifecycle of this Fragment
        binding.lifecycleOwner = this
        binding.homeViewModel = homeViewModel




        // call the getWeatherData With the new location
        homeViewModel.position.observe(viewLifecycleOwner, { newValue ->
            homeViewModel.getCurrentWeatherData(newValue)
        })
        //Update UI on change =)

        homeViewModel.dataCurrentWeather.observe(viewLifecycleOwner) { newValue ->
            homeViewModel.text.value = newValue.current_weather.toString()
            //homeViewModel.temperature.value = newValue.current_weather.temperature.toString()


            when (newValue.current_weather.weathercode) {
                0 -> binding.imageView.setImageDrawable(getResources().getDrawable(R.drawable.ic_sunny))
                1, 2, 3 -> binding.imageView.setImageDrawable(getResources().getDrawable(R.drawable.ic_sunny))
                45, 48 -> binding.imageView.setImageDrawable(getResources().getDrawable(R.drawable.ic_launcher_background))
                else -> binding.imageView.setImageDrawable(getResources().getDrawable(R.drawable.ic_launcher_background))
            }

            binding.tempurature.text = newValue.current_weather.temperature.toString() + "°C"

            // is Day
            when(newValue.current_weather.is_day){
                0 -> {
                    binding.imageIsDay.setImageDrawable(getResources().getDrawable(R.drawable.ic_night))
                    binding.textIsDay.text = "NightTime"
                }
                else -> {
                    binding.imageIsDay.setImageDrawable(getResources().getDrawable(R.drawable.ic_sunny))
                    binding.textIsDay.text = "DayTime"
                }
            }
            //wind
            binding.textWind.text = newValue.current_weather.windspeed.toString() + "km/h"



        }
        getLocation()
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        //fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(requireContext())
    }

    //Location
    private fun getLocation() {
        var fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(requireContext())
        val task = fusedLocationProviderClient.lastLocation
        if (ActivityCompat.checkSelfPermission(requireContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION)
            != PackageManager.PERMISSION_GRANTED && ActivityCompat
                .checkSelfPermission(requireContext(),android.Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(requireActivity(),arrayOf(
                android.Manifest.permission.ACCESS_FINE_LOCATION,
                android.Manifest.permission.ACCESS_COARSE_LOCATION
            ),
                101
            )
            return
        }
        task.addOnSuccessListener {
            if(it!=null){
                //Toast.makeText(requireContext(), "${it.latitude}", Toast.LENGTH_LONG).show()
                Log.i("location", "${it.longitude}")
                homeViewModel.position.value= CurrentPosition(it.latitude,it.longitude)

            }
        }
    }
}