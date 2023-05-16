package com.alt.letseatingtime_android.ui.activity

import android.app.AlertDialog
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.alt.letseatingtime_android.network.retrofit.RetrofitClient
import com.alt.letseatingtime_android.network.retrofit.response.MealResponse
import com.example.letseatingtime.R
import com.example.letseatingtime.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class MainActivity : AppCompatActivity() {
    private val binding: ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("상태", "onCreate()")
        setContentView(binding.root)
        val current = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("yyyyMMdd")
        RetrofitClient.apiMeal.meal(current.format(formatter))
            .enqueue(object : Callback<MealResponse> {
                override fun onResponse(
                    call: Call<MealResponse>,
                    response: retrofit2.Response<MealResponse>
                ) {
                    if (response.code() == 200) {
                        Log.d("상태", "${response.body()}")
                    } else {
                        Log.d("상태", "${response.code()}")
                    }
                }

                override fun onFailure(call: Call<MealResponse>, t: Throwable) {
                    Log.d("상태", t.message.toString())
                }
            })

        val loginIntent = Intent(this, LoginActivity::class.java)
        val signupIntent = Intent(this, SignupActivity::class.java)
        val homeIntent = Intent(this, HomeActivity::class.java)

        AlertDialog.Builder(this).run {
            setTitle("title")
            setIcon(R.drawable.btn_submit)
            setMessage("message")
            setPositiveButton("Yes") { dialog, which ->
            }
            setNegativeButton("No") { dialog, which ->
                dialog.dismiss()
                show()
            }
        }

        binding.btnLoginSubmit.setOnClickListener { startActivity(loginIntent) }
        binding.btnSignupSubmit.setOnClickListener { startActivity(signupIntent) }
    }

    override fun onStart() {
        super.onStart()
        Log.d("상태", "onStart()")
    }

    override fun onStop() {
        super.onStop()
        Log.d("상태", "onStop()")
    }

    override fun onRestart() {
        super.onRestart()
        Log.d("상태", "onRestart()")
    }

    //onStop()이던 상태가 완전이 제거되는 단계 / Activity가 호출하는 마지막 메소드
    override fun onDestroy() {
        super.onDestroy()
        Log.d("상태", "onDestroy()")
    }
}