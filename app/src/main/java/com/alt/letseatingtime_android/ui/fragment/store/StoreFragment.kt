package com.alt.letseatingtime_android.ui.fragment.store

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.alt.letseatingtime.R
import com.alt.letseatingtime.databinding.FragmentStoreBinding
import com.alt.letseatingtime_android.MyApplication
import com.alt.letseatingtime_android.network.retrofit.RetrofitClient
import com.alt.letseatingtime_android.network.retrofit.response.goods.StoreResponse
import com.alt.letseatingtime_android.network.retrofit.response.profile.ProfileResponse
import com.alt.letseatingtime_android.ui.adapter.store.StoreGoods1Adapter
import com.alt.letseatingtime_android.ui.adapter.store.StoreGoods2Adapter
import com.alt.letseatingtime_android.ui.adapter.store.storedata.GoodsItem
import com.alt.letseatingtime_android.ui.viewmodel.StoreViewModel
import com.alt.letseatingtime_android.util.shortToast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class StoreFragment : Fragment() {
    var _binding: FragmentStoreBinding? = null
    private val viewModel by activityViewModels<StoreViewModel>()

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStoreBinding.inflate(inflater, container, false)
        viewModel.getGoods()

        initProfile()

        viewModel.goodsDataList.observe(viewLifecycleOwner){
            with(binding) {
                rvForUserItems.adapter = StoreGoods1Adapter(it) { position ->
                    moveScreen(it[position])
                }

                rvUserItems.adapter = StoreGoods2Adapter(it) { position ->
                    moveScreen(it[position])
                }
            }
        }


        return binding.root
    }

    private fun moveScreen(deliver:StoreResponse) {
        viewModel.setGoodsData(deliver)
        findNavController().navigate(R.id.action_storeFragment2_to_goodsBuyFragment2)
    }

    fun initProfile(){
        RetrofitClient.api.profile("Bearer " + MyApplication.prefs.accessToken).enqueue(object  :
            Callback<ProfileResponse> {
            @SuppressLint("SetTextI18n")
            override fun onResponse(
                call: Call<ProfileResponse>,
                response: Response<ProfileResponse>
            ) {
                val result = response.body()
                if (response.isSuccessful) {
                    binding.tvForUser.text = "${result?.data?.user?.name}님을 위한 추천"
                }
                else{
                    Log.e("HomeFragment", "${response.errorBody().toString()}, ${response.code()}, ${response.body()}, ${response.message()}")
                    context?.shortToast("프로필 정보를 가져오지 못했습니다.")
                }
            }

            override fun onFailure(call: Call<ProfileResponse>, t: Throwable) {
                Log.e("server error", t.stackTraceToString())
                context?.shortToast("서버 에러")
            }
        })
    }
}