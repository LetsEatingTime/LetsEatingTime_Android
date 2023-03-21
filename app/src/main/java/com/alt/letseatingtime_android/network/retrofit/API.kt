package com.alt.letseatingtime_android.network.retrofit

import com.alt.letseatingtime_android.network.retrofit.response.MealResponse
import com.example.login.network.retrofit.request.LoginRequset
import com.example.login.network.retrofit.request.SignupRequest
import com.example.login.network.retrofit.response.LoginResponse
import com.example.login.network.retrofit.response.SignupResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface API {
    @POST("api/user/login.do")
    fun login(
        @Body body: LoginRequset
    ): Call<LoginResponse>

    @POST("api/user/signup.do")
    fun signup(
        @Body body: SignupRequest
    ): Call<SignupResponse>

    @GET("meal")
    fun meal(
        @Query("year") year: String,
        @Query("month") month: String,
        @Query("day") day: String,
    ): Call<MealResponse>
}