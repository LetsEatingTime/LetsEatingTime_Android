package com.alt.letseatingtime_android.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.alt.letseatingtime_android.MyApplication
import com.alt.letseatingtime_android.databinding.ActivityLoginBinding
import com.alt.letseatingtime_android.network.retrofit.RetrofitClient
import com.alt.letseatingtime_android.network.retrofit.request.LoginRequest
import com.alt.letseatingtime_android.network.retrofit.response.LoginResponse
import com.alt.letseatingtime_android.util.LoginPattern
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
            val patternPw = Pattern.compile(LoginPattern.id)

            if (patternId.matcher(id).find() && patternPw.matcher(pw).find()) {
                if (id != "" && pw != "") {
                    login(id, pw)
                } else {
                    Toast.makeText(this, "아이디나 비밀번호를 입력해주세요", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "아이다나 비밀번호를 제대로 입력해주세요", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun login(id: String, pw: String) {
        RetrofitClient.api.login(LoginRequest(id, pw)).enqueue(object : Callback<LoginResponse>{
            override fun onResponse(
                call: Call<LoginResponse>,
                response: Response<LoginResponse>
            ) {
                Log.d("상태", "$id $pw")

                val result = response.body()
                if (response.code() == 200) {
                    val mainIntent = Intent(this@LoginActivity, MainActivity2::class.java)

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