package com.alt.letseatingtime_android

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import com.alt.letseatingtime_android.databinding.ActivitySignupBinding
import com.alt.letseatingtime_android.network.retrofit.RetrofitClient
import com.example.login.network.retrofit.request.SignupRequest
import com.example.login.network.retrofit.response.SignupResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignupActivity : AppCompatActivity() {
    private lateinit var signupFragment1: SignupFragment1
    private val binding: ActivitySignupBinding by lazy { ActivitySignupBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        // 처음에 프레그먼트1로 이동
        signupFragment1 = SignupFragment1.newInstance()
        supportFragmentManager.beginTransaction().replace(R.id.fragmentContainer, signupFragment1).commit()

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