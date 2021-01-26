package com.example.weather.data

import com.example.weather.data.db.entity.FutureWeatherResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiFutureWeather {

    @GET("/data/2.5/forecast")
    fun getFutureWeather(
        @Query("lat") lat : Double ,@Query("lon") lon : Double, @Query("appid") appId : String, @Query("units") units : String
    ): Call<FutureWeatherResponse>
}