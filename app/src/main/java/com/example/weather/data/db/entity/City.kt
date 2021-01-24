package com.example.weather.data.db.entity


import androidx.room.Embedded
import com.example.weather.data.db.entity.future.Coord
import com.google.gson.annotations.SerializedName

data class City(
    @Embedded( prefix = "coord_")
    val coord: Coord,
    @SerializedName("country")
    val country: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("sunrise")
    val sunrise: Int,
    @SerializedName("sunset")
    val sunset: Int,
    @SerializedName("timezone")
    val timezone: Int
)