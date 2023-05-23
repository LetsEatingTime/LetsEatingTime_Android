package com.alt.letseatingtime_android.ui.activity

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import com.alt.letseatingtime_android.network.retrofit.RetrofitClient
import com.alt.letseatingtime_android.network.retrofit.response.meal.MealResponse
import com.example.letseatingtime.R
import com.example.letseatingtime.databinding.ActivityHomeBinding
import com.example.letseatingtime.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class HomeActivity : AppCompatActivity() {
    private val binding: ActivityHomeBinding by lazy { ActivityHomeBinding.inflate(layoutInflater) }
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val current = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("yyyyMMdd")
        RetrofitClient.api.meal(current.format(formatter))
            .enqueue(object : Callback<MealResponse> {
                override fun onResponse(
                    call: Call<MealResponse>,
                    response: retrofit2.Response<MealResponse>
                ) {
                    if (response.code() == 200) {
                        Log.d("밥", "${response.body()}")
                    } else {
                        Log.d("밥", "${response.code()}")
                    }
                }

                override fun onFailure(call: Call<MealResponse>, t: Throwable) {
                    Log.d("밥", t.message.toString())
                }
            })

        //로그아웃
        binding.logout.setOnClickListener {
            val intent: Intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

}


