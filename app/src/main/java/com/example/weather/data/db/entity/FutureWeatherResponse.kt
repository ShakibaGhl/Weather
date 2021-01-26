package com.example.weather.data.db.entity


import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
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
        @TypeConverters(MyListConverter::class)
        val list: List<MyList>,
        @SerializedName("message")
        val message: Double
)
