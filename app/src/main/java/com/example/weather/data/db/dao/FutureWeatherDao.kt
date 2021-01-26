package com.example.weather.data.db.dao

import androidx.room.*
import com.example.weather.data.db.entity.CurrentWeatherResponse
import com.example.weather.data.db.entity.FutureWeatherResponse


@Dao
interface FutureWeatherDao {

    @Insert(onConflict =  OnConflictStrategy.REPLACE)
    fun insertFutureWeather ( FutureWeather : FutureWeatherResponse)

    @Update
    fun updateFutureWeather ( FutureWeather : FutureWeatherResponse)

    @Delete
    fun delete( FutureWeather: FutureWeatherResponse)

    @Query("SELECT * FROM FutureWeatherResponse")
    fun getAllData() : FutureWeatherResponse

}