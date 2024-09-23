package com.alt.letseatingtime_android.ui.fragment.store

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import coil.load
import com.alt.letseatingtime.R
import com.alt.letseatingtime.databinding.FragmentGoodsBuyBinding
import com.alt.letseatingtime_android.ui.viewmodel.StoreViewModel
import com.alt.letseatingtime_android.util.BottomController

class GoodsBuyFragment : Fragment() {
    private var _binding: FragmentGoodsBuyBinding? = null
    private val binding get() = _binding!!
    private val viewModel by activityViewModels<StoreViewModel>()

    private var quantity = 1  // 기본 수량
    private var pricePerUnit = 0  // 단위당 가격

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGoodsBuyBinding.inflate(inflater, container, false)
        (requireActivity() as BottomController).setBottomNavVisibility(false)

        pricePerUnit = viewModel.goodsData.value?.price ?: 0

        with(binding) {
            tvGoodsName.text = viewModel.goodsData.value?.productName
            updatePrice()

            ibBackButton.setOnClickListener {
                requireActivity().supportFragmentManager.popBackStack()
            }

            btnIncrement.setOnClickListener {
                quantity++
                tvQuantity.text = quantity.toString()
                updatePrice()
            }

            btnDecrement.setOnClickListener {
                if (quantity > 1) {
                    quantity--
                    tvQuantity.text = quantity.toString()
                    updatePrice()
                }
            }

            btnSubmit.setOnClickListener {
                // 주문 제출 로직
            }
        }

        return binding.root
    }

    private fun updatePrice() {
        val totalPrice = pricePerUnit * quantity
        binding.tvPrice.text = totalPrice.toString()  // 총 가격 설정
    }

    override fun onPause() {
        super.onPause()
        (requireActivity() as BottomController).setBottomNavVisibility(true)
    }
}
