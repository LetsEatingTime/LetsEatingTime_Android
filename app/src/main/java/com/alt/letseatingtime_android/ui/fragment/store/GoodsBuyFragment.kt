package com.alt.letseatingtime_android.ui.fragment.store

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import coil.load
import com.alt.letseatingtime.databinding.FragmentGoodsBuyBinding
import com.alt.letseatingtime_android.ui.viewmodel.StoreViewModel
import com.alt.letseatingtime_android.util.BottomController
import com.alt.letseatingtime_android.util.shortToast
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class GoodsBuyFragment : Fragment() {
    private var _binding: FragmentGoodsBuyBinding? = null
    private val binding get() = _binding!!
    private val goodsViewModel by activityViewModels<StoreViewModel>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGoodsBuyBinding.inflate(inflater, container, false)
        (requireActivity() as BottomController).setBottomNavVisibility(false)
        goodsViewModel.goodsData.observe(viewLifecycleOwner){ goodsViewModel ->
            with(binding) {
                tvGoodsName.text = goodsViewModel.productName
                tvPrice.text = goodsViewModel.price.toString()
                tvStock.text = goodsViewModel.stock.toString()
                tvGrantTime.text =
                    formatDateToYYMMDD(goodsViewModel?.grantTime ?: "2014-12-12 07:07:07")
                ibBackButton.setOnClickListener {
                    requireActivity().supportFragmentManager.popBackStack()
                }
            }
        }


        goodsViewModel.toastMessage.observe(viewLifecycleOwner) {
            if (it != "") {
                requireContext().shortToast(it)
            }
        }

        goodsViewModel.productImageList.observe(viewLifecycleOwner) {
            binding.ivGoodsImage.load(it[goodsViewModel.goodsData.value?.idx]?.fileName ?: "")
        }
        return binding.root
    }

    override fun onPause() {
        super.onPause()
        (requireActivity() as BottomController).setBottomNavVisibility(true)
    }

    private fun formatDateToYYMMDD(dateString: String): String {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        val outputFormat = SimpleDateFormat("yy.MM.dd", Locale.getDefault())

        val date = inputFormat.parse(dateString)
        return outputFormat.format(date as Date) // 형식화된 문자열 반환
    }
}