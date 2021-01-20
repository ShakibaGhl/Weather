package com.example.weather.weather.current

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.weather.R
import com.example.weather.data.ApiCurrentweather
import com.example.weather.data.db.entity.*
import com.example.weather.data.db.network.retrofitWeatherInstance
import kotlinx.android.synthetic.main.current_weather_fragment.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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
        progressBar.visibility = View.VISIBLE

        val retrofit = retrofitWeatherInstance.getRetrofitInstance()
        val currentWeatherService = retrofit.create(ApiCurrentweather::class.java)

        currentWeatherService.getCurrentWeather("tehran" , "4a7539e81417846fb7a62e1520d84a87" , "metric").
        enqueue(object : Callback<CurrentWeatherResponse> {

            override fun onFailure(call: Call<CurrentWeatherResponse>, t: Throwable) {
                progressBar.visibility = View.GONE
                Toast.makeText(requireContext() , "failure" , Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<CurrentWeatherResponse>, response: Response<CurrentWeatherResponse>) {
                progressBar.visibility = View.GONE
                text_view_city.text = response.body()?.name
                text_view_time.text = response.body()?.dt.toString()
                text_view_date.text = response.body()?.timezone.toString()
                response.body()?.main?.let { updateMain(it) }
                response.body()?.sys?.let { updateSys(it) }
                var url = response.body()?.weather?.let { updateWeather(it) }
                response.body()?.coord?.let { updateCoord(it) }
                response.body()?.wind?.let { updateWind(it) }
                Glide.with(requireContext())
                        .load("http://openweathermap.org/img/wn/$url@2x.png")
                        .into(image_view_weather_condition)
            }
        })

    }


    private fun updateMain(main : Main){
        view_humidity.text = main.humidity.toString() + "  %"
        view_pressure.text = main.pressure.toString() + "  hPa"
        text_view_temp.text = main.temp.toString() + " \u2103"
    }

    private fun updateSys(sys : Sys){
        view_sunrise.text = sys.sunrise.toString()
        view_sunset.text = sys.sunset.toString()
    }

    private fun updateWeather(weather: List<Weather>): String {
        text_view_weather_condition.text = weather[0].description
        var iconString = weather[0].icon
        return iconString
    }


    private fun updateCoord(coord : Coord){
        view_latitude.text = coord.lat.toString() + "\u00B0  N"
        view_longitude.text = coord.lon.toString() + "\u00B0  S"
    }

    private fun updateWind(wind: Wind){
        view_windSpeed.text = wind.speed.toString() + "  m/s"
    }


}