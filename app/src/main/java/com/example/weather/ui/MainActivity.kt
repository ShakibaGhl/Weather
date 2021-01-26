package com.example.weather.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.setupWithNavController
import com.example.weather.R
import com.example.weather.weather.current.CurrentWeatherFragment
import com.example.weather.weather.future.ForecastListFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {

   // private lateinit var navController: NavController

    var bn: BottomNavigationView? = null
    val currentFragment: Fragment = CurrentWeatherFragment()
    val forecastFragment: Fragment = ForecastListFragment()
    val fm: FragmentManager = supportFragmentManager
    var active: Fragment = currentFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        bn = findViewById<View>(R.id.bottom_nav) as BottomNavigationView
        fm.beginTransaction().add(R.id.nav_host, forecastFragment, "2").hide(forecastFragment).commit()
        fm.beginTransaction().add(R.id.nav_host, currentFragment, "1").commit()
        bn!!.setOnNavigationItemSelectedListener(this)


       // navController = Navigation.findNavController(this , R.id.nav_host)

       // bottom_nav.setupWithNavController(navController)



    }
    override fun onNavigationItemSelected(menuItem: MenuItem): Boolean {
        when (menuItem.getItemId()) {
            R.id.currentWeatherFragment -> {
                fm.beginTransaction().hide(active).show(currentFragment).commit()
                active = currentFragment
                return true
            }
            R.id.forecastListFragment -> {
                fm.beginTransaction().hide(active).show(forecastFragment).commit()
                active = forecastFragment
                return true
            }

        }
        return false
    }



}