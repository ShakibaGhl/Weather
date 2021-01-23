package com.example.weather.data.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.weather.data.db.entity.CurrentWeatherResponse


@Dao
interface  CurrentWeatherDao {
    @Insert(onConflict =  OnConflictStrategy.REPLACE)
    fun insertCurrentWeather ( currentWeather : CurrentWeatherResponse)

    @Update
    fun updateCurrentWeather ( currentWeather : CurrentWeatherResponse)

    @Delete
    fun deleteNote( currentWeather: CurrentWeatherResponse)

    @Query("SELECT * FROM CurrentWeatherResponse")
    fun getAllData() : CurrentWeatherResponse

}