package com.example.weather.data.db.entity

import androidx.room.Embedded
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.google.gson.annotations.SerializedName

data class MyList (

        @PrimaryKey(autoGenerate = true)
        val id : Int? = null,

        @SerializedName("dt")
        val dt: Int,
        @Embedded( prefix = "main_")
        val main: Main,

        @Embedded( prefix = "coord_")
        val coord: Coord,

        @SerializedName("weather")
        @TypeConverters(WeatherListConverter::class)
        val weather: List<Weather>,

        @Embedded( prefix = "sys_")
        val sys: Sys,

        @SerializedName("dt_txt")
        val dt_txt: String
)
