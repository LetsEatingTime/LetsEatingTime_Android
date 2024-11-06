package com.alt.letseatingtime_android.ui.fragment.store

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
import com.alt.letseatingtime_android.network.retrofit.response.ImageResponse
import com.alt.letseatingtime_android.network.retrofit.response.goods.StoreResponse
import com.alt.letseatingtime_android.ui.adapter.store.StoreDecoration1
import com.alt.letseatingtime_android.ui.adapter.store.StoreGoods1Adapter
import com.alt.letseatingtime_android.ui.adapter.store.StoreGoods2Adapter
import com.alt.letseatingtime_android.ui.adapter.store.storedata.GoodsItem
import com.alt.letseatingtime_android.ui.viewmodel.StoreViewModel
import com.alt.letseatingtime_android.ui.viewmodel.UserActivityViewModel
import com.alt.letseatingtime_android.util.shortToast


class StoreFragment : Fragment() {
    var _binding: FragmentStoreBinding? = null
    private val goodsViewModel by activityViewModels<StoreViewModel>()
    private val profileViewModel by activityViewModels<UserActivityViewModel>()

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStoreBinding.inflate(inflater, container, false)
        goodsViewModel.getGoods()
        goodsViewModel.getMyOrderList()

        binding.tvItems.text = "포인트로 구매가능한 상품"

        profileViewModel.userData.observe(viewLifecycleOwner){
            binding.tvForUser.text = "${it.data.user.name}님을 위한 추천"
        }

//        goodsViewModel.goodsDataList.observe(viewLifecycleOwner){
//            with(binding) {
//                rvForUserItems.adapter = StoreGoods1Adapter(it, goodsViewModel.productImageList.value ?: mapOf()) { position ->
//                    moveScreen(it[position])
//                }
//
//                rvUserItems.adapter = StoreGoods2Adapter(it) { position ->
//                    moveScreen(it[position])
//                }
//            }
//        }

        goodsViewModel.productImageList.observe(viewLifecycleOwner) {
            with(binding) {
                rvForUserItems.adapter = StoreGoods1Adapter(
                    goodsViewModel.myOrderList.value ?: listOf(),
                    imageList =  it
                ) { position ->
                    goodsViewModel.goodsDataList.value?.get(position)
                        ?.let { it1 -> moveScreen(it1) }
                }
                if (rvForUserItems.itemDecorationCount == 0) {
                    rvForUserItems.addItemDecoration(
                        StoreDecoration1(
                            lastIndex = goodsViewModel.goodsDataList.value?.size ?: 0,
                            startPadding = 9,
                            endPadding = 9
                        )
                    )
                }

                rvUserItems.adapter = StoreGoods2Adapter(
                    itemList = goodsViewModel.goodsDataList.value ?: listOf(),
                    imageList = it
                ) { position ->
                    goodsViewModel.goodsDataList.value?.get(position)
                        ?.let { it1 -> moveScreen(it1) }
                }
            }
        }

        goodsViewModel.toastMessage.observe(viewLifecycleOwner) {
            requireContext().shortToast(it)
        }


        return binding.root
    }

    private fun moveScreen(deliver: StoreResponse) {
        goodsViewModel.setGoodsData(deliver)
        findNavController().navigate(R.id.action_storeFragment2_to_goodsBuyFragment2)
    }

}