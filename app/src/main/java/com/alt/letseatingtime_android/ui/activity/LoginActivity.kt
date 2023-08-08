package com.alt.letseatingtime_android.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.alt.letseatingtime_android.MyApplication
import com.alt.letseatingtime_android.network.retrofit.RetrofitClient
import com.alt.letseatingtime_android.network.retrofit.request.LoginRequest
import com.alt.letseatingtime_android.network.retrofit.response.login.LoginResponse
import com.alt.letseatingtime_android.util.LoginPattern
import com.example.letseatingtime.databinding.ActivityLoginBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.regex.Pattern

class LoginActivity : AppCompatActivity() {
    private val binding: ActivityLoginBinding by lazy { ActivityLoginBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.btnLoginSubmit.setOnClickListener {
            val id = binding.etId.text.toString()
            val pw = binding.etPw.text.toString()

            val patternId = Pattern.compile(LoginPattern.id)
            val patternPw = Pattern.compile(LoginPattern.pw)

            if (id != "" && pw != "") {
                login(id, pw)
            } else {
                Toast.makeText(this, "아이디나 비밀번호를 입력해주세요", Toast.LENGTH_SHORT).show()
            }
        }

        binding.tvSignup.setOnClickListener()
        {
            val intent: Intent = Intent(this, SignupActivity::class.java)
            startActivity(intent)
        }
    }

    private fun login(id: String, pw: String) {
        RetrofitClient.api.login(LoginRequest(id, pw)).enqueue(object : Callback<LoginResponse> {
            override fun onResponse(
                call: Call<LoginResponse>,
                response: Response<LoginResponse>
            ) {
                Log.d("인터넷", response.code().toString())
                val result = response.body()
                if (response.code() == 200) {
                    val intent = Intent(this@LoginActivity, HomeActivity::class.java)
                    MyApplication.prefs.accessToken = result?.data?.accessToken
                    if(binding.cbLogin.isChecked){
                        MyApplication.prefs.refreshToken = result?.data?.refreshToken
                        MyApplication.prefs.accessToken = result?.data?.accessToken
                        Log.d("상태","로그인 상태 유지")
                    } else {
                        MyApplication.prefs.accessToken = result?.data?.accessToken
                        Log.d("상태","로그인 상태 유지X")
                    }

                    Log.d("인터넷",response.body().toString())
                    startActivity(intent)
                    finish()
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                Log.d("ERROR",t.message.toString())
            }
        })
    }
}