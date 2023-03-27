package com.alt.letseatingtime_android

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.WindowManager
import com.alt.letseatingtime_android.databinding.ActivitySignupHomeBinding
import com.alt.letseatingtime_android.network.retrofit.RetrofitClient
import com.example.login.network.retrofit.request.SignupRequest
import com.example.login.network.retrofit.response.SignupResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Signup_home : AppCompatActivity() {
    private lateinit var signupFragment1: SignupFragment1
    private val binding: ActivitySignupHomeBinding by lazy { ActivitySignupHomeBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
//        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)

        // 툴바 설정
        signupFragment1 = SignupFragment1.newInstance()
        supportFragmentManager.beginTransaction().replace(R.id.fragmentContainer, signupFragment1).commit()

        setSupportActionBar(binding.toolBar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = ""
        RetrofitClient.api.signup(SignupRequest("돈카스","돈카스","2318","00:00:00","Y",'S')).enqueue(object :Callback<SignupResponse>{
            override fun onResponse(
                call: Call<SignupResponse>,
                response: Response<SignupResponse>
            ) {
                if(response.code() != 200){
                    Log.d("상태",response.code().toString())
                } else {
                    Log.d("상태",response.code().toString())
                }
            }

            override fun onFailure(call: Call<SignupResponse>, t: Throwable) {
                Log.d("상태",t.message.toString())
            }
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        when (id) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}