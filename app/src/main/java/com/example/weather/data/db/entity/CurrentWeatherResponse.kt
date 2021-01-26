package com.example.weather.data.db.entity

import androidx.room.*
import com.google.gson.annotations.SerializedName

const val CURRENT_WEATHER_ID = 0

@Entity
data class CurrentWeatherResponse(
        @SerializedName("base")
        val base: String,
    //@SerializedName("clouds")
   // val clouds: Clouds,
        @SerializedName("cod")
        val cod: Int,
        @Embedded( prefix = "coord_")
        val coord: Coord,
        @SerializedName("dt")
        val dt: Int,
        @SerializedName("id")
        val id: Int,
        @Embedded( prefix = "main_")
        val main: Main,
        @SerializedName("name")
        val name: String,
        @Embedded( prefix = "sys_")
        val sys: Sys,
        @SerializedName("timezone")
        val timezone: Int,
        @SerializedName("visibility")
        val visibility: Int,

        @SerializedName("weather")
        @TypeConverters(WeatherListConverter::class)
        val weather: List<Weather>,

        @Embedded( prefix = "wind_")
        val wind: Wind
) {
    @PrimaryKey(autoGenerate = false)
    var idCurrentWeather : Int = CURRENT_WEATHER_ID
}