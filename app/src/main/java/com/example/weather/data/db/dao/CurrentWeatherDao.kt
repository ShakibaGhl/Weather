package com.example.weather.data.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Update
import com.example.weather.data.db.entity.CurrentWeatherResponse


@Dao
interface CurrentWeatherDao {
    @Insert
    fun insertCurrentWeather ( currentWeather : CurrentWeatherResponse)

    @Update
    fun updateCurrentWeather ( currentWeather : CurrentWeatherResponse)

    @Delete
    fun deleteNote( currentWeather: CurrentWeatherResponse)

}