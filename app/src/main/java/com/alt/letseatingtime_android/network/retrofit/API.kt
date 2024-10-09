package com.alt.letseatingtime_android.network.retrofit

import com.alt.letseatingtime_android.network.retrofit.response.meal.MealResponse
import com.alt.letseatingtime_android.network.retrofit.request.LoginRequest
import com.alt.letseatingtime_android.network.retrofit.request.SignupRequest
import com.alt.letseatingtime_android.network.retrofit.response.ImageResponse
import com.alt.letseatingtime_android.network.retrofit.response.login.LoginResponse
import com.alt.letseatingtime_android.network.retrofit.response.SignupResponse
import com.alt.letseatingtime_android.network.retrofit.response.profile.ProfileResponse
import com.alt.letseatingtime_android.network.retrofit.response.WithdrawResponse
import com.alt.letseatingtime_android.network.retrofit.response.goods.StoreResponse
import com.alt.letseatingtime_android.network.retrofit.response.profile.User
import com.alt.letseatingtime_android.network.retrofit.response.pwchange.PwChangeRequest
import com.alt.letseatingtime_android.network.retrofit.response.scan.ScanResponse
import okhttp3.MultipartBody
import com.alt.letseatingtime_android.network.retrofit.response.util.BaseResponse
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Multipart
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

    @POST("/api/account/pw-change")
    fun pwChange(
        @Body body: PwChangeRequest
    ): Call<Unit>

    @GET("api/openapi/meal")
    fun meal(
        @Query("date") date: String
    ): Call<MealResponse>

    @GET("/api/user/image/{idx}")
    fun getUserImage(
        @Header("Authorization") authorization: String,
        @Path(value = "idx") idx: String
    ): Call<ImageResponse>

    @GET("/api/file/get/{idx}")
    fun getProductImage(
        @Path("idx") idx: Int
    ): Call<BaseResponse<ImageResponse>>

    @POST("teacher/edit/student")
    fun editStudent(
        @Header("Authorization") Authorization: String,
        @Body body: User
    ): Call<BaseResponse<String>>

    @GET("/api/user/profile")
    fun profile(
        @Header("Authorization") Authorization: String
    ): Call<ProfileResponse>

    @GET("api/account/refresh.do")
    fun refresh(
        @Header("Authorization") Authorization: String
    ): Call<LoginResponse>

    @POST("/api/user/withdraw")
    fun withdraw(
        @Header("Authorization") Authorization: String
    ): Call<WithdrawResponse>

    @Multipart
    @POST("/ai/please")
    fun scanMenu(
        @Part("userId") userId: Int,
        @Part file: MultipartBody.Part
    ): Call<ScanResponse>

    @GET("/api/product/all")
    fun getStoreList(
    ): Call<BaseResponse<List<StoreResponse>>>

}