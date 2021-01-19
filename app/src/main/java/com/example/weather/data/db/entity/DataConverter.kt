package com.example.weather.data.db.entity

import androidx.room.TypeConverter
import com.example.weather.data.db.entity.Weather
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

public class DataConverter {
    @TypeConverter
    fun fromWeatherList(weather: List<Weather?>?): String? {
        if (weather == null) {
            return null
        }
        val gson = Gson()
        val type: Type = object : TypeToken<List<Weather?>?>() {}.type
        return gson.toJson(weather, type)
    }

    @TypeConverter
    fun toWeatherList(weatherString: String?): List<Weather>? {
        if (weatherString == null) {
            return null
        }
        val gson = Gson()
        val type: Type = object : TypeToken<List<Weather?>?>() {}.type
        return gson.fromJson<List<Weather>>(weatherString, type)
    }
}