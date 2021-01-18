package com.example.weather.weather.current

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.weather.R
import com.example.weather.data.ApiCurrentweather
import com.example.weather.data.db.network.retrofitWeatherInstance
import kotlinx.android.synthetic.main.current_weather_fragment.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.await

class CurrentWeatherFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.current_weather_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getWeather()




    }

    private fun getWeather(){
        val retrofit = retrofitWeatherInstance.getRetrofitInstance()
        val currentWeatherService = retrofit.create(ApiCurrentweather::class.java)
        currentWeatherService.getCurrentWeather("tehran" , "4a7539e81417846fb7a62e1520d84a87")

        GlobalScope.launch (Dispatchers.Main) {
            val  currentWeatherResponse = currentWeatherService.getCurrentWeather("tehran", "4a7539e81417846fb7a62e1520d84a87").await()
            textView.text = currentWeatherResponse.toString()
        }
    }
}