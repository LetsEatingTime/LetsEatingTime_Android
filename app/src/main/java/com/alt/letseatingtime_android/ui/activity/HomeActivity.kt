package com.alt.letseatingtime_android.ui.activity

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.alt.letseatingtime_android.MyApplication.Companion.prefs
import com.alt.letseatingtime_android.network.retrofit.RetrofitClient
import com.alt.letseatingtime_android.network.retrofit.response.meal.MealResponse
import com.alt.letseatingtime_android.network.retrofit.response.profile.ProfileResponse
import com.bumptech.glide.Glide
import com.example.letseatingtime.databinding.ActivityHomeBinding
import kotlinx.coroutines.Deferred
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
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
        getMeal()





        //로그아웃
        binding.logout.setOnClickListener {
            prefs.refreshToken = null
            prefs.accessToken = null
            prefs.userGrade = null
            prefs.userClassName = null
            prefs.userClassNo = null
            prefs.userIdx = null
            prefs.userImg = null
            prefs.userName = null
            val intent: Intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun getImg(id: String) {
        RetrofitClient.api.image(prefs.accessToken.toString(), id)
            .enqueue(object : Callback<ResponseBody> {
                override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                    if (response.code() == 200) {
                        Log.d("사진", prefs.userImg.toString())
                        val photoBytes = response.body()?.bytes()
                        if (photoBytes != null) {
                            val image = getBitmapFromBytes(photoBytes)
                            Glide.with(instance)
                                .load(image)
                                .into(binding.studentImage)
                        } else {
                            Log.e("ApiError", "Photo data is null.")
                        }
                    } else if (response.code() == 500) {
                        Log.d("애러", response.code().toString())
                    } else {
                        Log.d("애러", response.code().toString())
                    }

                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    Log.d("애러", t.message.toString())
                }
            })
    }

    fun getBitmapFromBytes(byteArray: ByteArray): Bitmap {
        return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
    }

    private fun getProfile() {
        RetrofitClient.api.profile("Bearer ${prefs.accessToken.toString()}")
            .enqueue(object : Callback<ProfileResponse> {
                override fun onResponse(
                    call: Call<ProfileResponse>, response: Response<ProfileResponse>
                ) {
                    if (response.code() == 200) {
                        prefs.userName = response.body()?.data?.user?.name
                        prefs.userGrade = response.body()?.data?.user?.grade.toString()
                        prefs.userClassName = response.body()?.data?.user?.className.toString()
                        prefs.userClassNo = response.body()?.data?.user?.classNo.toString()
                        prefs.userIdx = response.body()?.data?.user?.idx.toString()
                        prefs.userImg = response.body()?.data?.user?.image.toString()
                        prefs.userName = response.body()?.data?.user?.name
                        getImg(prefs.userImg?:"0")

                        val stname = binding.nameId
                        stname.text = prefs.userName // TextView에 userName 값을 설정합니다.

                        val textView = binding.trashId
                        val userClass =
                            prefs.userGrade + "학년 " + prefs.userClassName + "반 " + prefs.userClassNo + "번"
                        textView.text = userClass

                        var now = LocalDate.now()
                        var strnow = now.format(DateTimeFormatter.ofPattern("yyyy/MM/dd"))
                        binding.today.text = strnow
                    } else {
                        Log.d("상태", response.code().toString())
                    }
                }

                override fun onFailure(call: Call<ProfileResponse>, t: Throwable) {
                    Log.d("상태", t.message.toString())
                }

            })
    }

    private fun getMeal() {
        val current = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("yyyyMMdd")
        val currentTime = current.format(formatter)

        val breakfastStartTime = LocalTime.of(20, 0)
        val lunchStartTime = LocalTime.of(9, 0)
        val dinnerStartTime = LocalTime.of(14, 0)

        val currentTimeOfDay = current.toLocalTime()
        val mealType: String

        mealType = when {
            currentTimeOfDay.isBefore(lunchStartTime) -> "breakfast"
            currentTimeOfDay.isBefore(dinnerStartTime) -> "lunch"
            else -> "dinner"
        }

        RetrofitClient.api.meal(currentTime).enqueue(object : Callback<MealResponse> {
            override fun onResponse(
                call: Call<MealResponse>, response: retrofit2.Response<MealResponse>
            ) {
                if (response.code() == 200) {
                    val menu: String? = when (mealType) {
                        "breakfast" -> response.body()?.data?.breakfast?.menu?.toString()
                        "lunch" -> response.body()?.data?.lunch?.menu?.toString()
                        else -> response.body()?.data?.dinner?.menu?.toString()
                    }
                    binding.mealMenu.text = menu ?: when (mealType) {
                        "breakfast" -> "아침이 없습니다."
                        "lunch" -> "점심이 없습니다."
                        else -> "저녁이 없습니다."
                    }
                } else {
                    Log.d("급식을 불러오는데 실패하였습니다.", response.toString())
                }
            }

            override fun onFailure(call: Call<MealResponse>, t: Throwable) {
                Log.d("밥", t.message.toString())
            }
        })
    }

}


