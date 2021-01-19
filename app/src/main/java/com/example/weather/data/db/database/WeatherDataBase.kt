package com.example.weather.data.db.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.weather.data.db.dao.CurrentWeatherDao
import com.example.weather.data.db.entity.CurrentWeatherResponse
import com.example.weather.data.db.entity.DataConverter


@Database( entities = [CurrentWeatherResponse ::class] , version = 1)
@TypeConverters(DataConverter::class)
abstract class WeatherDataBase : RoomDatabase () {

    abstract fun getCurrentWeatherDao() : CurrentWeatherDao

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