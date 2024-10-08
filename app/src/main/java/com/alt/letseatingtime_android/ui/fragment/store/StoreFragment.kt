package com.alt.letseatingtime_android.ui.fragment.store

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.alt.letseatingtime.R
import com.alt.letseatingtime.databinding.FragmentStoreBinding
import com.alt.letseatingtime_android.network.retrofit.response.goods.StoreResponse
import com.alt.letseatingtime_android.ui.adapter.store.StoreGoods1Adapter
import com.alt.letseatingtime_android.ui.adapter.store.StoreGoods2Adapter
import com.alt.letseatingtime_android.ui.adapter.store.storedata.GoodsItem
import com.alt.letseatingtime_android.ui.viewmodel.StoreViewModel
import com.alt.letseatingtime_android.util.shortToast


class StoreFragment : Fragment() {
    var _binding: FragmentStoreBinding? = null
    private val goodsViewModel by activityViewModels<StoreViewModel>()

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStoreBinding.inflate(inflater, container, false)
        goodsViewModel.getGoods()

        goodsViewModel.goodsDataList.observe(viewLifecycleOwner){
            with(binding) {
                rvForUserItems.adapter = StoreGoods1Adapter(it) { position ->
                    moveScreen(it[position])
                }

                rvUserItems.adapter = StoreGoods2Adapter(it) { position ->
                    moveScreen(it[position])
                }
            }
        }

        goodsViewModel.toastMessage.observe(viewLifecycleOwner){
            requireContext().shortToast(it)
        }


        return binding.root
    }

    private fun moveScreen(deliver:StoreResponse) {
        goodsViewModel.setGoodsData(deliver)
        findNavController().navigate(R.id.action_storeFragment2_to_goodsBuyFragment2)
    }

}