package com.alt.letseatingtime_android.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.alt.letseatingtime_android.MyApplication
import com.alt.letseatingtime_android.network.retrofit.RetrofitClient
import com.alt.letseatingtime_android.network.retrofit.response.goods.StoreResponse
import com.alt.letseatingtime_android.network.retrofit.response.login.LoginResponse
import com.alt.letseatingtime_android.network.retrofit.response.util.BaseResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class StoreViewModel : ViewModel() {
    private var _goodsData = MutableLiveData<StoreResponse>()
    private var _goodsDataList = MutableLiveData<List<StoreResponse>>()
    val goodsData: LiveData<StoreResponse> = _goodsData
    val goodsDataList: LiveData<List<StoreResponse>> = _goodsDataList

    private var _logout = MutableLiveData<Boolean>()
    val logout: LiveData<Boolean> = _logout

    private var _toastMessage = MutableLiveData<String>()
    val toastMessage: LiveData<String> = _toastMessage

    fun setGoodsData(data: StoreResponse) {
        _goodsData.value = data
    }

    fun setGoodsDataList(data: List<StoreResponse>) {
        _goodsDataList.value = data
    }

    fun getGoods() {
        RetrofitClient.api.getStoreList().enqueue(object :
            Callback<BaseResponse<List<StoreResponse>>> {
            override fun onResponse(
                call: Call<BaseResponse<List<StoreResponse>>>,
                response: Response<BaseResponse<List<StoreResponse>>>
            ) {
                if (response.isSuccessful) {
                    val result = response.body()
                    if (result!!.status == 200) {
                        _goodsDataList.value = result.data
                    }
                }
            }

            override fun onFailure(call: Call<BaseResponse<List<StoreResponse>>>, t: Throwable) {
                t.printStackTrace()
                refreshToken(){
                    getGoods()
                }
            }


        })
    }

    fun getProductImageUrl(idx : Int){

    }

    fun getProductImageUrlList(productList : List<StoreResponse>){

    }

    fun refreshToken(action : () -> Unit){
        RetrofitClient.api.refresh("Bearer " + MyApplication.prefs.refreshToken).enqueue(object :
            Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if(response.isSuccessful){
                    MyApplication.prefs.refreshToken = response.body()?.data?.refreshToken.toString()
                    MyApplication.prefs.accessToken = response.body()?.data?.accessToken.toString()
                    action()
                } else {
                    _toastMessage.value = "다시 로그인해주세요"
                    _logout.value = true
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                _toastMessage.value = "인터넷에 연결 되어있는지 확인 해주세요"
                _logout.value = true
            }
        })
    }
}