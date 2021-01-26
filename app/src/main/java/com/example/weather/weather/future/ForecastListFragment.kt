package com.example.weather.weather.future

import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weather.R
import com.example.weather.data.ApiFutureWeather
import com.example.weather.data.db.database.FutureWeatherDataBase
import com.example.weather.data.db.database.WeatherDataBase
import com.example.weather.data.db.entity.CurrentWeatherResponse
import com.example.weather.data.db.entity.FutureWeatherResponse
import com.example.weather.data.db.entity.MyList
import com.example.weather.data.db.network.retrofitFutureWeatherInstance
import kotlinx.android.synthetic.main.current_weather_fragment.*
import kotlinx.android.synthetic.main.forecast_list_fragment.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

const val  MY_PERMISSIONS_REQUEST_LOCATION = 12345
class ForecastListFragment : Fragment() {

   // private lateinit var navController: NavController
    //private val args : ForecastListFragment by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.forecast_list_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val dataBase = FutureWeatherDataBase.getFutureWeatherDataBaseInstance(requireContext())
        val lastUpdate = dataBase?.getFutureWeatherDao()?.getAllData()
        lastUpdate?.list?.let { registerRecyclerView(it) }


       // navController = Navigation.findNavController(view)
       // if(checkLocationPermission())
            getCurrentLocation()

//        list_button_get_permission.setOnClickListener {
//
//            if(checkLocationPermission()) getCurrentLocation()
//        }

    }



    override fun onResume() {
        super.onResume()
        //if(checkLocationPermission())
            getCurrentLocation()
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
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,2000 , 1000f , object :
            LocationListener {
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






//    fun checkLocationPermission(): Boolean {
//        return if (ContextCompat.checkSelfPermission(
//                requireContext(),
//                android.Manifest.permission.ACCESS_FINE_LOCATION
//            )
//            != PackageManager.PERMISSION_GRANTED
//        ) {
//            //permission not granted
//
//            // Should we show an explanation?
//            if (ActivityCompat.shouldShowRequestPermissionRationale(
//                    requireActivity(),
//                    android.Manifest.permission.ACCESS_FINE_LOCATION
//                )
//            ) {
//                requestPermissions(
//                    arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
//                    com.example.weather.weather.current.MY_PERMISSIONS_REQUEST_LOCATION
//                )
//            } else {
//                // No explanation needed, we can request the permission.
//                requestPermissions(
//                    arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
//                    com.example.weather.weather.current.MY_PERMISSIONS_REQUEST_LOCATION
//                )
//            }
//            false
//        } else {
//            //permission granted
//           // view_location_layout.visibility = View.VISIBLE
//            true
//        }
//    }





//    override fun onRequestPermissionsResult(
//        requestCode: Int,
//        permissions: Array<out String>,
//        grantResults: IntArray
//    ) {
//        when (requestCode) {
//            com.example.weather.weather.current.MY_PERMISSIONS_REQUEST_LOCATION -> {
//
//                // If request is cancelled, the result arrays are empty.
//                if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//
//                    // permission was granted, yay! Do the
//                    // location-related task you need to do.
//                    if (ContextCompat.checkSelfPermission(
//                            requireContext(),
//                            android.Manifest.permission.ACCESS_FINE_LOCATION
//                        )
//                        == PackageManager.PERMISSION_GRANTED
//                    ) {
//
//                        //Request location updates:
////                        locationManager.requestLocationUpdates(provider, 400, 1, this)
//                        Toast.makeText(requireContext() , "Granted" , Toast.LENGTH_SHORT).show()
//                       // view_location_layout.visibility = View.VISIBLE
//                        list_view_permission_layout.visibility = View.GONE
//                        getCurrentLocation()
//                    }
//                } else {
//                    Toast.makeText(requireContext() , "Denied" , Toast.LENGTH_SHORT).show()
//                    // permission denied, boo! Disable the
//                    // functionality that depends on this permission.
//                   // view_location_layout.visibility = View.GONE
//                    list_view_permission_layout.visibility = View.VISIBLE
//
//
//                }
//                return
//            }
//        }
//    }






    private fun getWeather(location : Location) {
        list_progressBar.visibility = View.VISIBLE
       // list_view_permission_layout.visibility = View.GONE

        val retrofit = retrofitFutureWeatherInstance.getFutureRetrofitInstance()
        val futureWeatherService = retrofit.create(ApiFutureWeather::class.java)

        futureWeatherService.getFutureWeather(location.latitude , location.longitude, "4a7539e81417846fb7a62e1520d84a87", "metric")
                .enqueue(object : Callback<FutureWeatherResponse> {

                    override fun onFailure(call: Call<FutureWeatherResponse>, t: Throwable) {
                        list_progressBar.visibility = View.GONE
                        Toast.makeText(requireContext(), "Failure", Toast.LENGTH_SHORT).show()
                    }

                    override fun onResponse(call: Call<FutureWeatherResponse>, response: Response<FutureWeatherResponse>) {
                       // forecast_show.text = response.body()?.toString()
                       // response.body()?.let { registerRecyclerView(listOf(it)) }
                        list_progressBar.visibility = View.GONE
                        response.body()?.list?.let {
                            registerRecyclerView(it)
                            fetchData(response.body()!!)
                        }
                       // Toast.makeText(requireContext(), "  ", Toast.LENGTH_SHORT).show()
                    }

                })
    }



    private fun registerRecyclerView(items: List<MyList>) {

        recyclerview_forecastList.layoutManager = LinearLayoutManager(requireContext() , LinearLayoutManager.VERTICAL, false)
        recyclerview_forecastList.adapter = ForecastListAdapter(items as MutableList<MyList>)

    }



    private fun fetchData(futureWeather: FutureWeatherResponse){

        val dataBase = FutureWeatherDataBase.getFutureWeatherDataBaseInstance(requireContext())
        val dataDao = dataBase?.getFutureWeatherDao()

        val allData = dataDao?.getAllData()
        if(allData == null) {
            dataDao?.insertFutureWeather(futureWeather)
            Toast.makeText(context , " !! Data Inserted !!" , Toast.LENGTH_SHORT).show()
        }else{

            dataDao.updateFutureWeather(futureWeather)

            Toast.makeText(context , "!! Data Updated !!" , Toast.LENGTH_SHORT).show()

        }

    }




}