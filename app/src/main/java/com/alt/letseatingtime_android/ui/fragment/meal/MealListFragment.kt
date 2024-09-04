package com.alt.letseatingtime_android.ui.fragment.meal

import android.icu.util.Calendar
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.alt.letseatingtime.R
import com.alt.letseatingtime.databinding.FragmentMealListBinding
import com.alt.letseatingtime_android.network.retrofit.RetrofitClient
import com.alt.letseatingtime_android.network.retrofit.response.meal.MealResponse
import com.alt.letseatingtime_android.ui.adapter.meal.MealRecyclerViewAdapter
import com.alt.letseatingtime_android.util.shortToast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.GregorianCalendar

class MealListFragment : Fragment() {

    private lateinit var binding: FragmentMealListBinding

    private val today : String = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"))

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMealListBinding.inflate(inflater, container, false)

        initRecyclerview(today)

        binding.ivBackButton.setOnClickListener{
            findNavController().navigate(R.id.action_mealListFragment_to_homeFragment2)
        }

        return binding.root
    }

    private fun initRecyclerview(date : String) {
        RetrofitClient.api.meal(date = date)
            .enqueue(object : Callback<MealResponse> {
                override fun onResponse(
                    call: Call<MealResponse>,
                    response: Response<MealResponse>
                ) {
                    val result = response.body()
                    if (response.isSuccessful) {
                        Log.d(requireActivity().packageName, "내용 : ${result}")
                        val adapter =  MealRecyclerViewAdapter(mealList = listOf(result!!), mealDate = date)
                        binding.mealRecyclerview.adapter = adapter
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