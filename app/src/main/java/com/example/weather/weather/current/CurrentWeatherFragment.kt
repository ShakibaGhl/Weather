package com.example.weather.weather.current

import android.content.Context
import android.content.pm.PackageManager
import android.icu.util.Calendar
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import com.example.weather.R
import com.example.weather.data.ApiCurrentweather
import com.example.weather.data.db.database.WeatherDataBase
import com.example.weather.data.db.entity.*
import com.example.weather.data.db.network.retrofitWeatherInstance
import com.example.weather.setWeatherIcon
import kotlinx.android.synthetic.main.current_weather_fragment.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.time.ZonedDateTime
import java.util.*
import java.util.jar.Manifest
const val  MY_PERMISSIONS_REQUEST_LOCATION = 12345
class CurrentWeatherFragment : Fragment() {


    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.current_weather_fragment, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val dataBase = WeatherDataBase.getWeatherDataBaseInstance(requireContext())
        val lastUpdate = dataBase?.getCurrentWeatherDao()?.getAllData()
        lastUpdate?.let { updateUi(it) }



        if(checkLocationPermission()) getCurrentLocation()

        button_get_permission.setOnClickListener {

            checkLocationPermission()
        }
    }





    private fun getCurrentLocation(){

        val locationManager = activity?.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        if (ActivityCompat.checkSelfPermission(
                        requireContext(),
                        android.Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                        requireContext(),
                        android.Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,2000 , 1000f , object : LocationListener {
            override fun onLocationChanged(p0: Location) {

            //    Toast.makeText(requireContext() , "${p0?.latitude} ${p0?.longitude}" , Toast.LENGTH_SHORT).show()
                p0?.let {
                    getWeather(it)
                }

            }

            override fun onStatusChanged(p0: String?, p1: Int, p2: Bundle?) {


            }

            override fun onProviderEnabled(p0: String) {

                Toast.makeText(requireContext() , "onProviderEnabled" , Toast.LENGTH_SHORT).show()
            }

            override fun onProviderDisabled(p0: String) {

                Toast.makeText(requireContext() , "onProviderDisabled" , Toast.LENGTH_SHORT).show()
            }

        })
    }



    fun checkLocationPermission(): Boolean {
        return if (ContextCompat.checkSelfPermission(
                        requireContext(),
                        android.Manifest.permission.ACCESS_FINE_LOCATION
                )
                != PackageManager.PERMISSION_GRANTED
        ) {
            //permission not granted

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                            requireActivity(),
                            android.Manifest.permission.ACCESS_FINE_LOCATION
                    )
            ) {
                requestPermissions(
                        arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                        MY_PERMISSIONS_REQUEST_LOCATION
                )
            } else {
                // No explanation needed, we can request the permission.
                requestPermissions(
                        arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                        MY_PERMISSIONS_REQUEST_LOCATION
                )
            }
            false
        } else {
            //permission granted
            view_location_layout.visibility = View.VISIBLE
            true
        }
    }


    override fun onRequestPermissionsResult(
            requestCode: Int,
            permissions: Array<out String>,
            grantResults: IntArray
    ) {
        when (requestCode) {
            MY_PERMISSIONS_REQUEST_LOCATION -> {

                // If request is cancelled, the result arrays are empty.
                if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // location-related task you need to do.
                    if (ContextCompat.checkSelfPermission(
                                    requireContext(),
                                    android.Manifest.permission.ACCESS_FINE_LOCATION
                            )
                            == PackageManager.PERMISSION_GRANTED
                    ) {

                        //Request location updates:
//                        locationManager.requestLocationUpdates(provider, 400, 1, this)
                        Toast.makeText(requireContext() , "granted" , Toast.LENGTH_SHORT).show()
                        view_location_layout.visibility = View.VISIBLE
                        view_permission_layout.visibility = View.GONE
                        getCurrentLocation()
                    }
                } else {
                    Toast.makeText(requireContext() , "denied" , Toast.LENGTH_SHORT).show()
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    view_location_layout.visibility = View.GONE
                    view_permission_layout.visibility = View.VISIBLE


                }
                return
            }
        }
    }








    private fun getWeather(location : Location) {
        progressBar.visibility = View.VISIBLE

        val retrofit = retrofitWeatherInstance.getRetrofitInstance()
        val currentWeatherService = retrofit.create(ApiCurrentweather::class.java)

        currentWeatherService.getCurrentWeather(location.latitude , location.longitude, "4a7539e81417846fb7a62e1520d84a87", "metric").enqueue(object : Callback<CurrentWeatherResponse> {

            @RequiresApi(Build.VERSION_CODES.N)
            override fun onFailure(call: Call<CurrentWeatherResponse>, t: Throwable) {

                val dataBase = WeatherDataBase.getWeatherDataBaseInstance(requireContext())
                val lastUpdate = dataBase?.getCurrentWeatherDao()?.getAllData()
                lastUpdate?.let { updateUi(it) }
                progressBar.visibility = View.GONE
                Toast.makeText(requireContext(), "failure", Toast.LENGTH_SHORT).show()
            }

            @RequiresApi(Build.VERSION_CODES.N)
            override fun onResponse(call: Call<CurrentWeatherResponse>, response: Response<CurrentWeatherResponse>) {
                progressBar.visibility = View.GONE

                response.body()?.let {
                    updateUi(it)
                    fetchData(it)
                }
            }
        })

    }


    @RequiresApi(Build.VERSION_CODES.N)
    private fun updateUi(currentWeather: CurrentWeatherResponse) {


        text_view_city.text = currentWeather.name


        val calender : Calendar = Calendar.getInstance()
        val sf : SimpleDateFormat = SimpleDateFormat("HH:mm:ss")
        val time : String = sf.format((calender.time))
        text_view_time.text = time


        val sdf : SimpleDateFormat = SimpleDateFormat("MM/DD/yyyy")
        val date : String = sdf.format((calender.time))
        text_view_date.text = date

       // text_view_date.text = currentWeather.timezone.toString()

        currentWeather.main.let {
            view_humidity.text = resources.getString(R.string.humidity).format(it.humidity.toString())
            view_pressure.text = resources.getString(R.string.pressure).format(it.pressure.toString())
            text_view_temp.text = resources.getString(R.string.temp).format(it.temp.toString())
        }

        currentWeather.sys.let {
            // var calender : Calendar = Calendar.getInstance()
            //var sf : SimpleDateFormat = SimpleDateFormat("hh:mm:ss")
            //var riseTime : String = sf.format((currentWeather.sys.sunrise))
            //view_sunrise.text = riseTime




            var sdf : SimpleDateFormat = SimpleDateFormat("HH:mm:ss" )
            sdf.timeZone = TimeZone.getTimeZone(currentWeather.id.toString())
            var setTime : String = sdf.format(((currentWeather.sys.sunset)*1000))
            var riseTime : String = sdf.format(((currentWeather.sys.sunrise)*1000))
            view_sunset.text = setTime
            view_sunrise.text = riseTime



            //val sunrise = Date(((currentWeather.sys.sunrise)*1000).toLong())
            //val sun = sdf.format(sunrise)
            //view_sunrise.text = sunrise.toString()

           // val sunset = Date(((currentWeather.sys.sunset + currentWeather.timezone)/currentWeather.timezone).toLong())
           // view_sunset.text = sunset.toString()

//            val dt =SimpleDateFormat("HH:mm:ss")
//            val riseTime = Date(currentWeather.sys.sunrise.toLong())
//            val setTime = Date(currentWeather.sys.sunset.toLong())
//            val rise = dt.format(riseTime)
//            val set = dt.format(setTime)
//            view_sunrise.text = rise
//            view_sunset.text = set

           // view_sunrise.text = currentWeather.sys.sunrise.toString()
           // view_sunset.text = currentWeather.sys.sunset.toString()
        }

        currentWeather.weather.let {
            text_view_weather_condition.text = currentWeather.weather[0].description
           // var url = currentWeather.weather[0].icon
           // Glide.with(requireContext())
           //         .load("http://openweathermap.org/img/wn/$url@2x.png")
           //         .into(image_view_weather_condition)
            image_view_weather_condition.setWeatherIcon(currentWeather.weather[0].id)
        }

        currentWeather.coord.let {
            view_latitude.text = resources.getString(R.string.lat).format(it.lat.toString())
            view_longitude.text = resources.getString(R.string.lon).format(it.lon.toString())
        }

        currentWeather.wind.let {
            view_windSpeed.text = resources.getString(R.string.speed).format(it.speed.toString())
        }

    }





    private fun fetchData(currentWeather: CurrentWeatherResponse){

        val dataBase = WeatherDataBase.getWeatherDataBaseInstance(requireContext())
        val dataDao = dataBase?.getCurrentWeatherDao()

            val allData = dataDao?.getAllData()
            if(allData == null) {
                dataDao?.insertCurrentWeather(currentWeather)
                Toast.makeText(context , "Data Inserted" , Toast.LENGTH_SHORT).show()
            }else{

                dataDao.updateCurrentWeather(currentWeather)

                Toast.makeText(context , "Data Updated" , Toast.LENGTH_SHORT).show()

            }

    }


}