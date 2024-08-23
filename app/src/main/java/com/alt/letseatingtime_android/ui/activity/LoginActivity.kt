package com.alt.letseatingtime_android.ui.activity

import android.animation.ArgbEvaluator
import android.animation.ValueAnimator
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.alt.letseatingtime.R
import com.alt.letseatingtime.databinding.ActivityLoginBinding
import com.alt.letseatingtime_android.MyApplication.Companion.prefs
import com.alt.letseatingtime_android.network.retrofit.RetrofitClient
import com.alt.letseatingtime_android.network.retrofit.request.LoginRequest
import com.alt.letseatingtime_android.network.retrofit.response.login.LoginResponse
import com.alt.letseatingtime_android.util.OnSingleClickListener
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
        val startColor =
            ContextCompat.getColor(this, if (!boolean) R.color.button else R.color.button_push)
        val endColor = ContextCompat.getColor(
            this, if (!boolean) R.color.button else R.color.button_push
        )
        // ValueAnimator 생성
        val animator = ValueAnimator.ofObject(ArgbEvaluator(), startColor, endColor)
        animator.duration = 60 // 애니메이션 지속 시간 (밀리초)

        animator.addUpdateListener {
            val animatedValue = animator.animatedValue as Int
            binding.btnLoginSubmit.background.setTint(animatedValue)
        }

        animator.start()
    }

    private val callback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            // 뒤로 버튼 이벤트 처리
            exit()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        this.onBackPressedDispatcher.addCallback(this, callback)
        binding.btnLoginSubmit.setOnClickListener(OnSingleClickListener {
            val id = binding.etId.text.toString()
            val pw = binding.etPw.text.toString()
            if (id != "" && pw != "") {
                loginBtnAnim(true)
                binding.loginErrorMessage.text = ""
                login(id = id, pw = pw)
                binding.loginErrorMessage.visibility = View.GONE
            } else {
                binding.loginErrorMessage.visibility = View.VISIBLE
                binding.loginErrorMessage.text = "비밀번호와 아이디를 입력해주세요"
            }
        })

        binding.tvSignup.setOnClickListener(OnSingleClickListener {
            Intent(this, SignupActivity::class.java).also {
                startActivity(it)
            }
        })
        binding.tvFindPw.setOnClickListener(OnSingleClickListener {
            Toast.makeText(this, "아직 개발중인 기능입니다.", Toast.LENGTH_SHORT).show()
        })
    }

    private fun login(id: String, pw: String) {
        RetrofitClient.api.login(LoginRequest(id = id, password = pw))
            .enqueue(object : Callback<LoginResponse> {
                override fun onResponse(
                    call: Call<LoginResponse>,
                    response: Response<LoginResponse>
                ) {
                    val result = response.body()
                    if (response.isSuccessful) {
                        binding.loginErrorMessage.visibility = View.GONE
                        val intent = Intent(this@LoginActivity, HomeActivity::class.java)
                        if (binding.cbLogin.isChecked) {
                            prefs.autoLogin = true
                            prefs.refreshToken = result?.data?.refreshToken.toString()
                            prefs.accessToken = result?.data?.accessToken.toString()
                        } else {
                            prefs.autoLogin = false
                            prefs.accessToken = result?.data?.accessToken.toString()
                        }
                        startActivity(intent)
                        finish()
                    } else {
                        binding.loginErrorMessage.visibility = View.VISIBLE
                        binding.loginErrorMessage.text = "비밀번호나 아이디가 틀렸습니다"
                        loginBtnAnim(false)
                    }
                }

                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                    Toast.makeText(this@LoginActivity, "서버 애러", Toast.LENGTH_SHORT).show()
                    loginBtnAnim(false)
                }
            })
    }

    fun exit() {
        ActivityCompat.finishAffinity(this)
    }
}