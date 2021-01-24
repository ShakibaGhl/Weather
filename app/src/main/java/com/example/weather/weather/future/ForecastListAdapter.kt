package com.example.weather.weather.future

import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.weather.R
import com.example.weather.data.db.entity.FutureWeatherResponse
import com.example.weather.data.db.entity.MyList
import kotlinx.android.synthetic.main.item_forecast.view.*
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import com.example.weather.data.db.entity.Main as Main1

internal class ForecastListAdapter(private val forecasts : MutableList<MyList>) : RecyclerView.Adapter<ForecastListAdapter.ForecastListViewHolder>() {

    internal inner class ForecastListViewHolder(view : View) : RecyclerView.ViewHolder(view){
        var textViewTemp = view.item_text_view_temp
        var textViewDate = view.item_text_view_date
        var textViewWeatherCondition = view.item_text_view_weather_condition
        var imageViewCondition = view.item_image_view_weather_condition


    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ForecastListViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_forecast , parent , false)
        return ForecastListViewHolder(itemView)
    }

    override fun getItemCount(): Int = forecasts.size


    override fun onBindViewHolder(holder: ForecastListViewHolder, position: Int) {

//        holder.itemView.setOnClickListener{
//
//            clicked(forecasts[position])
//        }

        holder.textViewTemp.text = forecasts[position].main.temp.toString()

       // val dtFormatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM)
        holder.textViewDate.text = forecasts[position].dt_txt

        holder.textViewWeatherCondition.text = forecasts[position].weather[position].description

    }
}