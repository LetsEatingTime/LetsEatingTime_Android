package com.alt.letseatingtime_android.ui.fragment.home

import android.icu.util.Calendar
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.alt.letseatingtime.R
import com.alt.letseatingtime.databinding.FragmentHomeBinding
import com.alt.letseatingtime_android.network.retrofit.RetrofitClient
import com.alt.letseatingtime_android.network.retrofit.response.meal.MealResponse
import com.alt.letseatingtime_android.ui.adapter.meal.MealViewPagerAdapter
import com.alt.letseatingtime_android.ui.adapter.store.StoreGoods1Adapter
import com.alt.letseatingtime_android.ui.viewmodel.StoreViewModel
import com.alt.letseatingtime_android.util.BottomController
import com.alt.letseatingtime_android.util.OnSingleClickListener
import com.alt.letseatingtime_android.util.shortToast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.GregorianCalendar

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var mealAdapter: MealViewPagerAdapter
    private val gregorianCalendar = GregorianCalendar()
    private val goodsViewModel  by activityViewModels<StoreViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        (requireActivity() as BottomController).setBottomNavVisibility(true)

        //오늘 아침 00:00 ~ 8:29  |  내일 아침 19:10 ~ 23:59
        //점심 8:30 ~ 13:29
        //저녁 13:30 ~ 19:09

        goodsViewModel.getGoods()

        goodsViewModel.goodsDataList.observe(viewLifecycleOwner){
            with(binding) {
                rvForUserItems.adapter = StoreGoods1Adapter(it) { position ->
                    goodsViewModel.setGoodsData(it[position])
                    findNavController().navigate(R.id.action_homeFragment2_to_goodsBuyFragment2)
                }
            }
        }


        binding.cbtnScan.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment2_to_scanFragment)
        }


        val time = (LocalDateTime.now().hour * 60) + LocalDateTime.now().minute

        Log.d(
            "test",
            "h : ${LocalDateTime.now().hour},\nm : ${LocalDateTime.now().minute},\ns : ${LocalDateTime.now().second},\ntime : $time"
        )
        var day: String = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"))
        var position = 0
        when (time) {
            in 510..809 -> { // 점심
                Log.d("test", "today l")
                position = 1
            }
            in 810..1149 -> { // 저녁
                Log.d("test", "today d")
                position = 2
            }
            in 1150..1439 -> {// 내일 아침
                Log.d("test", "next day b")
                val year = gregorianCalendar.get(Calendar.YEAR)
                val today = gregorianCalendar.get(Calendar.DATE)
                val month = gregorianCalendar.get(Calendar.MONTH)
                day = String.format("%4d%02d%02d", year, month + 1, today + 1)
            }
            else -> {// 오늘 아침
                Log.d("test", "today b")
                position = 0
            }
        }
        initRecyclerview(day, position)
        binding.clvMealMore.setOnClickListener(OnSingleClickListener {
            findNavController().navigate(R.id.action_homeFragment2_to_mealListFragment)
        })



        return binding.root
    }

    // TODO : 시간받고, position에 값 넣기
    fun setMeal(data: MealResponse, position : Int) {


        mealAdapter = MealViewPagerAdapter(
            todayMealDateList = listOf(
                data.data.breakfast.menu.joinToString(", ", "", ""),
                data.data.lunch.menu.joinToString(", ", "", ""),
                data.data.dinner.menu.joinToString(", ", "", "")
            ),
            position = position
        )
        mealAdapter.notifyItemRemoved(0)

        with(binding) {
            vp2TodayMeal.adapter = mealAdapter
            vp2TodayMeal.orientation = ViewPager2.ORIENTATION_HORIZONTAL
            vp2TodayMeal.setCurrentItem(1, false)
        }


    }

    private fun initRecyclerview(date: String, position : Int) {
        RetrofitClient.api.meal(date = date)
            .enqueue(object : Callback<MealResponse> {
                override fun onResponse(
                    call: Call<MealResponse>,
                    response: Response<MealResponse>
                ) {
                    val result = response.body()
                    if (response.isSuccessful) {
                        Log.d(requireActivity().packageName, "내용 : ${result}")
                        setMeal(result!!, position)
                    } else {
                        context?.shortToast("데이터를 불러오지 못했습니다.")
                    }
                }

                override fun onFailure(call: Call<MealResponse>, t: Throwable) {
                    context?.shortToast("서버 애러")
                    t.printStackTrace()
                }
            })
    }
}

//    private val binding: ActivityHomeBinding by lazy { ActivityHomeBinding.inflate(layoutInflater) }
//    private lateinit var bottomNavigationView: BottomNavigationView
//
//    private val bottomNavFragments = listOf(
//        StoreFragment(),
//        HomeFragment(),
//        ProfileFragment()
//    )
//
//    private var recentPosition = 1
//
//    private val lunchStartTime = LocalTime.of(9, 0)
//    private val dinnerStartTime = LocalTime.of(14, 0)
//    private val breakfastEndTime = LocalTime.of(8, 20)
//    private val lunchEndTime = LocalTime.of(13, 20)
//    private val dinnerEndTime = LocalTime.of(19, 10)
//
//    private val viewModel by lazy {
//        ViewModelProvider(this)[UserActivityViewModel::class.java]
//    }
//
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(binding.root)
//        bottomTabBar()
//        getProfile()
//        getMeal()
//        cheakMeal()
//
//        binding.logout.setOnClickListener {
//            prefs.remove()
//            Toast.makeText(this, "로그아웃", Toast.LENGTH_SHORT).show()
//            Intent(this, LoginActivity::class.java).also {
//                startActivity(it)
//            }
//            finish()
//        }
//
//        binding.withdrawal.setOnClickListener {
//            val builder = AlertDialog.Builder(this)
//            val dialog = builder.create()
//            dialog.setOnShowListener {
//                dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.RED)
//                dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.BLACK)
//            }
//
//            builder.setTitle("경고").setMessage("정말로 탈퇴하시겠습니까?").setPositiveButton(
//                "예"
//            ) { _, _ ->
//                withdrawal()
//            }.setNegativeButton(
//                "아니요"
//            ) { _, _ ->
//            }
//            builder.show()
//        }
//    }
//
//    private fun bottomTabBar() {
//        bottomNavigationView = binding.bottomNav
//
//        supportFragmentManager.beginTransaction()
//            .replace(binding.homeFrameContainer.id, bottomNavFragments[1])
//            .commit()
//
//        bottomNavigationView.selectedItemId = R.id.homeFragment
//
//        bottomNavigationView.setOnItemSelectedListener {
//            val transaction = supportFragmentManager.beginTransaction()
//            when (it.itemId) {
//                R.id.storeFragment -> {
//                    transaction.setCustomAnimations(
//                        R.anim.anim_slide_in_from_left_fade_in,
//                        R.anim.anim_fade_out_200
//                    )
//                    transaction.replace(binding.homeFrameContainer.id, bottomNavFragments[0])
//                    transaction.commit()
//                    recentPosition = 0
//                    return@setOnItemSelectedListener true
//                }
//                R.id.homeFragment -> {
//                    if (recentPosition < 1) {
//                        transaction.setCustomAnimations(
//                            R.anim.anim_slide_in_from_right_fade_in,
//                            R.anim.anim_fade_out_200
//                        )
//                    } else {
//                        transaction.setCustomAnimations(
//                            R.anim.anim_slide_in_from_left_fade_in,
//                            R.anim.anim_fade_out_200
//                        )
//                    }
//                    transaction.replace(binding.homeFrameContainer.id, bottomNavFragments[1])
//                    transaction.commit()
//                    recentPosition = 1
//                    return@setOnItemSelectedListener true
//                }
//                R.id.profileFragment -> {
//                    transaction.setCustomAnimations(
//                        R.anim.anim_slide_in_from_right_fade_in,
//                        R.anim.anim_fade_out_200
//                    )
//                    transaction.replace(binding.homeFrameContainer.id, bottomNavFragments[2])
//                    transaction.commit()
//                    recentPosition = 4
//                    return@setOnItemSelectedListener true
//                }
//            }
//            return@setOnItemSelectedListener false
//        }
//    }
//
//    private fun getImg(id: String) {
//        api.image(prefs.accessToken, id).enqueue(object : Callback<ResponseBody> {
//            override fun onResponse(
//                call: Call<ResponseBody>, response: Response<ResponseBody>
//            ) {
//                val photoBytes = response.body()?.bytes()
//                val image = getBitmapFromBytes(photoBytes)
//                Glide.with(this@HomeActivity).load(image)
//                    .error(AppCompatResources.getDrawable(this@HomeActivity, R.drawable.profile_image_background))
//                    .apply(RequestOptions.bitmapTransform(RoundedCorners(10)))
//                    .into(binding.studentImage)
//            }
//
//            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
//                Toast.makeText(this@HomeActivity, "서버 애러", Toast.LENGTH_SHORT).show()
//            }
//        })
//    }
//
//    fun getBitmapFromBytes(byteArray: ByteArray?): Bitmap? {
//        return if (byteArray != null) {
//            BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
//        } else {
//            null
//        }
//    }
//
//
//    private fun getProfile() {
//        api.profile("Bearer ${prefs.accessToken}").enqueue(object : Callback<ProfileResponse> {
//            override fun onResponse(
//                call: Call<ProfileResponse>, response: Response<ProfileResponse>
//            ) {
//                if (response.code() == 200) {
//                    val user = response.body()?.data?.user
//                    val now = LocalDate.now()
//                    val strnow = now.format(DateTimeFormatter.ofPattern("yyyy/MM/dd"))
//                    with(binding) {
//                        nameId.text = user?.name
////                            trashId.text = "${user?.grade}학년 ${user?.className}반 ${user?.classNo}번"
//                        trashId.text = getString(com.alt.letseatingtime.R.string.trash_id, user?.grade,user?.className,user?.classNo)
//                        today.text = strnow
//                    }
//                    getImg(user?.image.toString())
//                } else {
//                    viewModel.refreshToken()
//                    getProfile()
//                }
//            }
//
//            override fun onFailure(call: Call<ProfileResponse>, t: Throwable) {
//                Toast.makeText(this@HomeActivity, "인터넷에 연결 되어있는지 확인 해주세요", Toast.LENGTH_SHORT)
//                    .show()
//            }
//
//        })
//    }
//
//    private fun getMeal() {
//        val current = LocalDateTime.now()
//        val formatter = DateTimeFormatter.ofPattern("yyyyMMdd")
//        val currentTime = current.format(formatter)
//
//        val currentTimeOfDay = current.toLocalTime()
//
//        val mealType: String = when {
//            currentTimeOfDay.isBefore(lunchStartTime) -> "breakfast"
//            currentTimeOfDay.isBefore(dinnerStartTime) -> "lunch"
//            else -> "dinner"
//        }
//
//
//        api.meal(currentTime).enqueue(object : Callback<MealResponse> {
//            override fun onResponse(
//                call: Call<MealResponse>, response: Response<MealResponse>
//            ) {
//                if (response.code() == 200) {
//                    val data = response.body()?.data
//
//                    binding.mealType.text = when (mealType) {
//                        "breakfast" -> "조식"
//                        "lunch" -> "중식"
//                        else -> "석식"
//                    }
//
//                    val menu: String? = arrayToString(
//                        when (mealType) {
//                            "breakfast" -> data?.breakfast?.menu
//                            "lunch" -> data?.lunch?.menu
//                            else -> data?.dinner?.menu
//                        }
//                    )
//                    binding.mealMenu.text = menu ?: when (mealType) {
//                        "breakfast" -> "아침이 없습니다."
//                        "lunch" -> "점심이 없습니다."
//                        else -> "저녁이 없습니다."
//                    }
//                } else {
//                    Toast.makeText(this@HomeActivity, "급식 불러오는데 실패하였습니다", Toast.LENGTH_SHORT).show()
//                }
//            }
//
//
//            override fun onFailure(call: Call<MealResponse>, t: Throwable) {
//                Toast.makeText(this@HomeActivity, "인터넷에 연결 되어있는지 확인 해주세요", Toast.LENGTH_SHORT)
//                    .show()
//            }
//        })
//    }
//
//    /**
//     * 문자열 배열을 줄내림 넣어 문자열로 반환
//     *
//     * @param List<String>?
//     * @retrun String?
//     */
//    val arrayToString: (List<String>?) -> String? = { aStr ->
//        var newStr = ""
//
//        if (aStr != null) {
//            for (i: Int in aStr.indices) {
//                newStr += aStr[i].removePrefix("*")
//                if (i < aStr.size - 1) newStr += "\n"
//            }
//        }
//        newStr
//    }
//
//    private fun cheakMeal() {
//        api.profile("Bearer ${prefs.accessToken}").enqueue(object : Callback<ProfileResponse> {
//            override fun onResponse(
//                call: Call<ProfileResponse>, response: Response<ProfileResponse>
//            ) {
//                if (response.isSuccessful) {
//                    val mealData = response.body()?.data?.mealTime
//                    if (mealData != null) {
//                        breakfast(mealData)
//                        lunch(mealData)
//                        dinner(mealData)
//                    } else {
//                        with(binding) {
//                            breakfastCheak.setBackgroundColor(android.graphics.Color.parseColor("#D9D9D9"))
//                            lunchCheak.setBackgroundColor(android.graphics.Color.parseColor("#D9D9D9"))
//                            dinnerCheak.setBackgroundColor(android.graphics.Color.parseColor("#D9D9D9"))
//
//                            breakfastCheak.setBackgroundResource(com.alt.letseatingtime.R.drawable.cardview)
//                            lunchCheak.setBackgroundResource(com.alt.letseatingtime.R.drawable.cardview)
//                            dinnerCheak.setBackgroundResource(com.alt.letseatingtime.R.drawable.cardview)
//                        }
//                    }
//                } else {
//                    Toast.makeText(
//                        this@HomeActivity, "애러: ${response.code()}", Toast.LENGTH_SHORT
//                    ).show()
//                }
//            }
//
//            override fun onFailure(call: Call<ProfileResponse>, t: Throwable) {
//                Toast.makeText(this@HomeActivity, "인터넷에 연결 되어있는지 확인 해주세요", Toast.LENGTH_SHORT)
//                    .show()
//            }
//
//        })
//    }
//
//
//    private fun withdrawal() {
//        api.withdraw("Bearer ${prefs.accessToken}").enqueue(object : Callback<WithdrawResponse> {
//            override fun onResponse(
//                call: Call<WithdrawResponse>, response: Response<WithdrawResponse>
//            ) {
//                if (response.isSuccessful) {
//                    logout()
//                }
//            }
//
//            override fun onFailure(call: Call<WithdrawResponse>, t: Throwable) {
//                Toast.makeText(this@HomeActivity, "인터넷에 연결 되어있는지 확인 해주세요", Toast.LENGTH_SHORT)
//                    .show()
//            }
//
//        })
//    }
//
//    fun logout() {
//        prefs.remove()
//        Intent(this, LoginActivity::class.java).also {
//            it.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
//            startActivity(it)
//        }
//        finish()
//    }
//
//    fun breakfast(mealData: List<String>) {
//        val nowTime = LocalTime.now()
//        with(binding) {
//            for (data in mealData) {
//                if (data == "breakfast") {
//                    breakfastCheak.setBackgroundColor(android.graphics.Color.parseColor("#ACEEB4"))
//                    breakfastCheak.setBackgroundResource(com.alt.letseatingtime.R.drawable.cardview)
//                } else if (breakfastEndTime < nowTime) {
//                    breakfastCheak.setBackgroundColor(android.graphics.Color.parseColor("#FF8383"))
//                    breakfastCheak.setBackgroundResource(com.alt.letseatingtime.R.drawable.cardview)
//                } else {
//                    breakfastCheak.setBackgroundColor(android.graphics.Color.parseColor("#D9D9D9"))
//                    breakfastCheak.setBackgroundResource(com.alt.letseatingtime.R.drawable.cardview)
//                }
//            }
//        }
//    }
//
//    fun lunch(mealData: List<String>) {
//        val nowTime = LocalTime.now()
//        with(binding) {
//            for (data in mealData) {
//                if (data == "lunch") {
//                    lunchCheak.setBackgroundColor(android.graphics.Color.parseColor("#ACEEB4"))
//                    lunchCheak.setBackgroundResource(com.alt.letseatingtime.R.drawable.cardview)
//                } else if (lunchEndTime < nowTime) {
//                    lunchCheak.setBackgroundColor(android.graphics.Color.parseColor("#FF8383"))
//                    lunchCheak.setBackgroundResource(com.alt.letseatingtime.R.drawable.cardview)
//                } else {
//                    lunchCheak.setBackgroundColor(android.graphics.Color.parseColor("#D9D9D9"))
//                    lunchCheak.setBackgroundResource(com.alt.letseatingtime.R.drawable.cardview)
//                }
//            }
//        }
//    }
//
//    fun dinner(mealData: List<String>) {
//        val nowTime = LocalTime.now()
//        with(binding) {
//            for (data in mealData) {
//                if (data == "dinner") {
//                    dinnerCheak.setBackgroundColor(android.graphics.Color.parseColor("#ACEEB4"))
//                    dinnerCheak.setBackgroundResource(com.alt.letseatingtime.R.drawable.cardview)
//                } else if (dinnerEndTime < nowTime) {
//                    dinnerCheak.setBackgroundColor(android.graphics.Color.parseColor("#FF8383"))
//                    dinnerCheak.setBackgroundResource(com.alt.letseatingtime.R.drawable.cardview)
//                } else {
//                    dinnerCheak.setBackgroundColor(android.graphics.Color.parseColor("#D9D9D9"))
//                    dinnerCheak.setBackgroundResource(com.alt.letseatingtime.R.drawable.cardview)
//                }
//            }
//        }
//
//    }
//}