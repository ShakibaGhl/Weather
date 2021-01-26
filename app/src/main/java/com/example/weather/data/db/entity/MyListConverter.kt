package com.example.weather.data.db.entity

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

public class MyListConverter {

    @TypeConverter
    fun fromMyList(myList: List<MyList?>?): String? {
        if (myList == null) {
            return null
        }
        val gson = Gson()
        val type: Type = object : TypeToken<List<MyList?>?>() {}.type
        return gson.toJson(myList, type)
    }

    @TypeConverter
    fun toMyList(myListString: String?): List<MyList>? {
        if (myListString == null) {
            return null
        }
        val gson = Gson()
        val type: Type = object : TypeToken<List<MyList?>?>() {}.type
        return gson.fromJson<List<MyList>>(myListString, type)
    }
}