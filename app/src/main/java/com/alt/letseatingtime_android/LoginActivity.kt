package com.alt.letseatingtime_android

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.alt.letseatingtime_android.databinding.ActivityLoginBinding
import com.alt.letseatingtime_android.network.retrofit.RetrofitClient
import com.example.login.network.retrofit.request.LoginRequest
import com.example.login.network.retrofit.response.LoginResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {
    private val binding: ActivityLoginBinding by lazy { ActivityLoginBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

//        val login_id =
//        val login_pw =
//        Log.d("상태",)

        binding.btnLoginSubmit.setOnClickListener {
            RetrofitClient.api.login(LoginRequest(binding.etId.text.toString(), binding.etPw.text.toString())).enqueue(object : Callback<LoginResponse>{
                override fun onResponse(
                    call: Call<LoginResponse>,
                    response: Response<LoginResponse>
                ) {
                    Log.d("상태", "${binding.etId.text} ${binding.etPw.text}")

                    val result = response.body()
                    if (response.code() == 200) {
                        val mainIntent = Intent(this@LoginActivity, Main2::class.java)

                        finishAffinity()
                        startActivity(mainIntent)
                        Log.d("상태", "${result?.accessToken}, ${result?.refreshToken}")

                        MyApplication.prefs.accessToken = result?.accessToken
                        MyApplication.prefs.refrashToken = result?.refreshToken
                        Log.d("상태", "${MyApplication.prefs.accessToken}, ${MyApplication.prefs.refrashToken}")
                    }
                    else{
                        Log.d("상태", "${response.code()}")
                    }
                }

                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
//                    val mainIntent: Intent = Intent(this@LoginActivity, Main2::class.java)
//                    startActivity(mainIntent)
                }
            })
        }
    }
}