package com.alt.letseatingtime_android

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.renderscript.ScriptGroup.Binding
import android.util.Log
import com.alt.letseatingtime_android.databinding.ActivityMainBinding
import com.alt.letseatingtime_android.network.retrofit.RetrofitClient
import com.alt.letseatingtime_android.network.retrofit.response.MealResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.time.LocalDateTime

class MainActivity : AppCompatActivity() {
    private var mbinding: ActivityMainBinding ?= null
    private val binding get() = mbinding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("상태","onCreate()")
        mbinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        RetrofitClient.apiMeal.meal("2023","3","21").enqueue(object : Callback<MealResponse>{
            override fun onResponse(call: Call<MealResponse>, response: Response<MealResponse>) {
                if(response.code() == 200){
                    Log.d("상태","${response.body()}")
                } else {
                    Log.d("상태","${response.code()}")
                }
            }

            override fun onFailure(call: Call<MealResponse>, t: Throwable) {
                Log.d("상태",t.message.toString())
            }

        })
    }

    override fun onStart() {
        super.onStart()
        Log.d("상태","onStart()")
    }

    override fun onStop() {
        super.onStop()
        Log.d("상태","onStop()")
    }

    override fun onRestart(){
        super.onRestart()
        Log.d("상태","onRestart()")
    }

    //onStop()이던 상태가 완전이 제거되는 단계 / Activity가 호출하는 마지막 메소드
    override fun onDestroy() {
        super.onDestroy()
        Log.d("상태","onDestroy()")
        mbinding = null
    }
}