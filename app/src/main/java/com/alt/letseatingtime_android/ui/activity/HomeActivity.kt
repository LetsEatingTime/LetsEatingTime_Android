package com.alt.letseatingtime_android.ui.activity

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.ImageView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.alt.letseatingtime_android.MyApplication.Companion.prefs
import com.alt.letseatingtime_android.network.retrofit.RetrofitClient
import com.alt.letseatingtime_android.network.retrofit.response.meal.MealResponse
import com.bumptech.glide.Glide
import com.example.letseatingtime.databinding.ActivityHomeBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import retrofit2.Call
import retrofit2.Callback
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class HomeActivity : AppCompatActivity() {
    private val binding: ActivityHomeBinding by lazy { ActivityHomeBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        getImg("141")

        val stname = binding.someId
        val userName = prefs.userName // userName 값을 가져옵니다.
        stname.text = userName // TextView에 userName 값을 설정합니다.

        val textView = binding.trashId
        val userClass =
            prefs.userGrade + "학년 " + prefs.userClassName + "반 " + prefs.userClassNo + "번"
        textView.text = userClass

//        val student_image = binding.studentImage
//        Glide.with(this).load(getImg("141")).into(student_image)


        val current = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("yyyyMMdd")
        RetrofitClient.api.meal(current.format(formatter))
            .enqueue(object : Callback<MealResponse> {
                override fun onResponse(
                    call: Call<MealResponse>,
                    response: retrofit2.Response<MealResponse>
                ) {
                    if (response.code() == 200) {
                        Log.d("밥", "${response.body()}")
                    } else {
                        Log.d("밥", "${response.code()}")
                    }
                }

                override fun onFailure(call: Call<MealResponse>, t: Throwable) {
                    Log.d("밥", t.message.toString())
                }
            })

        //로그아웃
        binding.logout.setOnClickListener {
            val intent: Intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    fun getImg(idx: String){
        CoroutineScope(Dispatchers.IO).async {
            val imgURL = async {
                RetrofitClient.api.image(idx)
            }
            Log.d("사진",imgURL.await())
        }
    }
}


