package com.alt.letseatingtime_android.ui.fragment.store

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import coil.load
import com.alt.letseatingtime.R
import com.alt.letseatingtime.databinding.FragmentGoodsBuyBinding
import com.alt.letseatingtime_android.ui.adapter.store.storedata.GoodsItem
import com.alt.letseatingtime_android.ui.viewmodel.StoreViewModel
import com.alt.letseatingtime_android.util.BottomController

class GoodsBuyFragment : Fragment() {
    private var _binding: FragmentGoodsBuyBinding? = null
    private val binding get() = _binding!!
    private val viewModel by activityViewModels<StoreViewModel>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGoodsBuyBinding.inflate(inflater, container, false)
        (requireActivity() as BottomController).setBottomNavVisibility(false)
        with(binding) {
            tvGoodsName.text = viewModel.goodsData.value?.productName
            tvPrice.text = viewModel.goodsData.value?.price.toString()
//            ivGoodsImage.load(viewModel.goodsData.value?)

            ibBackButton.setOnClickListener {
                requireActivity().supportFragmentManager.popBackStack()

            }
        }
        return binding.root
    }
    override fun onPause() {
        super.onPause()
        (requireActivity() as BottomController).setBottomNavVisibility(true)
    }
}