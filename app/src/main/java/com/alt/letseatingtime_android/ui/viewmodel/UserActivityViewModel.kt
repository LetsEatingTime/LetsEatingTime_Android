package com.alt.letseatingtime_android.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.alt.letseatingtime_android.MyApplication
import com.alt.letseatingtime_android.network.retrofit.RetrofitClient
import com.alt.letseatingtime_android.network.retrofit.response.login.LoginResponse
import com.alt.letseatingtime_android.network.retrofit.response.profile.ProfileResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserActivityViewModel: ViewModel() {

    private var _userData = MutableLiveData<ProfileResponse>()
    val userData: LiveData<ProfileResponse> = _userData

    private var _toastMessage = MutableLiveData<String>()
    val toastMessage: LiveData<String> = _toastMessage

    private var _logout = MutableLiveData<Boolean>()
    val logout: LiveData<Boolean> = _logout

    fun getProfile(){
        RetrofitClient.api.profile("Bearer " + MyApplication.prefs.accessToken).enqueue(object :
            Callback<ProfileResponse> {
            override fun onResponse(
                call: Call<ProfileResponse>,
                response: Response<ProfileResponse>
            ) {
                if(response.isSuccessful) {
                    _userData.value = response.body()
                }
                else {
                    refreshToken()
                    getProfile()
                }
            }

            override fun onFailure(call: Call<ProfileResponse>, t: Throwable) {
                _toastMessage.value = "인터넷에 연결 되어있는지 확인 해주세요"
                _logout.value = true
            }
        })

    }

    fun refreshToken(){
        RetrofitClient.api.refresh("Bearer " + MyApplication.prefs.refreshToken).enqueue(object :
            Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if(response.isSuccessful){
                    MyApplication.prefs.refreshToken = response.body()?.data?.refreshToken.toString()
                    MyApplication.prefs.accessToken = response.body()?.data?.accessToken.toString()
                    getProfile()
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