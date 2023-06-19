package com.example.botnavigation.ui.dashboard

import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.icu.text.DateFormat
import android.icu.text.SimpleDateFormat
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.botnavigation.R
import com.example.botnavigation.databinding.FragmentDashboardBinding
import com.example.botnavigation.model.location.CurrentPosition
import com.example.botnavigation.viewmodels.DashboardViewModel
import com.google.android.gms.location.LocationServices
import java.time.LocalDate
import java.util.*

class DashboardFragment : Fragment() {

    private val dashViewModel: DashboardViewModel by viewModels()

    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentDashboardBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.dashViewModel = dashViewModel

        //Status of the api response
        binding.statusImage.visibility = View.VISIBLE
        binding.statusImage.setImageResource(R.drawable.loading_animation)

        // call the getWeatherData With the new location
        dashViewModel.position.observe(viewLifecycleOwner, { newValue ->
            dashViewModel.getDailyWeatherData(newValue)
        })
        //check if the result of the api call fail or not
        dashViewModel.status.observe(viewLifecycleOwner, { newValue ->
            if (dashViewModel.status.value == DashboardViewModel.WeatherApiStatus.ERROR ) {
                binding.statusImage.visibility = View.VISIBLE
                binding.statusImage.setImageResource(R.drawable.ic_connection_error)
            }else{
                binding.statusImage.visibility = View.GONE
            }
        })
     //Update UI once we get data =)
        dashViewModel.dataDailyWeather.observe(viewLifecycleOwner) { newValue ->
            dashViewModel.text.value = newValue.daily.toString()

            //get date

            //today
            var date = LocalDate.parse(newValue.daily.time[0])
            var day = date.dayOfWeek
            var dayOfMonth = date.dayOfMonth
            var month  = date.month
            binding.dateText.text = "$day $dayOfMonth $month"
            when (newValue.daily.weathercode[0]) {
                0 -> binding.imageView.setImageDrawable(getResources().getDrawable(R.drawable.ic_sunny))
                1, 2 -> binding.imageView.setImageDrawable(getResources().getDrawable(R.drawable.ic_sun_cloud))
                3 -> binding.imageView.setImageDrawable(getResources().getDrawable(R.drawable.ic_cloud))
                45, 48 -> binding.imageView.setImageDrawable(getResources().getDrawable(R.drawable.ic_fog))
                51, 53, 55 -> binding.imageView.setImageDrawable(getResources().getDrawable(R.drawable.ic_drizzle))
                56, 57 -> binding.imageView.setImageDrawable(getResources().getDrawable(R.drawable.ic_freezing_drizzle))
                61, 63, 65, 80, 81, 82 -> binding.imageView.setImageDrawable(getResources().getDrawable(
                    R.drawable.ic_rainy))
                66, 67 	-> binding.imageView.setImageDrawable(getResources().getDrawable(R.drawable.ic_freezing_rain))
                71, 73, 75, 85, 86 -> binding.imageView.setImageDrawable(getResources().getDrawable(
                    R.drawable.ic_snow))
                77 ->	binding.imageView.setImageDrawable(getResources().getDrawable(R.drawable.ic_snow_gains))
                95,96, 99 -> binding.imageView.setImageDrawable(getResources().getDrawable(R.drawable.ic_thunderstorm))
                else -> binding.imageView.setImageDrawable(getResources().getDrawable(R.drawable.ic_launcher_background))
            }

            binding.textTempMax.text = newValue.daily.temperature_2m_max[0].toString() + "°C   max"
            binding.textTempMin.text = newValue.daily.temperature_2m_min[0].toString() + "°C   min"

            //day1
            date = LocalDate.parse(newValue.daily.time[1])
            day = date.dayOfWeek
            dayOfMonth = date.dayOfMonth
            month  = date.month
            binding.dateText1.text =  "$day $dayOfMonth $month"
            when (newValue.daily.weathercode[1]) {
                0 -> binding.imageView1.setImageDrawable(getResources().getDrawable(R.drawable.ic_sunny))
                1, 2 -> binding.imageView1.setImageDrawable(getResources().getDrawable(R.drawable.ic_sun_cloud))
                3 -> binding.imageView1.setImageDrawable(getResources().getDrawable(R.drawable.ic_cloud))
                45, 48 -> binding.imageView1.setImageDrawable(getResources().getDrawable(R.drawable.ic_fog))
                51, 53, 55 -> binding.imageView1.setImageDrawable(getResources().getDrawable(R.drawable.ic_drizzle))
                56, 57 -> binding.imageView1.setImageDrawable(getResources().getDrawable(R.drawable.ic_freezing_drizzle))
                61, 63, 65, 80, 81, 82 -> binding.imageView1.setImageDrawable(getResources().getDrawable(
                    R.drawable.ic_rainy))
                66, 67 	-> binding.imageView1.setImageDrawable(getResources().getDrawable(R.drawable.ic_freezing_rain))
                71, 73, 75, 85, 86 -> binding.imageView1.setImageDrawable(getResources().getDrawable(
                    R.drawable.ic_snow))
                77 ->	binding.imageView1.setImageDrawable(getResources().getDrawable(R.drawable.ic_snow_gains))
                95,96, 99 -> binding.imageView1.setImageDrawable(getResources().getDrawable(R.drawable.ic_thunderstorm))
                else -> binding.imageView1.setImageDrawable(getResources().getDrawable(R.drawable.ic_launcher_background))
            }

            binding.textTempMax1.text = newValue.daily.temperature_2m_max[1].toString() + "°C   max"
            binding.textTempMin1.text = newValue.daily.temperature_2m_min[1].toString() + "°C   min"

            //day2
            date = LocalDate.parse(newValue.daily.time[2])
            day = date.dayOfWeek
            dayOfMonth = date.dayOfMonth
            month  = date.month
            binding.dateText2.text =  "$day $dayOfMonth $month"
            when (newValue.daily.weathercode[2]) {
                0 -> binding.imageView2.setImageDrawable(getResources().getDrawable(R.drawable.ic_sunny))
                1, 2 -> binding.imageView2.setImageDrawable(getResources().getDrawable(R.drawable.ic_sun_cloud))
                3 -> binding.imageView2.setImageDrawable(getResources().getDrawable(R.drawable.ic_cloud))
                45, 48 -> binding.imageView2.setImageDrawable(getResources().getDrawable(R.drawable.ic_fog))
                51, 53, 55 -> binding.imageView2.setImageDrawable(getResources().getDrawable(R.drawable.ic_drizzle))
                56, 57 -> binding.imageView2.setImageDrawable(getResources().getDrawable(R.drawable.ic_freezing_drizzle))
                61, 63, 65, 80, 81, 82 -> binding.imageView2.setImageDrawable(getResources().getDrawable(
                    R.drawable.ic_rainy))
                66, 67 	-> binding.imageView2.setImageDrawable(getResources().getDrawable(R.drawable.ic_freezing_rain))
                71, 73, 75, 85, 86 -> binding.imageView2.setImageDrawable(getResources().getDrawable(
                    R.drawable.ic_snow))
                77 ->	binding.imageView2.setImageDrawable(getResources().getDrawable(R.drawable.ic_snow_gains))
                95,96, 99 -> binding.imageView2.setImageDrawable(getResources().getDrawable(R.drawable.ic_thunderstorm))
                else -> binding.imageView2.setImageDrawable(getResources().getDrawable(R.drawable.ic_launcher_background))
            }

            binding.textTempMax2.text = newValue.daily.temperature_2m_max[2].toString() + "°C   max"
            binding.textTempMin2.text = newValue.daily.temperature_2m_min[2].toString() + "°C   min"

            //day3
            date = LocalDate.parse(newValue.daily.time[3])
            day = date.dayOfWeek
            dayOfMonth = date.dayOfMonth
            month  = date.month
            binding.dateText3.text =  "$day $dayOfMonth $month"
            when (newValue.daily.weathercode[3]) {
                0 -> binding.imageView3.setImageDrawable(getResources().getDrawable(R.drawable.ic_sunny))
                1, 2 -> binding.imageView3.setImageDrawable(getResources().getDrawable(R.drawable.ic_sun_cloud))
                3 -> binding.imageView3.setImageDrawable(getResources().getDrawable(R.drawable.ic_cloud))
                45, 48 -> binding.imageView3.setImageDrawable(getResources().getDrawable(R.drawable.ic_fog))
                51, 53, 55 -> binding.imageView3.setImageDrawable(getResources().getDrawable(R.drawable.ic_drizzle))
                56, 57 -> binding.imageView3.setImageDrawable(getResources().getDrawable(R.drawable.ic_freezing_drizzle))
                61, 63, 65, 80, 81, 82 -> binding.imageView3.setImageDrawable(getResources().getDrawable(
                    R.drawable.ic_rainy))
                66, 67 	-> binding.imageView3.setImageDrawable(getResources().getDrawable(R.drawable.ic_freezing_rain))
                71, 73, 75, 85, 86 -> binding.imageView3.setImageDrawable(getResources().getDrawable(
                    R.drawable.ic_snow))
                77 ->	binding.imageView3.setImageDrawable(getResources().getDrawable(R.drawable.ic_snow_gains))
                95,96, 99 -> binding.imageView3.setImageDrawable(getResources().getDrawable(R.drawable.ic_thunderstorm))
                else -> binding.imageView3.setImageDrawable(getResources().getDrawable(R.drawable.ic_launcher_background))
            }

            binding.textTempMax3.text = newValue.daily.temperature_2m_max[3].toString() + "°C   max"
            binding.textTempMin3.text = newValue.daily.temperature_2m_min[3].toString() + "°C   min"

            //day4
            date = LocalDate.parse(newValue.daily.time[4])
            day = date.dayOfWeek
            dayOfMonth = date.dayOfMonth
            month  = date.month
            binding.dateText4.text =  "$day $dayOfMonth $month"
            when (newValue.daily.weathercode[4]) {
                0 -> binding.imageView4.setImageDrawable(getResources().getDrawable(R.drawable.ic_sunny))
                1, 2 -> binding.imageView4.setImageDrawable(getResources().getDrawable(R.drawable.ic_sun_cloud))
                3 -> binding.imageView4.setImageDrawable(getResources().getDrawable(R.drawable.ic_cloud))
                45, 48 -> binding.imageView4.setImageDrawable(getResources().getDrawable(R.drawable.ic_fog))
                51, 53, 55 -> binding.imageView4.setImageDrawable(getResources().getDrawable(R.drawable.ic_drizzle))
                56, 57 -> binding.imageView4.setImageDrawable(getResources().getDrawable(R.drawable.ic_freezing_drizzle))
                61, 63, 65, 80, 81, 82 -> binding.imageView4.setImageDrawable(getResources().getDrawable(
                    R.drawable.ic_rainy))
                66, 67 	-> binding.imageView4.setImageDrawable(getResources().getDrawable(R.drawable.ic_freezing_rain))
                71, 73, 75, 85, 86 -> binding.imageView4.setImageDrawable(getResources().getDrawable(
                    R.drawable.ic_snow))
                77 ->	binding.imageView4.setImageDrawable(getResources().getDrawable(R.drawable.ic_snow_gains))
                95,96, 99 -> binding.imageView4.setImageDrawable(getResources().getDrawable(R.drawable.ic_thunderstorm))
                else -> binding.imageView4.setImageDrawable(getResources().getDrawable(R.drawable.ic_launcher_background))
            }

            binding.textTempMax4.text = newValue.daily.temperature_2m_max[4].toString() + "°C   max"
            binding.textTempMin4.text = newValue.daily.temperature_2m_min[4].toString() + "°C   min"

            //day5
            date = LocalDate.parse(newValue.daily.time[5])
            day = date.dayOfWeek
            dayOfMonth = date.dayOfMonth
            month  = date.month
            binding.dateText5.text =  "$day $dayOfMonth $month"
            when (newValue.daily.weathercode[5]) {
                0 -> binding.imageView5.setImageDrawable(getResources().getDrawable(R.drawable.ic_sunny))
                1, 2 -> binding.imageView5.setImageDrawable(getResources().getDrawable(R.drawable.ic_sun_cloud))
                3 -> binding.imageView5.setImageDrawable(getResources().getDrawable(R.drawable.ic_cloud))
                45, 48 -> binding.imageView5.setImageDrawable(getResources().getDrawable(R.drawable.ic_fog))
                51, 53, 55 -> binding.imageView5.setImageDrawable(getResources().getDrawable(R.drawable.ic_drizzle))
                56, 57 -> binding.imageView5.setImageDrawable(getResources().getDrawable(R.drawable.ic_freezing_drizzle))
                61, 63, 65, 80, 81, 82 -> binding.imageView5.setImageDrawable(getResources().getDrawable(
                    R.drawable.ic_rainy))
                66, 67 	-> binding.imageView5.setImageDrawable(getResources().getDrawable(R.drawable.ic_freezing_rain))
                71, 73, 75, 85, 86 -> binding.imageView5.setImageDrawable(getResources().getDrawable(
                    R.drawable.ic_snow))
                77 ->	binding.imageView5.setImageDrawable(getResources().getDrawable(R.drawable.ic_snow_gains))
                95,96, 99 -> binding.imageView5.setImageDrawable(getResources().getDrawable(R.drawable.ic_thunderstorm))
                else -> binding.imageView5.setImageDrawable(getResources().getDrawable(R.drawable.ic_launcher_background))
            }

            binding.textTempMax5.text = newValue.daily.temperature_2m_max[5].toString() + "°C   max"
            binding.textTempMin5.text = newValue.daily.temperature_2m_min[5].toString() + "°C   min"

            //day6
            date = LocalDate.parse(newValue.daily.time[6])
            day = date.dayOfWeek
            dayOfMonth = date.dayOfMonth
            month  = date.month
            binding.dateText6.text =  "$day $dayOfMonth $month"
            when (newValue.daily.weathercode[6]) {
                0 -> binding.imageView6.setImageDrawable(getResources().getDrawable(R.drawable.ic_sunny))
                1, 2 -> binding.imageView6.setImageDrawable(getResources().getDrawable(R.drawable.ic_sun_cloud))
                3 -> binding.imageView6.setImageDrawable(getResources().getDrawable(R.drawable.ic_cloud))
                45, 48 -> binding.imageView6.setImageDrawable(getResources().getDrawable(R.drawable.ic_fog))
                51, 53, 55 -> binding.imageView6.setImageDrawable(getResources().getDrawable(R.drawable.ic_drizzle))
                56, 57 -> binding.imageView6.setImageDrawable(getResources().getDrawable(R.drawable.ic_freezing_drizzle))
                61, 63, 65, 80, 81, 82 -> binding.imageView6.setImageDrawable(getResources().getDrawable(
                    R.drawable.ic_rainy))
                66, 67 	-> binding.imageView6.setImageDrawable(getResources().getDrawable(R.drawable.ic_freezing_rain))
                71, 73, 75, 85, 86 -> binding.imageView6.setImageDrawable(getResources().getDrawable(
                    R.drawable.ic_snow))
                77 ->	binding.imageView6.setImageDrawable(getResources().getDrawable(R.drawable.ic_snow_gains))
                95,96, 99 -> binding.imageView6.setImageDrawable(getResources().getDrawable(R.drawable.ic_thunderstorm))
                else -> binding.imageView6.setImageDrawable(getResources().getDrawable(R.drawable.ic_launcher_background))
            }

            binding.textTempMax6.text = newValue.daily.temperature_2m_max[6].toString() + "°C   max"
            binding.textTempMin6.text = newValue.daily.temperature_2m_min[6].toString() + "°C   min"

        }

        getLocation()
        return binding.root
    }
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
                dashViewModel.position.value= CurrentPosition(it.latitude,it.longitude)

            }
        }
    }
}