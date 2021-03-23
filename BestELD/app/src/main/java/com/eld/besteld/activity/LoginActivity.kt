package com.eld.besteld.activity


import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.eld.besteld.R
import com.eld.besteld.dialogs.CommonDialogs
import com.eld.besteld.listener.DialogCallback
import com.eld.besteld.networkHandling.request.LoginRequest
import com.eld.besteld.networkHandling.request.VinRequest
import com.eld.besteld.networkHandling.responce.VinResponce
import com.eld.besteld.networkHandling.responce.LoginResponce
import com.eld.besteld.roomDataBase.DriverInformation
import com.eld.besteld.roomDataBase.DriverViewModel
import com.eld.besteld.roomDataBase.EldDataBaseExicution
import com.eld.besteld.roomDataBase.insertDriverInformationDao
import com.eld.besteld.utils.CommonUtils
import com.eld.besteld.utils.DataHandler
import com.eld.besteld.utils.TimeUtility
import com.ethane.choosetobefit.web_services.RetrofitExecuter
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException
import java.sql.Timestamp
import java.time.*
import java.util.*

class LoginActivity : AppCompatActivity(R.layout.activity_login), View.OnClickListener, TextWatcher,

    DialogCallback {
    private lateinit var context: Context
    private lateinit var mUserInfo: LoginRequest
    private lateinit var profile: DriverInformation
    lateinit var viewModel: DriverViewModel

    private var driverInformation: insertDriverInformationDao? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        context = this
        init()
        viewModel = ViewModelProvider(this).get(DriverViewModel::class.java)
        if (CommonUtils.DEBUB_MODE == true) {
            etEmail.setText("pankajsunal66@gmail.com".toString())
            etPassword.setText("Pankaj@123".toString())
        }
        viewModel.dayDaya.observe(this, Observer {
        })

        var curretTime = TimeUtility.currentDateUTC()
        var abc = TimeUtility.getTimeIntervalForStartOfTheDay(curretTime)
        print("tet")
        // mDriverInformation = DriverInformation("city","country","dlBackPic","dlExpiryDate","dlFrontPic","dlNumber")
    }

    private fun init() {
        driverInformation = EldDataBaseExicution.invoke(context)?.getDriverDao()

        timeToStartOfTheDay(LocalDateTime.now())
        timeToStartOfTheDay1(Date())
        setListener()
    }

    fun timeToStartOfTheDay(inDate: LocalDateTime) {
        //var dateTeimObj = LocalDateTime.now()
        var utcTime = inDate.atZone(ZoneOffset.UTC);
        print(utcTime)
        //Log.i(utcTime)
    }

    fun timeToStartOfTheDay1(inDate: Date) {
        println("Date is: $inDate")

        //Getting the default zone id

        //Getting the default zone id

        //Converting the date to Instant

        //Converting the date to Instant
        val instant: Instant = inDate.toInstant()

        //Converting the Date to LocalDate

        //Converting the Date to LocalDate
        val localDate: LocalDate = instant.atZone(ZoneOffset.UTC).toLocalDate()
        println("Local Date is: $localDate")


        val test1 = localDate.atStartOfDay(ZoneOffset.UTC)
        val test2 = localDate.atStartOfDay()

        val totalSecond = test2.second

        ///val time23 = Timestamp(test2.)
        val date = Date()
        val ts = Timestamp(date.getTime())
        val outputTimestamp = startOfDay(ts)

        //var dateTeimObj = LocalDateTime.now()
        //var utcTime = inDate.atZone(ZoneOffset.UTC);
        print(inDate)
        // Log.i(utcTime)
    }

    fun startOfDay(time: Timestamp): Int {
        val cal = Calendar.getInstance()
        cal.timeInMillis = time.getTime()
        cal[Calendar.HOUR_OF_DAY] = 0 //set hours to zero
        cal[Calendar.MINUTE] = 0 // set minutes to zero
        cal[Calendar.SECOND] = 0 //set seconds to zero
        Log.i("Time", cal.time.toString())
        return cal.timeInMillis.toInt() / 1000
    }


    private fun setListener() {
        btnLogin.setOnClickListener(this)
        ivDialogOption.setOnClickListener(this)
        etEmail.addTextChangedListener(this)
        etPassword.addTextChangedListener(this)
    }




    override fun onClick(view: View?) {


        when (view?.id) {

            R.id.btnLogin -> {

                if (checkValidation()) {
                    callLoginApi()

                }
            }

            R.id.ivDialogOption -> {
                val dialog = CommonDialogs(this)
                dialog.setCallback(context)
                dialog.show(supportFragmentManager, "Login Activity")
            }
        }

    }

    private fun callLoginApi() {

        progressbar.visibility = View.VISIBLE
        if (CommonUtils.isOnline(context)) {
            mUserInfo =
                LoginRequest(etEmail.text.toString(), etPassword.text.toString())
            var call =
                RetrofitExecuter.getApiInterface("", "", "sdf", false).addUser(userData = mUserInfo)
            Log.e("url", "" + call.request().url)
            call.enqueue(object : Callback<LoginResponce?> {
                override fun onResponse(
                    call: Call<LoginResponce?>,
                    response: Response<LoginResponce?>
                ) {
                    progressbar.visibility = View.GONE
                    if (response.body() != null) {
                        progressbar.visibility = View.GONE
                        val loginResponce = response.body()
                        profile = loginResponce?.profile!!
                        profile = DriverInformation(zip = profile.zip,lastName = profile.lastName,strAddress1 = profile.strAddress1,FleetDotNuber = profile.FleetDotNuber,dlNumber =  profile.dlNumber,strAddress2 = profile.strAddress2,dlExpiryDate = profile.dlExpiryDate,email = profile.email,country = profile.country,primaryPhone = profile.primaryPhone,firstName = profile.firstName,state = profile.state,city = profile.city,dlBackPic = profile.dlBackPic,dlFrontPiv = profile.dlFrontPiv,secondaryPhone = profile.secondaryPhone,id = profile.id)
                        viewModel.insertDriverInfromation(profile)
                         DataHandler.currentDriver = profile
                        //  fillingDriverProfile(profile)
                        startActivity(Intent(context,MainActivity::class.java))
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
                    call: Call<LoginResponce?>,
                    t: Throwable
                ) {
                    progressbar.visibility = View.GONE
                    call.cancel()
                }
            })
        } else {
            Toast.makeText(context, "Please check internet connection", Toast.LENGTH_LONG)
        }

    }




    private fun checkValidation(): Boolean {
        if (etEmail.text.toString().isEmpty()) {
            tvEmailError.visibility = View.VISIBLE
            tvEmailError.text = getString(R.string.empty_email)
            return false
        }
        else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(etEmail.text.toString()).matches()) {
            tvEmailError.visibility = View.VISIBLE
            tvEmailError.text = getString(R.string.valid_email)
            return false
        }
        else if (etPassword.text.toString().isEmpty()) {
            tvPasswordError.visibility = View.VISIBLE
            tvPasswordError.text = getString(R.string.enter_password)
            return false
        }

        return true
    }

    override fun afterTextChanged(p0: Editable?) {
    }

    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
    }

    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        tvEmailError.visibility = View.GONE
        tvPasswordError.visibility = View.GONE
    }

    override fun onClose() {
    }

    override fun onDemo() {
        startActivity(Intent(context, MainActivity::class.java))
    }

    override fun onTest() {
    }

    override fun onRadio() {
    }

    override fun onHelp() {

    }

    override fun onMessage() {
    }

    override fun onDotInspect() {

    }



    override fun onSetting() {
    }

    override fun onLogBook() {
    }

    override fun onSos() {
    }

    private fun fillingDriverProfile(mdriverInformation: DriverInformation?) {
        CoroutineScope(Dispatchers.IO).launch {
            //mdriverInformation?.let { driverInformation?.insertCourseDetailData(mdriverInformation) }
            if (mdriverInformation != null) {

            }
        }
    }


}