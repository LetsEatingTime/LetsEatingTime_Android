package com.alt.letseatingtime_android.network.retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
object RetrofitClient{
    private const val URL = "https://let.team-alt.com/"

    var server: Retrofit = Retrofit.Builder()
        .baseUrl(URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    var api: API = server.create(API::class.java)
}


