package com.alt.letseatingtime_android.network.retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
object RetrofitClient{
    private const val MealURL = "http://10.80.163.81:8080"
    private const val URL = "http://10.80.163.81:8080"

    //이찬혁 서버
    var server: Retrofit = Retrofit.Builder()
        .baseUrl(URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    var api: API = server.create(API::class.java)

    //바인드 서버
    var serverMeal: Retrofit = Retrofit.Builder()
        .baseUrl(MealURL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    var apiMeal: API = serverMeal.create(API::class.java)
}


