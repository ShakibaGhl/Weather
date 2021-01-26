package com.example.weather.data.db.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.weather.data.db.dao.CurrentWeatherDao
import com.example.weather.data.db.entity.CurrentWeatherResponse
import com.example.weather.data.db.entity.WeatherListConverter


@Database( entities = [CurrentWeatherResponse ::class] , version = 1)
@TypeConverters(WeatherListConverter::class)
abstract class WeatherDataBase : RoomDatabase () {

    abstract fun getCurrentWeatherDao() : CurrentWeatherDao
 //   abstract fun getFutureWeatherDao() : FutureWeatherDao

    companion object {

        var INSTANCE : WeatherDataBase? = null

        fun getWeatherDataBaseInstance(context: Context) : WeatherDataBase? {

            if(INSTANCE == null){

                synchronized(WeatherDataBase::class){
                    INSTANCE = Room.databaseBuilder(context.applicationContext, WeatherDataBase::class.java, "weatherDB").allowMainThreadQueries().build()
                }
            }
            return INSTANCE
        }
    }



}