package com.example.weather.data.db.entity

import androidx.room.Embedded
import androidx.room.TypeConverters
import com.example.weather.data.db.entity.Coord
import com.example.weather.data.db.entity.DataConverter
import com.example.weather.data.db.entity.Main
import com.example.weather.data.db.entity.Sys
import com.example.weather.data.db.entity.Weather
import com.google.gson.annotations.SerializedName

public class MyList (
        @SerializedName("dt")
        val dt: Int,
        @Embedded( prefix = "main_")
        val main: Main,

        @Embedded( prefix = "coord_")
        val coord: Coord,

        @SerializedName("weather")
        @TypeConverters(DataConverter::class)
        val weather: List<Weather>,

        @Embedded( prefix = "sys_")
        val sys: Sys,

        @SerializedName("dt_txt")
        val dt_txt: String
)
