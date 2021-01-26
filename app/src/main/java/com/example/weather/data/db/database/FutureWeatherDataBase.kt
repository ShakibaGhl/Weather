package com.example.weather.data.db.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.weather.data.db.dao.FutureWeatherDao
import com.example.weather.data.db.entity.WeatherListConverter
import com.example.weather.data.db.entity.FutureWeatherResponse
import com.example.weather.data.db.entity.MyListConverter

@Database(entities = [FutureWeatherResponse ::class ] , version = 1)
@TypeConverters(WeatherListConverter::class , MyListConverter ::class)

abstract class FutureWeatherDataBase  :  RoomDatabase() {

    abstract fun getFutureWeatherDao() : FutureWeatherDao

    companion object {

        var INSTANCE : FutureWeatherDataBase? = null

        fun getFutureWeatherDataBaseInstance(context: Context) : FutureWeatherDataBase? {

            if(INSTANCE == null){

                synchronized(FutureWeatherDataBase::class){
                    INSTANCE = Room.databaseBuilder(context.applicationContext, FutureWeatherDataBase::class.java, "futureWeatherDB").allowMainThreadQueries().build()
                }
            }
            return INSTANCE
        }
    }


}