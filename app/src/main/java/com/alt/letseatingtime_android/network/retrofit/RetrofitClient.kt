package com.alt.letseatingtime_android.network.retrofit

import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient{
    private const val URL = "https://let.team-alt.com/"

    var gson = GsonBuilder().setLenient().create()

    var server: Retrofit = Retrofit.Builder()
        .baseUrl(URL)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()
    var api: API = server.create(API::class.java)
}


