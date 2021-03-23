package com.eld.besteld.dialogs

import android.Manifest
import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.aagito.imageradiobutton.RadioImageGroup
import com.eld.besteld.R
import com.eld.besteld.networkHandling.request.DayDatum
import com.eld.besteld.networkHandling.request.DutyDataRequest
import com.eld.besteld.networkHandling.responce.LoginResponce
import com.eld.besteld.roomDataBase.*
import com.eld.besteld.utils.CommonUtils
import com.eld.besteld.utils.DataHandler
import com.eld.besteld.utils.DutyStatus
import com.eld.besteld.utils.TimeUtility
import com.ethane.choosetobefit.web_services.RetrofitExecuter.getApiInterface
import com.google.android.gms.location.*
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.duty_inspection_layout.*
import org.json.JSONObject
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class DutyHourDialog : DialogFragment() {

    private lateinit var mContext: Context
    private var day = ""
    private var dutyStatus: DutyStatus = DutyStatus.OFFDUTY
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private var PERMISSION_ID = 1000
    private val offDuty = 2131231066
    private val onDuty = 2131231067
    private val sleeper = 2131231068
    private lateinit var dayData: DayData
    private lateinit var dayDataGraph: List<DayDatum>
    private lateinit var inspection: List<Inspection>
    private lateinit var dutyDataRequest: DutyDataRequest
    private var dayDataList: insertDriverInformationDao? = null
    private lateinit var viewModel: DriverViewModel
    private lateinit var logDataViewModel: LogDataViewModel


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.duty_inspection_layout, container, false)
    }

    override fun onStart() {
        super.onStart()
        setListener()
        settingCallBack(mContext)
        viewModel = ViewModelProvider(this).get(DriverViewModel::class.java)
        logDataViewModel = ViewModelProvider(this).get(LogDataViewModel::class.java)
        dayDataList = EldDataBaseExicution.invoke(mContext).getDriverDao()
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(mContext)
    }

    fun settingCallBack(context: Context) {
        this.mContext = context
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = Dialog(Objects.requireNonNull(mContext))
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window?.attributes?.windowAnimations = R.style.dialogAnimation
        dialog.setCanceledOnTouchOutside(true)
        dialog.window?.let {
            val layoutParams: WindowManager.LayoutParams = it.attributes
            it.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)
            layoutParams.dimAmount = 0.0f
            layoutParams.gravity = Gravity.BOTTOM
            // it.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            it.attributes = layoutParams
        }
        return dialog
    }

    private fun setListener() {
        btnConfirm.setOnClickListener(this::onClick)
        btnCancel.setOnClickListener(this::onClick)
        etLocation.setOnClickListener(this::onClick)
        rbOffDuty.setOnClickListener(this::onClick)
        rbOnDuty.setOnClickListener(this::onClick)
        rbSleeper.setOnClickListener(this::onClick)
        radioGroupDriverStatus.setOnCheckedChangeListener(object :
            RadioImageGroup.OnCheckedChangeListener {
            override fun onCheckedChanged(
                radioGroup: View,
                radioButton: View?,
                isChecked: Boolean,
                checkedId: Int
            ) {

                // Toast.makeText(mContext,""+checkedId,Toast.LENGTH_LONG).show()
                if (onDuty == checkedId) {
                    dutyStatus = DutyStatus.ONDUTY
                } else if (offDuty == checkedId) {
                    dutyStatus = DutyStatus.OFFDUTY
                } else if (sleeper == checkedId) {
                    dutyStatus = DutyStatus.SLEEPER
                }
            }

        })
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun onClick(view: View) {
        when (view) {
            btnCancel -> dismiss()
            //  btnConfirm -> listener.onButtonConfirm(rbEnableYardMode.id,et)
            btnConfirm -> {
                DataHandler.logDataViewModel = logDataViewModel
                DataHandler.dutyStatusChanged(dutyStatus, etNotes.text.toString(), TimeUtility.currentDateUTC())
                dismiss()
            }
            etLocation -> {
            }
            rbOffDuty -> {
            }

            rbOnDuty -> {
            }

            rbSleeper -> {
            }
        }
    }

    private fun callLogBookApi() {

        inspection = listOf(Inspection(12323.4, "Delhi", 23434.34, "ertert", "ertert"))
        if (CommonUtils.isOnline(mContext)) {
            dutyDataRequest =
                DutyDataRequest(1612882460, 1330194600001, 1330194600000, dayDataGraph, inspection)
            var call =
                getApiInterface(
                    "",
                    "eyJraWQiOiJLOSswR3hadytGYjVMY1VzWVwvUEFraVg0VGNkZmp5UFRiRTFodEpzUG5Lbz0iLCJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJhNTU3OTkyZi03YjAxLTQzY2ItYjg3NC04NjI2M2I4ODA0MjYiLCJlbWFpbF92ZXJpZmllZCI6dHJ1ZSwiaXNzIjoiaHR0cHM6XC9cL2NvZ25pdG8taWRwLnVzLXdlc3QtMS5hbWF6b25hd3MuY29tXC91cy13ZXN0LTFfUU1wVHRscHJsIiwiY3VzdG9tOmlkIjoiMjQyMDJmNTAtNzIxOS0xMWViLTg3N2MtNWQ4NWIwMmQyMzkyIiwiY29nbml0bzp1c2VybmFtZSI6ImE1NTc5OTJmLTdiMDEtNDNjYi1iODc0LTg2MjYzYjg4MDQyNiIsImF1ZCI6IjRuM2g2MXU4N2kzdG9sNHE2YTNzb2toYnA0IiwiZXZlbnRfaWQiOiJmODJlNTlmYi1iYjQ2LTQ4ZDktYWM2My0xYzkyZGExODlhNDciLCJ0b2tlbl91c2UiOiJpZCIsImF1dGhfdGltZSI6MTYxNTAwNzk1MywiY3VzdG9tOnN0YXR1cyI6ImFjdGl2ZSIsImV4cCI6MTYxNTAxMTU1MywiY3VzdG9tOnJvbGUiOiJkcml2ZXIiLCJpYXQiOjE2MTUwMDc5NTMsImVtYWlsIjoicGFua2Fqc3VuYWw2NkBnbWFpbC5jb20ifQ.BNJRMksx1UWOo3mAj4niIcn9opBHwMNFr23YFrT1hegFJ40McJ9IPMpYRQZwsOn5Pq0qt853bbSpD9I6tM3gXrUI2MU9juSnT0GZSpCuuF-LtSDt7hzLjWZwqCm0ppJnQ0IK7eCeJ9u6P3StrEj2F_nIJTVcc8aER1XFOIUrypX5XAqCGGCXeiC9v6nuyud37ioNkWotqX6PCG5SIGfGqa3OD_xwI91LpYLD4j0vI5ZXLMI2TLM7f9klkLD1kTfYc0BGkI8veg4cy7PVvUNL4pkFxbmbqAXfpkhXRnITqri1QCFUpwbO0UHCEzgAP5tI0YVXBNEwz_fQAWZMZJkudA",
                    "sdf",
                    false
                ).createLogbook(dutyDataReq = dutyDataRequest)
            Log.e("url", "" + call.request().url)
            call.enqueue(object : retrofit2.Callback<LoginResponce?> {
                override fun onResponse(
                    call: retrofit2.Call<LoginResponce?>,
                    response: retrofit2.Response<LoginResponce?>
                ) {
                    if (response.body() != null) {
                        val loginResponce = response.body()
                        //  fillingDriverProfile(profile)
                        //saving driver information to the database from server
                    } else {
                        val gson = GsonBuilder().create()
                        try {
                            val jObjError = JSONObject(response.errorBody()!!.string())
                            Toast.makeText(
                                context,
                                jObjError.getString("message").toString(),
                                Toast.LENGTH_LONG
                            ).show()
                        } catch (e: IOException) {
                        }
                    }
                }

                override fun onFailure(
                    call: retrofit2.Call<LoginResponce?>,
                    t: Throwable
                ) {
                    call.cancel()
                }
            })
        } else {
            Toast.makeText(context, "Please check internet connection", Toast.LENGTH_LONG)
        }

    }
}

/*
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

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == PERMISSION_ID) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            }
        }
    }

    private fun getCityName(lat: Double, long: Double): String {
        var geoCoder = Geocoder(mContext, Locale.getDefault())
        var Adress = geoCoder.getFromLocation(lat, long, 1)
        return Adress.get(0).getAddressLine(0)
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
            var lastLocation: Location = locationResult.lastLocation
            Log.d("Debug:", "your last last location: " + lastLocation.longitude.toString())
            startLatitude = lastLocation.latitude
            startLongitude = lastLocation.longitude
            day = "" + getCityName(lastLocation.latitude, lastLocation.longitude)
            etLocation.text = "" + getCityName(lastLocation.latitude, lastLocation.longitude)
        }
    }
}*/








