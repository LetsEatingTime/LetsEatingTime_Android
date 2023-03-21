package com.alt.letseatingtime_android.network.retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
object RetrofitClient{
    private const val MealURL = "http://172.67.191.173:443"
    private const val url = ""
//    var server: Retrofit = Retrofit.Builder()
//        .baseUrl(url)
//        .addConverterFactory(GsonConverterFactory.create())
//        .build()

    //alt 서버
    var serverMeal: Retrofit = Retrofit.Builder()
        .baseUrl(MealURL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
//    var api: API = server.create(API::class.java)
    //b1nd 서버
    var apiMeal: API = serverMeal.create(API::class.java)


}


