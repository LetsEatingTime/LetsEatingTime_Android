package com.alt.letseatingtime_android.ui.fragment.store

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import com.alt.letseatingtime.databinding.FragmentGoodsBuyBinding
import com.alt.letseatingtime_android.MyApplication
import com.alt.letseatingtime_android.ui.viewmodel.StoreViewModel
import com.alt.letseatingtime_android.util.BottomController
import com.alt.letseatingtime_android.network.retrofit.RetrofitClient
import com.alt.letseatingtime_android.ui.adapter.store.storedata.CreateOrderRequest
import com.alt.letseatingtime_android.ui.adapter.store.storedata.CreateOrderResponse
import com.alt.letseatingtime_android.ui.adapter.store.storedata.Order
import com.alt.letseatingtime_android.ui.adapter.store.storedata.OrderDetail
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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

        // 가격 및 상품 정보 초기화
        pricePerUnit = viewModel.goodsData.value?.price ?: 0
        val productId = viewModel.goodsData.value?.idx ?: 0L  // productId로 사용할 idx

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
                sendOrderToServer(productId)  // productId를 주문 생성에 사용
            }
        }

        return binding.root
    }

    private fun updatePrice() {
        val totalPrice = pricePerUnit * quantity
        binding.tvPrice.text = totalPrice.toString()  // 총 가격 설정
    }

    private fun sendOrderToServer(productId: Long) {
        val token = "Bearer " + MyApplication.prefs.accessToken

        // Order 및 OrderDetail 객체 생성
        val order = Order(total = quantity)
        val orderDetails = listOf(
            OrderDetail(
                productId = productId,  // 서버에서 받은 productId 사용
                quantity = quantity,  // 선택된 수량
                price = pricePerUnit  // 단가
            )
        )

        val request = CreateOrderRequest(order, orderDetails)

        RetrofitClient.api.createOrder(token, request)
            .enqueue(object : Callback<CreateOrderResponse> {
                override fun onResponse(
                    call: Call<CreateOrderResponse>,
                    response: Response<CreateOrderResponse>
                ) {
                    if (response.isSuccessful && response.body()?.status == 200) {
                        val orderData = response.body()?.data
                        Toast.makeText(requireContext(), "주문 성공: 주문 번호 ${orderData?.idx}", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(requireContext(), "주문 실패", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<CreateOrderResponse>, t: Throwable) {
                    Toast.makeText(requireContext(), "서버 오류 발생", Toast.LENGTH_SHORT).show()
                }
            })
    }

    override fun onPause() {
        super.onPause()
        (requireActivity() as BottomController).setBottomNavVisibility(true)
    }
}
