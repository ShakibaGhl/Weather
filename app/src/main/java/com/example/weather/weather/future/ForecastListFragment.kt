package com.example.weather.weather.future

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weather.R
import com.example.weather.data.ApiFutureWeather
import com.example.weather.data.db.entity.FutureWeatherResponse
import com.example.weather.data.db.entity.MyList
import com.example.weather.data.db.network.retrofitFutureWeatherInstance
import kotlinx.android.synthetic.main.forecast_list_fragment.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ForecastListFragment : Fragment() {

    private lateinit var navController: NavController
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
        navController = Navigation.findNavController(view)
        getWeather()

    }

    private fun getWeather() {
        //progressBar.visibility = View.VISIBLE

        val retrofit = retrofitFutureWeatherInstance.getFutureRetrofitInstance()
        val futureWeatherService = retrofit.create(ApiFutureWeather::class.java)

        futureWeatherService.getFutureWeather("tehran", "4a7539e81417846fb7a62e1520d84a87", "metric")
                .enqueue(object : Callback<FutureWeatherResponse> {

                    override fun onFailure(call: Call<FutureWeatherResponse>, t: Throwable) {
                        Toast.makeText(requireContext(), "failure", Toast.LENGTH_SHORT).show()
                    }

                    override fun onResponse(call: Call<FutureWeatherResponse>, response: Response<FutureWeatherResponse>) {
                       // forecast_show.text = response.body()?.toString()
                       // response.body()?.let { registerRecyclerView(listOf(it)) }
                        response.body()?.list?.let { registerRecyclerView(it) }
                        Toast.makeText(requireContext(), "  ", Toast.LENGTH_SHORT).show()
                    }

                })
    }



    private fun registerRecyclerView(items: List<MyList>) {

        recyclerview_forecastList.layoutManager = LinearLayoutManager(requireContext() , LinearLayoutManager.VERTICAL, false)
        recyclerview_forecastList.adapter = ForecastListAdapter(items as MutableList<MyList>)

    }




}