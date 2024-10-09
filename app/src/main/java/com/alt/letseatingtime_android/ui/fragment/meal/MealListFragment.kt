package com.alt.letseatingtime_android.ui.fragment.meal

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.alt.letseatingtime.R
import com.alt.letseatingtime.databinding.FragmentMealListBinding
import com.alt.letseatingtime_android.network.retrofit.RetrofitClient
import com.alt.letseatingtime_android.network.retrofit.response.meal.MealResponse
import com.alt.letseatingtime_android.ui.adapter.meal.MealRecyclerViewAdapter
import com.alt.letseatingtime_android.util.BottomController
import com.alt.letseatingtime_android.util.shortToast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class MealListFragment : Fragment() {

    private lateinit var binding: FragmentMealListBinding

    private lateinit var mealRecyclerAdapter: MealRecyclerViewAdapter

    val mealList = mutableListOf<MealResponse>()
    val mealDateList = mutableListOf<String>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMealListBinding.inflate(inflater, container, false)
        (requireActivity() as BottomController).setBottomNavVisibility(false)

        initRecyclerview()

        binding.ivBackButton.setOnClickListener {
            findNavController().navigate(R.id.action_mealListFragment_to_homeFragment2)
        }

        return binding.root
    }

    private fun initRecyclerview() {
        val now = LocalDateTime.now()
        for (i in 1..8) {
            RetrofitClient.api.meal(
                date = now.plusDays(i.toLong()).format(DateTimeFormatter.ofPattern("yyyyMMdd"))
            )
                .enqueue(object : Callback<MealResponse> {
                    override fun onResponse(
                        call: Call<MealResponse>,
                        response: Response<MealResponse>
                    ) {
                        val result = response.body()
                        if (response.isSuccessful) {
                            if (result != null) {
                                saveMeal(result)
                                mealDateList.add(now.plusDays(i.toLong()).format(DateTimeFormatter.ofPattern("yyyyMMdd")))
                                mealRecyclerAdapter = MealRecyclerViewAdapter(
                                    mealList = mealList,
                                    mealDateList = mealDateList
                                )
                                mealRecyclerAdapter.notifyItemRemoved(0)
                                binding.mealRecyclerview.adapter = mealRecyclerAdapter
                            }
                        } else {
                            context?.shortToast("데이터를 불러오지 못했습니다.")
                        }
                    }

                    override fun onFailure(call: Call<MealResponse>, t: Throwable) {
                        context?.shortToast("서버 애러")
                        t.printStackTrace()
                        if (i == 7) {
                            mealRecyclerAdapter = MealRecyclerViewAdapter(
                                mealList = mealList,
                                mealDateList = mealDateList
                            )
                            binding.mealRecyclerview.adapter = mealRecyclerAdapter
                        }
                    }
                })
        }

//        RetrofitClient.api.meal(date = date)
//            .enqueue(object : Callback<MealResponse> {
//                override fun onResponse(
//                    call: Call<MealResponse>,
//                    response: Response<MealResponse>
//                ) {
//                    val result = response.body()
//                    if (response.isSuccessful) {
//                        Log.d(requireActivity().packageName, "내용 : ${result}")
//                        mealRecyclerAdapter =
//                            MealRecyclerViewAdapter(mealList = listOf(result!!), mealDate = date)
//                        binding.mealRecyclerview.adapter = mealRecyclerAdapter
//                    } else {
//                        context?.shortToast("데이터를 불러오지 못했습니다.")
//                    }
//                }
//
//                override fun onFailure(call: Call<MealResponse>, t: Throwable) {
//                    context?.shortToast("서버 애러")
//                    t.printStackTrace()
//                }
//            })
    }

    private fun saveMeal(result: MealResponse) {
        mealList.add(result)
    }

    override fun onPause() {
        super.onPause()
        (requireActivity() as BottomController).setBottomNavVisibility(true)
    }
}