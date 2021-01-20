package com.example.weather.data

import com.example.weather.data.db.entity.CurrentWeatherResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


interface ApiCurrentweather {

    @GET("/data/2.5/weather")
    fun getCurrentWeather(
        @Query("q") cityName: String , @Query("appid") appId : String ,  @Query("units") units : String
    ): Call<CurrentWeatherResponse>

}