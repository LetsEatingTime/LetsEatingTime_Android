package com.alt.letseatingtime_android.ui.activity

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.lifecycle.ViewModelProvider
import com.alt.letseatingtime.databinding.ActivityHomeBinding
import com.alt.letseatingtime_android.MyApplication.Companion.prefs
import com.alt.letseatingtime_android.network.retrofit.response.WithdrawResponse
import com.alt.letseatingtime_android.network.retrofit.response.meal.MealResponse
import com.alt.letseatingtime_android.network.retrofit.response.profile.ProfileResponse
import com.bumptech.glide.Glide
import com.alt.letseatingtime.R
import com.alt.letseatingtime_android.network.retrofit.RetrofitClient.api
import com.alt.letseatingtime_android.ui.viewmodel.UserActivityViewModel
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
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

    private val lunchStartTime = LocalTime.of(9, 0)
    private val dinnerStartTime = LocalTime.of(14, 0)
    private val breakfastEndTime = LocalTime.of(8, 20)
    private val lunchEndTime = LocalTime.of(13, 20)
    private val dinnerEndTime = LocalTime.of(19, 10)

    private val viewModel by lazy {
        ViewModelProvider(this)[UserActivityViewModel::class.java]
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        getProfile()
        getMeal()
        cheakMeal()

        binding.logout.setOnClickListener {
            prefs.remove()
            Toast.makeText(this, "로그아웃", Toast.LENGTH_SHORT).show()
            Intent(this, LoginActivity::class.java).also {
                startActivity(it)
            }
            finish()
        }

        binding.withdrawal.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            val dialog = builder.create()
            dialog.setOnShowListener {
                dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.RED)
                dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.BLACK)
            }

            builder.setTitle("경고").setMessage("정말로 탈퇴하시겠습니까?").setPositiveButton(
                    "예"
                ) { _, _ ->
                    withdrawal()
                }.setNegativeButton(
                    "아니요"
                ) { _, _ ->
                }
            builder.show()
        }
    }

    private fun getImg(id: String) {
        api.image(prefs.accessToken, id).enqueue(object : Callback<ResponseBody> {
                override fun onResponse(
                    call: Call<ResponseBody>, response: Response<ResponseBody>
                ) {
                    val photoBytes = response.body()?.bytes()
                    val image = getBitmapFromBytes(photoBytes)
                    Glide.with(this@HomeActivity).load(image)
                        .error(AppCompatResources.getDrawable(this@HomeActivity, R.drawable.profile_image_background))
                        .apply(RequestOptions.bitmapTransform(RoundedCorners(10)))
                        .into(binding.studentImage)
                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    Toast.makeText(this@HomeActivity, "서버 애러", Toast.LENGTH_SHORT).show()
                }
            })
    }

    fun getBitmapFromBytes(byteArray: ByteArray?): Bitmap? {
        return if (byteArray != null) {
            BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
        } else {
            null
        }
    }


    private fun getProfile() {
        api.profile("Bearer ${prefs.accessToken}").enqueue(object : Callback<ProfileResponse> {
                override fun onResponse(
                    call: Call<ProfileResponse>, response: Response<ProfileResponse>
                ) {
                    if (response.code() == 200) {
                        val user = response.body()?.data?.user
                        val now = LocalDate.now()
                        val strnow = now.format(DateTimeFormatter.ofPattern("yyyy/MM/dd"))
                        with(binding) {
                            nameId.text = user?.name
//                            trashId.text = "${user?.grade}학년 ${user?.className}반 ${user?.classNo}번"
                            trashId.text = getString(R.string.trash_id, user?.grade,user?.className,user?.classNo)
                            today.text = strnow
                        }
                        getImg(user?.image.toString())
                    } else {
                        viewModel.refreshToken()
                        getProfile()
                    }
                }

                override fun onFailure(call: Call<ProfileResponse>, t: Throwable) {
                    Toast.makeText(this@HomeActivity, "인터넷에 연결 되어있는지 확인 해주세요", Toast.LENGTH_SHORT)
                        .show()
                }

            })
    }

    private fun getMeal() {
        val current = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("yyyyMMdd")
        val currentTime = current.format(formatter)

        val currentTimeOfDay = current.toLocalTime()

        val mealType: String = when {
            currentTimeOfDay.isBefore(lunchStartTime) -> "breakfast"
            currentTimeOfDay.isBefore(dinnerStartTime) -> "lunch"
            else -> "dinner"
        }


        api.meal(currentTime).enqueue(object : Callback<MealResponse> {
            override fun onResponse(
                call: Call<MealResponse>, response: Response<MealResponse>
            ) {
                if (response.code() == 200) {
                    val data = response.body()?.data

                    binding.mealType.text = when (mealType) {
                        "breakfast" -> "조식"
                        "lunch" -> "중식"
                        else -> "석식"
                    }

                    val menu: String? = arrayToString(
                        when (mealType) {
                            "breakfast" -> data?.breakfast?.menu
                            "lunch" -> data?.lunch?.menu
                            else -> data?.dinner?.menu
                        }
                    )
                    binding.mealMenu.text = menu ?: when (mealType) {
                        "breakfast" -> "아침이 없습니다."
                        "lunch" -> "점심이 없습니다."
                        else -> "저녁이 없습니다."
                    }
                } else {
                    Toast.makeText(this@HomeActivity, "급식 불러오는데 실패하였습니다", Toast.LENGTH_SHORT).show()
                }
            }


            override fun onFailure(call: Call<MealResponse>, t: Throwable) {
                Toast.makeText(this@HomeActivity, "인터넷에 연결 되어있는지 확인 해주세요", Toast.LENGTH_SHORT)
                    .show()
            }
        })
    }

    /**
     * 문자열 배열을 줄내림 넣어 문자열로 반환
     *
     * @param List<String>?
     * @retrun String?
     */
    val arrayToString: (List<String>?) -> String? = { aStr ->
        var newStr = ""

        if (aStr != null) {
            for (i: Int in aStr.indices) {
                newStr += aStr[i].removePrefix("*")
                if (i < aStr.size - 1) newStr += "\n"
            }
        }
        newStr
    }

    private fun cheakMeal() {
        api.profile("Bearer ${prefs.accessToken}").enqueue(object : Callback<ProfileResponse> {
                override fun onResponse(
                    call: Call<ProfileResponse>, response: Response<ProfileResponse>
                ) {
                    if (response.isSuccessful) {
                        val mealData = response.body()?.data?.mealTime
                        if (mealData != null) {
                            breakfast(mealData)
                            lunch(mealData)
                            dinner(mealData)
                        } else {
                            with(binding) {
                                breakfastCheak.setBackgroundColor(Color.parseColor("#D9D9D9"))
                                lunchCheak.setBackgroundColor(Color.parseColor("#D9D9D9"))
                                dinnerCheak.setBackgroundColor(Color.parseColor("#D9D9D9"))

                                breakfastCheak.setBackgroundResource(R.drawable.cardview)
                                lunchCheak.setBackgroundResource(R.drawable.cardview)
                                dinnerCheak.setBackgroundResource(R.drawable.cardview)
                            }
                        }
                    } else {
                        Toast.makeText(
                            this@HomeActivity, "애러: ${response.code()}", Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onFailure(call: Call<ProfileResponse>, t: Throwable) {
                    Toast.makeText(this@HomeActivity, "인터넷에 연결 되어있는지 확인 해주세요", Toast.LENGTH_SHORT)
                        .show()
                }

            })
    }


    private fun withdrawal() {
        api.withdraw("Bearer ${prefs.accessToken}").enqueue(object : Callback<WithdrawResponse> {
                override fun onResponse(
                    call: Call<WithdrawResponse>, response: Response<WithdrawResponse>
                ) {
                    if (response.isSuccessful) {
                        logout()
                    }
                }

                override fun onFailure(call: Call<WithdrawResponse>, t: Throwable) {
                    Toast.makeText(this@HomeActivity, "인터넷에 연결 되어있는지 확인 해주세요", Toast.LENGTH_SHORT)
                        .show()
                }

            })
    }

    fun logout() {
        prefs.remove()
        Intent(this, LoginActivity::class.java).also {
            it.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
            startActivity(it)
        }
        finish()
    }

    fun breakfast(mealData: List<String>) {
        val nowTime = LocalTime.now()
        with(binding) {
            for (data in mealData) {
                if (data == "breakfast") {
                    breakfastCheak.setBackgroundColor(Color.parseColor("#ACEEB4"))
                    breakfastCheak.setBackgroundResource(R.drawable.cardview)
                } else if (breakfastEndTime < nowTime) {
                    breakfastCheak.setBackgroundColor(Color.parseColor("#FF8383"))
                    breakfastCheak.setBackgroundResource(R.drawable.cardview)
                } else {
                    breakfastCheak.setBackgroundColor(Color.parseColor("#D9D9D9"))
                    breakfastCheak.setBackgroundResource(R.drawable.cardview)
                }
            }
        }
    }

    fun lunch(mealData: List<String>) {
        val nowTime = LocalTime.now()
        with(binding) {
            for (data in mealData) {
                if (data == "lunch") {
                    lunchCheak.setBackgroundColor(Color.parseColor("#ACEEB4"))
                    lunchCheak.setBackgroundResource(R.drawable.cardview)
                } else if (lunchEndTime < nowTime) {
                    lunchCheak.setBackgroundColor(Color.parseColor("#FF8383"))
                    lunchCheak.setBackgroundResource(R.drawable.cardview)
                } else {
                    lunchCheak.setBackgroundColor(Color.parseColor("#D9D9D9"))
                    lunchCheak.setBackgroundResource(R.drawable.cardview)
                }
            }
        }
    }

    fun dinner(mealData: List<String>) {
        val nowTime = LocalTime.now()
        with(binding) {
            for (data in mealData) {
                if (data == "dinner") {
                    dinnerCheak.setBackgroundColor(Color.parseColor("#ACEEB4"))
                    dinnerCheak.setBackgroundResource(R.drawable.cardview)
                } else if (dinnerEndTime < nowTime) {
                    dinnerCheak.setBackgroundColor(Color.parseColor("#FF8383"))
                    dinnerCheak.setBackgroundResource(R.drawable.cardview)
                } else {
                    dinnerCheak.setBackgroundColor(Color.parseColor("#D9D9D9"))
                    dinnerCheak.setBackgroundResource(R.drawable.cardview)
                }
            }
        }

    }
}
