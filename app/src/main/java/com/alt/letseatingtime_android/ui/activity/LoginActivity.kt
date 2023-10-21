package com.alt.letseatingtime_android.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.OnBackPressedCallback
import androidx.core.app.ActivityCompat
import com.alt.letseatingtime_android.MyApplication
import com.alt.letseatingtime_android.network.retrofit.RetrofitClient
import com.alt.letseatingtime_android.network.retrofit.request.LoginRequest
import com.alt.letseatingtime_android.network.retrofit.response.login.LoginResponse
import com.alt.letseatingtime.databinding.ActivityLoginBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {
    private val binding: ActivityLoginBinding by lazy { ActivityLoginBinding.inflate(layoutInflater) }

    private val callback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            // 뒤로 버튼 이벤트 처리
            exit()
            Log.e("뒤로가기", "뒤로가기 클릭")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        this.onBackPressedDispatcher.addCallback(this, callback)
        binding.btnLoginSubmit.setOnClickListener {
            val id = binding.etId.text.toString()
            val pw = binding.etPw.text.toString()

//            val patternId = Pattern.compile(LoginPattern.id)
//            val patternPw = Pattern.compile(LoginPattern.pw)
            if (id != "" && pw != "") {
                binding.loginErrorMessage.text = ""
                login(id = id, pw = pw)
                Log.d("인터넷", "id: $id, pw: $pw")
            } else {
                binding.loginErrorMessage.text = "비밀번호와 아이디를 입력해주세요"
            }
        }

        binding.tvSignup.setOnClickListener() {
            Intent(this, SignupActivity::class.java).also {
                startActivity(it)
            }
        }
    }

    private fun login(id: String, pw: String) {
        RetrofitClient.api.login(LoginRequest(id = id, password = pw)).enqueue(object : Callback<LoginResponse> {
            override fun onResponse(
                call: Call<LoginResponse>,
                response: Response<LoginResponse>
            ) {
                val result = response.body()
                if (response.isSuccessful) {
                    val intent = Intent(this@LoginActivity, HomeActivity::class.java)
                    if (binding.cbLogin.isChecked) {
                        MyApplication.prefs.autoLogin = true
                        MyApplication.prefs.refreshToken = result?.data?.refreshToken.toString()
                        MyApplication.prefs.accessToken = result?.data?.accessToken.toString()
                    }
                    Log.d("인터넷", response.body().toString())
                    startActivity(intent)
                    finish()
                } else {
                    binding.loginErrorMessage.text = "비밀번호나 아이디가 틀렸습니다"
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                Log.d("버그", t.message.toString())
            }
        })
    }

    fun exit() {
        ActivityCompat.finishAffinity(this)
    }
}