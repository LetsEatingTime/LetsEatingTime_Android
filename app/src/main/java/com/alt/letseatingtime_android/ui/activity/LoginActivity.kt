package com.alt.letseatingtime_android.ui.activity

import android.animation.ArgbEvaluator
import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.util.Log
import android.view.animation.AnimationUtils
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.graphics.toColor
import com.alt.letseatingtime.R
import com.alt.letseatingtime.databinding.ActivityLoginBinding
import com.alt.letseatingtime_android.MyApplication.Companion.prefs
import com.alt.letseatingtime_android.network.retrofit.RetrofitClient
import com.alt.letseatingtime_android.network.retrofit.request.LoginRequest
import com.alt.letseatingtime_android.network.retrofit.response.login.LoginResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class LoginActivity : AppCompatActivity() {
    private val binding: ActivityLoginBinding by lazy { ActivityLoginBinding.inflate(layoutInflater) }

    /**
     * 버튼 누를때 모션
     *
     * @param boolean up/down
     */
    private fun loginBtnAnim(boolean: Boolean) {
        val startColor = resources.getColor( if(boolean) R.color.button else R.color.button_push )
        val endColor = resources.getColor( if(! boolean) R.color.button else R.color.button_push )

        // ValueAnimator 생성
        val animator = ValueAnimator.ofObject(ArgbEvaluator(), startColor, endColor)
        animator.duration = 100 // 애니메이션 지속 시간 (밀리초)

        animator.addUpdateListener { valueAnimator ->
            val animatedValue = animator.animatedValue as Int
            binding.btnLoginSubmit.background.setTint(animatedValue)
        }

        animator.start()
    }

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

            //val animation = AnimationUtils.loadAnimation(this, R.anim.btn_animation)
            //binding.btnLoginSubmit.startAnimation(animation)

            val id = binding.etId.text.toString()
            val pw = binding.etPw.text.toString()

//            val patternId = Pattern.compile(LoginPattern.id)
//            val patternPw = Pattern.compile(LoginPattern.pw)
            if (id != "" && pw != "") {
                loginBtnAnim(true)
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
                        prefs.autoLogin = true
                        prefs.refreshToken = result?.data?.refreshToken.toString()
                        prefs.accessToken = result?.data?.accessToken.toString()
                    } else {
                        prefs.autoLogin = false
                        prefs.accessToken = result?.data?.accessToken.toString()
                    }
                    Log.d("인터넷", response.body().toString())
                    startActivity(intent)
                    finish()
                } else {
                    binding.loginErrorMessage.text = "비밀번호나 아이디가 틀렸습니다"
                    loginBtnAnim(false)
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                Log.d("버그", t.message.toString())
                loginBtnAnim(false)
            }
        })
    }

    fun exit() {
        ActivityCompat.finishAffinity(this)
    }
}