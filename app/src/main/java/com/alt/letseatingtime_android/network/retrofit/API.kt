package com.alt.letseatingtime_android.network.retrofit

import com.alt.letseatingtime_android.network.retrofit.response.meal.MealResponse
import com.alt.letseatingtime_android.network.retrofit.request.LoginRequest
import com.alt.letseatingtime_android.network.retrofit.request.SignupRequest
import com.alt.letseatingtime_android.network.retrofit.response.login.LoginResponse
import com.alt.letseatingtime_android.network.retrofit.response.SignupResponse
import com.alt.letseatingtime_android.network.retrofit.response.profile.ProfileResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path
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
    @GET("api/openapi/meal")
    fun meal(
        @Query("date") date: String
    ): Call<MealResponse>

    @GET("/api/user/image/{idx}")
    fun image(
        @Header("Authorization") Authorization: String,
        @Path(value = "idx") idx: String
    ): Call<String>


    @GET("/api/user/profile")
    fun profile(
        @Header("Authorization") Authorization: String
    ): Call<ProfileResponse>

}