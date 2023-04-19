package com.alt.letseatingtime_android.network.retrofit

import com.alt.letseatingtime_android.network.retrofit.response.MealResponse
import com.alt.letseatingtime_android.network.retrofit.request.LoginRequest
import com.alt.letseatingtime_android.network.retrofit.request.SignupRequest
import com.alt.letseatingtime_android.network.retrofit.response.LoginResponse
import com.alt.letseatingtime_android.network.retrofit.response.SignupResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface API {
    @POST("api/account/login.do")
    fun login(
        @Body body: LoginRequest
    ): Call<LoginResponse>

    @POST("api/account/signup.do")
    fun signup(
        @Body body: SignupRequest
    ): Call<SignupResponse>

    //바인트
    @GET("meal")
    fun meal(
        @Query("year") year: String,
        @Query("month") month: String,
        @Query("day") day: String,
    ): Call<MealResponse>
}