package com.example.weather

import android.widget.ImageView
import kotlinx.android.synthetic.main.current_weather_fragment.view.*


fun ImageView.setWeatherIcon(conditionCode : Int){

    this.setImageResource(
            when(conditionCode){
                200 -> R.drawable.ic_storm
                201 -> R.drawable.ic_storm
                202 -> R.drawable.ic_storm
                210 -> R.drawable.ic_storm
                211 -> R.drawable.ic_storm
                212 -> R.drawable.ic_storm
                221 -> R.drawable.ic_storm
                230 -> R.drawable.ic_storm
                231 -> R.drawable.ic_storm
                232 -> R.drawable.ic_storm

                300 -> R.drawable.ic_rain
                301 -> R.drawable.ic_rain
                302 -> R.drawable.ic_rain
                310 -> R.drawable.ic_rain
                311 -> R.drawable.ic_rain
                312 -> R.drawable.ic_rain
                313 -> R.drawable.ic_rain
                314 -> R.drawable.ic_rain
                321 -> R.drawable.ic_rain

                500 -> R.drawable.ic_rain
                501 -> R.drawable.ic_rain
                502 -> R.drawable.ic_rain
                503 -> R.drawable.ic_rain
                504 -> R.drawable.ic_rain
                511 -> R.drawable.ic_rain
                520 -> R.drawable.ic_rain
                521 -> R.drawable.ic_rain
                522 -> R.drawable.ic_rain
                531 -> R.drawable.ic_rain

                600 -> R.drawable.ic_snow1
                601 -> R.drawable.ic_snow1
                602 -> R.drawable.ic_snow1
                611 -> R.drawable.ic_sleet
                612 -> R.drawable.ic_sleet
                613 -> R.drawable.ic_sleet
                615 -> R.drawable.ic_sleet
                616 -> R.drawable.ic_sleet
                620 -> R.drawable.ic_snow1
                621 -> R.drawable.ic_snow1
                622 -> R.drawable.ic_snow1

                701 -> R.drawable.ic_fog
                711 -> R.drawable.ic_fog
                721 -> R.drawable.ic_fog
                731 -> R.drawable.ic_fog
                741 -> R.drawable.ic_fog
                751 -> R.drawable.ic_fog
                761 -> R.drawable.ic_fog
                762 -> R.drawable.ic_fog
                771 -> R.drawable.ic_fog
                781 -> R.drawable.ic_fog

                801 -> R.drawable.ic_cloud
                802 -> R.drawable.ic_cloud
                803 -> R.drawable.ic_cloud
                804 -> R.drawable.ic_cloud
                else -> 0
            }
    )
}