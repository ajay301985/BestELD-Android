package com.ethane.choosetobefit.web_services

import com.eld.besteld.networkHandling.ApiInterface
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitExecuter {
    fun getApiInterface(
        callingFor: String?,
        accessToken: String?,
        tokenType: String?,
        isFrom: Boolean
    ): ApiInterface {

      //  val BASE_URL = "13.233.111.74:5000"
        val BASE_URL = "http:52.52.43.159:8080/api/"
      //  val BASE_URL = "https://www.itsbesteld.com/api/"
        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        val okHttpClient = OkHttpClient()
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(
                okHttpClient.newBuilder()
                    .connectTimeout(2 * 60 * 1000.toLong(), TimeUnit.SECONDS)
                    .readTimeout(2 * 60 * 1000.toLong(), TimeUnit.SECONDS)
                    .writeTimeout(2 * 60 * 1000.toLong(), TimeUnit.SECONDS)
                    .addInterceptor { chain ->
                        val original = chain.request()
                        val requestBuilder = original.newBuilder()
                            .header("Content-Type", "application/json")
                            .header("Authorization", "Bearer" + "eyJraWQiOiJLOSswR3hadytGYjVMY1VzWVwvUEFraVg0VGNkZmp5UFRiRTFodEpzUG5Lbz0iLCJhbGciOiJSUzI1NiJ9.eyJzdWIiOiJhNTU3OTkyZi03YjAxLTQzY2ItYjg3NC04NjI2M2I4ODA0MjYiLCJlbWFpbF92ZXJpZmllZCI6dHJ1ZSwiaXNzIjoiaHR0cHM6XC9cL2NvZ25pdG8taWRwLnVzLXdlc3QtMS5hbWF6b25hd3MuY29tXC91cy13ZXN0LTFfUU1wVHRscHJsIiwiY3VzdG9tOmlkIjoiMjQyMDJmNTAtNzIxOS0xMWViLTg3N2MtNWQ4NWIwMmQyMzkyIiwiY29nbml0bzp1c2VybmFtZSI6ImE1NTc5OTJmLTdiMDEtNDNjYi1iODc0LTg2MjYzYjg4MDQyNiIsImF1ZCI6IjRuM2g2MXU4N2kzdG9sNHE2YTNzb2toYnA0IiwiZXZlbnRfaWQiOiJlZmNkZDJjZi01ZDRhLTRmY2QtYjlhMy0wMGYxMjcxZjkzZTMiLCJ0b2tlbl91c2UiOiJpZCIsImF1dGhfdGltZSI6MTYxNDM1OTAwMywiY3VzdG9tOnN0YXR1cyI6ImFjdGl2ZSIsImV4cCI6MTYxNDM2MjYwMywiY3VzdG9tOnJvbGUiOiJkcml2ZXIiLCJpYXQiOjE2MTQzNTkwMDMsImVtYWlsIjoicGFua2Fqc3VuYWw2NkBnbWFpbC5jb20ifQ.FlZyk6dVM04TapShTnQiP_p5MQ3lZU0X7ruIxLZTDkuqfVWJCQklE3kojHoivEiLxEGtM2iTReLcThLfK0iU2Fn_Hh4GV33cOGGOvosuCBLxrk2uTNdBNlmbTd4Uf8uJSK-xdi3AWIFG5iC4_YUmpRlQrHb_E48NZ8N4U2SPw40uobtP-Hx04fQ2KOhpbdM7XiL9yYya-jUZdULu9udjOlXeXrPlJ-VfNB0r3Hft3o0bZYs0qgOBa_CIAveONlTa0P2yob7zzZSnxBDRf0sBbMMCmOKdeh6GbBt6koUvVHVWvJdD_zgB3n-3D58O7zOsRcnhJPJCr86iz1vQqNpsSA")
                            .method(original.method, original.body)
                        val request = requestBuilder.build()
                        chain.proceed(request)
                    }
                    .addInterceptor(interceptor)
                    .connectTimeout(60, TimeUnit.SECONDS)
                    .readTimeout(80, TimeUnit.SECONDS)
                    .build()
            ).build()
        return retrofit.create(ApiInterface::class.java)
    }
}