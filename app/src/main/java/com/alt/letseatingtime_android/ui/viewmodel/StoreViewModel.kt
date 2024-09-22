package com.alt.letseatingtime_android.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.alt.letseatingtime_android.network.retrofit.RetrofitClient
import com.alt.letseatingtime_android.network.retrofit.response.goods.StoreResponse
import com.alt.letseatingtime_android.network.retrofit.response.util.BaseResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class StoreViewModel : ViewModel() {
    private var _goodsData = MutableLiveData<StoreResponse>()
    private var _goodsDataList = MutableLiveData<List<StoreResponse>>()
    val goodsData: LiveData<StoreResponse> = _goodsData
    val goodsDataList: LiveData<List<StoreResponse>> = _goodsDataList

    private var _toastMessage = MutableLiveData<String>()
    val toastMessage: LiveData<String> = _toastMessage

    fun setGoodsData(data : StoreResponse)  {
        _goodsData.value = data
    }

    fun setGoodsDataList(data : List<StoreResponse>)  {
        _goodsDataList.value = data
    }

    fun getGoods() {
        RetrofitClient.api.getStoreList().enqueue(object :
            Callback<BaseResponse<List<StoreResponse>>> {
            override fun onResponse(
                call: Call<BaseResponse<List<StoreResponse>>>,
                response: Response<BaseResponse<List<StoreResponse>>>
            ) {
                if (response.isSuccessful){
                    val result = response.body()
                    if (result!!.status == 200){
                        _goodsDataList.value = result.data
                    }
                }
            }

            override fun onFailure(call: Call<BaseResponse<List<StoreResponse>>>, t: Throwable) {
                t.printStackTrace()
            }


            })
    }
}