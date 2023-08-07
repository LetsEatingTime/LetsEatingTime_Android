package com.alt.letseatingtime_android.ui.activity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.alt.letseatingtime_android.MyApplication.Companion.prefs
import com.alt.letseatingtime_android.network.retrofit.RetrofitClient
import com.alt.letseatingtime_android.network.retrofit.response.meal.MealResponse
import com.alt.letseatingtime_android.network.retrofit.response.profile.ProfileResponse
import com.example.letseatingtime.databinding.ActivityHomeBinding
import kotlinx.coroutines.Deferred
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class HomeActivity : AppCompatActivity() {
    private val binding: ActivityHomeBinding by lazy { ActivityHomeBinding.inflate(layoutInflater) }
    lateinit var img: Deferred<String>

    companion object {
        lateinit var instance: HomeActivity
        fun ApplicationContext(): Context {
            return instance.applicationContext
        }
    }

    init {
        instance = this
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        getProfile()
        getImg(prefs.userIdx!!)
        getMeal()


        val stname = binding.nameId
        stname.text = prefs.userName // TextView에 userName 값을 설정합니다.

        val textView = binding.trashId
        val userClass =
            prefs.userGrade + "학년 " + prefs.userClassName + "반 " + prefs.userClassNo + "번"
        textView.text = userClass





        //나의 급식 현황

        // 날짜
        var now = LocalDate.now()
        var Strnow = now.format(DateTimeFormatter.ofPattern("yyyy/MM/dd"))
        binding.today.text = Strnow


        //로그아웃
        binding.logout.setOnClickListener {
            val intent: Intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    //급식 확인
    fun getMeal(){
        val current = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("yyyyMMdd")
        RetrofitClient.api.meal(current.format(formatter)).enqueue(object : Callback<MealResponse> {
            override fun onResponse(
                call: Call<MealResponse>, response: retrofit2.Response<MealResponse>
            ) {
                if (response.code() == 200) {
                    binding.mealMenu.text = response.body()?.data?.lunch?.menu.toString()
                } else {

                }
            }

            override fun onFailure(call: Call<MealResponse>, t: Throwable) {
                Log.d("밥", t.message.toString())
            }
        })
    }

    fun getImg(idx: String) {
        RetrofitClient.api.image(prefs.accessToken.toString(),idx).enqueue(object : Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                if (response.code() == 200) {
                    prefs.userImg = response.body().toString()
                    Log.d("사진", prefs.userImg.toString())
                } else if (response.code() == 500) {
                    Log.d("ERROR", response.code().toString())
                    Log.d("ERROR", idx.toString())

//                    TODO("사진이 없을때 어떻게 할지")
                } else {
                    Log.d("ERROR", response.code().toString())
                }

            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                Log.d("ERROR", t.message.toString())
            }
        })
    }

    fun getProfile() {
        RetrofitClient.api.profile("Bearer ${prefs.accessToken.toString()}")
            .enqueue(object : Callback<ProfileResponse> {
                override fun onResponse(
                    call: Call<ProfileResponse>, response: Response<ProfileResponse>
                ) {
                    if (response.code() == 200) {
                        Log.d("상태", response.body().toString())
                        prefs.userName = response.body()?.data?.user?.name
                        Log.d("이름",response.body()?.data?.user?.name.toString())
                        prefs.userGrade = response.body()?.data?.user?.grade.toString()
                        prefs.userClassName = response.body()?.data?.user?.className.toString()
                        prefs.userClassNo = response.body()?.data?.user?.classNo.toString()
                        prefs.userIdx = response.body()?.data?.user?.idx.toString()
                    } else {
                        Log.d("상태", response.code().toString())
                    }
                }

                override fun onFailure(call: Call<ProfileResponse>, t: Throwable) {
                    Log.d("상태", t.message.toString())
                }

            })
    }


}


