package com.alt.letseatingtime_android.ui.activity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import com.alt.letseatingtime_android.MyApplication.Companion.prefs
import com.alt.letseatingtime_android.network.retrofit.RetrofitClient
import com.alt.letseatingtime_android.network.retrofit.response.login.LoginResponse
import com.alt.letseatingtime_android.network.retrofit.response.profile.ProfileResponse
import com.example.letseatingtime.R
import com.example.letseatingtime.databinding.ActivitySplashScreenBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SplashScreenActivity : AppCompatActivity() {
    private val binding: ActivitySplashScreenBinding by lazy {
        ActivitySplashScreenBinding.inflate(layoutInflater)
    }
    private val splashTimeOut: Long = 1000

    companion object {
        lateinit var instance: SplashScreenActivity
        fun ApplicationContext(): Context {
            return instance.applicationContext
        }
    }
    init {
        instance = this
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val handler = Handler(Looper.getMainLooper())
        getProfile()
//        handler.postDelayed({
//
//        }, splashTimeOut)
    }

    private fun getProfile(){
        RetrofitClient.api.profile("Bearer " + prefs.accessToken?:"").enqueue(object : Callback<ProfileResponse>{
            override fun onResponse(
                call: Call<ProfileResponse>,
                response: Response<ProfileResponse>
            ) {
                Log.d("refreshToken토큰", prefs.refreshToken?:"")
                Log.d("accessToken토큰", prefs.accessToken?:"")
                if(response.isSuccessful){
                    login()
                    Log.d("getProfile상태",response.body().toString())
                } else {
                    Log.d("getProfile상태",response.code().toString())
                    refreshToken()
                }
            }

            override fun onFailure(call: Call<ProfileResponse>, t: Throwable) {

            }

        })

    }

    private fun refreshToken(){
        RetrofitClient.api.refresh("Bearer " + prefs.refreshToken).enqueue(object : Callback<LoginResponse>{
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if(response.isSuccessful){
                    prefs.refreshToken = response.body()?.data?.refreshToken
                    prefs.accessToken = response.body()?.data?.accessToken
                    login()
                } else {
                    logout()
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                logout()
            }
        })

    }

    private fun login(){
        Toast.makeText(this, "로그인", Toast.LENGTH_SHORT).show()
        val intent = Intent(this, HomeActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
        startActivity(intent)
        finish()
    }

    private fun logout(){
        prefs.refreshToken = null
        prefs.accessToken = null
        Toast.makeText(this, "로그아웃", Toast.LENGTH_SHORT).show()
        val intent = Intent(this, LoginActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
        startActivity(intent)
        finish()
    }

}