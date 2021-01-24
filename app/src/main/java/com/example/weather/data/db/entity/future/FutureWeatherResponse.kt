package com.example.weather.data.db.entity.future


import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.weather.data.db.entity.DataConverter
import com.google.gson.annotations.SerializedName

@Entity
data class FutureWeatherResponse(
    @PrimaryKey(autoGenerate = true)
    val id : Int? = null,
    @Embedded( prefix = "city_")
    val city: City,
    @SerializedName("cnt")
    val cnt: Int,
    @SerializedName("cod")
    val cod: String,
    @SerializedName("list")
    val list: List<Any>,
    @SerializedName("message")
    val message: Double
)