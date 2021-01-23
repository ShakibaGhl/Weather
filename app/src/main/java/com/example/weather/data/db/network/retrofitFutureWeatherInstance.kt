package com.example.weather.data.db.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object retrofitFutureWeatherInstance {

    var INSTANCE : Retrofit? = null

    fun getFutureRetrofitInstance() : Retrofit {

        if (INSTANCE == null) {
            INSTANCE = Retrofit.Builder()
                    .baseUrl("http://pro.openweathermap.org")
                    .addConverterFactory(GsonConverterFactory.create())

                    .build()
        }
        return INSTANCE!!
    }
}