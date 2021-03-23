package com.eld.besteld.networkHandling
import com.eld.besteld.networkHandling.request.DutyDataRequest
import com.eld.besteld.networkHandling.request.LoginRequest
import com.eld.besteld.networkHandling.request.VinRequest
import com.eld.besteld.networkHandling.responce.VinResponce
import com.eld.besteld.networkHandling.responce.LoginResponce
import retrofit2.Call
import retrofit2.http.*


interface ApiInterface {
    @Headers("Content-Type: application/json")
    @POST("driver/login")
    fun addUser(@Body userData: LoginRequest): Call<LoginResponce>

    @Headers("Conttent-Type: application/json")
    @POST("logbook/createLogbook")
    fun createLogbook(@Body dutyDataReq: DutyDataRequest): Call<LoginResponce>

    @Headers("Conttent-Type: application/json")
    @POST("truck-trailer/getTruckMapping")
    fun getEldProfile(@Body vinRequest : VinRequest): Call<VinResponce>




}