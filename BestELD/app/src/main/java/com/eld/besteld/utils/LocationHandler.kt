package com.eld.besteld.utils

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.*
import kotlinx.android.synthetic.main.duty_inspection_layout.*
import java.util.*

internal object LocationHandler {
    private lateinit var mContext: Context
    private var PERMISSION_ID = 1000
    private var fusedLocationProviderClient: FusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(mContext)
    private lateinit var lastLocation: Location

    init {
        // currentDriver = DataHandler.currentDriver
        println("Singleton class invoked.")
    }

    fun locationLatitude(): Double {
        return lastLocation.latitude
    }

    fun locationLongitude(): Double {
        return  lastLocation.longitude
    }


    private fun checkPermission(): Boolean {
        return ActivityCompat.checkSelfPermission(
            mContext,
            android.Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED ||
                ActivityCompat.checkSelfPermission(
                    mContext as Activity,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED

    }

    //check location is enabled or not
    fun isLocationEnabled(): Boolean {
        val locationManager =
            mContext.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER
        )
    }

    fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == PERMISSION_ID) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            }
        }
    }

    fun cityName(): String {
        if (lastLocation != null) {
            var geoCoder = Geocoder(mContext, Locale.getDefault())
            var Adress = geoCoder.getFromLocation(lastLocation.latitude, lastLocation.longitude, 1)
            return Adress.get(0).getAddressLine(0)
        }
        return "Invalid Location"
    }

    private fun getCityName(lat: Double, long: Double): String {
        var geoCoder = Geocoder(mContext, Locale.getDefault())
        var Adress = geoCoder.getFromLocation(lat, long, 1)
        return Adress.get(0).getAddressLine(0)
    }

    fun getLastLocation() {
        if (checkPermission()) {
            if (isLocationEnabled()) {
                fusedLocationProviderClient.lastLocation.addOnCompleteListener { task ->
                    var location: Location? = task.result
                    if (location == null) {
                        NewLocationData()
                    } else {
//                        day = getCityName(location.latitude, location.longitude)
//                        etLocation.text = day
                    }
                }
            } else {
                Toast.makeText(mContext, "Please Turn on Your device Location", Toast.LENGTH_SHORT)
                    .show()
            }
        } else {
            RequestPermission()
        }
    }


    fun RequestPermission(): Boolean {
        //this function will allows us to tell the user to requesut the necessary permsiion if they are not garented
        ActivityCompat.requestPermissions(
            mContext as Activity,
            arrayOf(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
            ),
            PERMISSION_ID
        )
        return true
    }

    //fuction to allow user permission
    fun NewLocationData() {
        var locationRequest = LocationRequest()
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        locationRequest.interval = 0
        locationRequest.fastestInterval = 0
        locationRequest.numUpdates = 1
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(mContext)
        if (ActivityCompat.checkSelfPermission(
                mContext,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                mContext,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        fusedLocationProviderClient.requestLocationUpdates(
            locationRequest, locationCallback, Looper.myLooper()
        )
    }

    private val locationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            lastLocation = locationResult.lastLocation
            /*
            Log.d("Debug:", "your last last location: " + lastLocation.longitude.toString())
            startLatitude = lastLocation.latitude
            startLongitude = lastLocation.longitude
            day = "" + getCityName(lastLocation.latitude, lastLocation.longitude)
            etLocation.text = "" + getCityName(lastLocation.latitude, lastLocation.longitude) */
        }
    }
}