package com.alt.letseatingtime_android.ui.viewmodel

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.alt.letseatingtime.R
import com.alt.letseatingtime_android.MyApplication
import com.alt.letseatingtime_android.network.retrofit.RetrofitClient
import com.alt.letseatingtime_android.network.retrofit.response.ImageResponse
import com.alt.letseatingtime_android.network.retrofit.response.WithdrawResponse
import com.alt.letseatingtime_android.network.retrofit.response.login.LoginResponse
import com.alt.letseatingtime_android.network.retrofit.response.profile.ProfileResponse
import com.alt.letseatingtime_android.network.retrofit.response.profile.User
import com.alt.letseatingtime_android.network.retrofit.response.util.BaseResponse
import com.alt.letseatingtime_android.util.shortToast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserActivityViewModel : ViewModel() {

    private var _userData = MutableLiveData<ProfileResponse>()
    val userData: LiveData<ProfileResponse> = _userData

    private var _toastMessage = MutableLiveData<String>()
    val toastMessage: LiveData<String> = _toastMessage

    private var _logout = MutableLiveData<Boolean>()
    val logout: LiveData<Boolean> = _logout

    private var _userImageUrl = MutableLiveData<String>().apply { value = "" }
    val userImageUrl: LiveData<String> = _userImageUrl

    private var _isSuccessChange =  MutableLiveData<Boolean>().apply { value = false }
    val isSuccessChange: LiveData<Boolean> = _isSuccessChange

    fun getProfile() {
        RetrofitClient.api.profile("Bearer " + MyApplication.prefs.accessToken).enqueue(object :
            Callback<ProfileResponse> {
            override fun onResponse(
                call: Call<ProfileResponse>,
                response: Response<ProfileResponse>
            ) {
                if (response.isSuccessful) {
                    _userData.value = response.body()
                    if (response.body()?.data?.user?.image != null) {
                        getUserImage(response.body()?.data?.user?.image!!.toInt())
                    }
                    else{
                        _userImageUrl.value =" "
                    }
                } else {
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

    fun userModify(userInfo: User) {

        RetrofitClient.api.editStudent(
            Authorization = "Bearer " + MyApplication.prefs.accessToken,
            body = userInfo
        ).enqueue(object : Callback<BaseResponse<String>> {
            override fun onResponse(
                call: Call<BaseResponse<String>>,
                response: Response<BaseResponse<String>>
            ) {
                Log.d("viewModel", "response : $response")
                Log.d("viewModel", "data : $userInfo")
                Log.d("viewModel", "response : ${MyApplication.prefs.accessToken}")
                if (response.isSuccessful){
                    if (response.code() == 200){
                        _isSuccessChange.value = true
                    }
                }else{
                        _toastMessage.value = "정보 변경에 실패했습니다."

                }
            }

            override fun onFailure(call: Call<BaseResponse<String>>, t: Throwable) {
                _toastMessage.value = "인터넷에 연결 되어있는지 확인 해주세요"
                _logout.value = true
            }
        })
    }

    fun getUserImage(userIdx: Int) {
        RetrofitClient.api.getUserImage(
            authorization = "Bearer " + MyApplication.prefs.accessToken,
            idx = userIdx.toString()
        ).enqueue(object : Callback<ImageResponse> {
            override fun onResponse(call: Call<ImageResponse>, response: Response<ImageResponse>) {
                if (response.isSuccessful) {
                    if (response.code() == 200) {
                        val result = response.body()
                        if (result != null) {
                            _userImageUrl.value = result.fileName
                        }
                    }
                } else {
                    refreshToken()
                    getUserImage(userIdx)
                }
            }

            override fun onFailure(call: Call<ImageResponse>, t: Throwable) {
                _toastMessage.value = "인터넷에 연결 되어있는지 확인 해주세요"
                _logout.value = true
            }
        })
    }

    fun refreshToken() {
        RetrofitClient.api.refresh("Bearer " + MyApplication.prefs.refreshToken).enqueue(object :
            Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if (response.isSuccessful) {
                    MyApplication.prefs.refreshToken =
                        response.body()?.data?.refreshToken.toString()
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

    fun withdraw(context: Context) {
        RetrofitClient.api.withdraw(
            "Bearer " + MyApplication.prefs.refreshToken
        ).enqueue(object : Callback<WithdrawResponse> {
            override fun onResponse(
                call: Call<WithdrawResponse>,
                response: Response<WithdrawResponse>
            ) {
                if (response.isSuccessful) {
                    context.shortToast("정상적으로 회원탈퇴 되셨습니다.")
                } else {
                    Log.e(
                        "ProfileFragment",
                        "${
                            response.errorBody().toString()
                        }, ${response.code()}, ${response.body()}, ${response.message()}"
                    )
                    context.shortToast("회원탈퇴 실패 다시 시도해 주세요.")
                }
            }

            override fun onFailure(call: Call<WithdrawResponse>, t: Throwable) {
                Log.e("server error", t.stackTraceToString())
                context.shortToast("서버 에러")
            }

        })
    }

    fun setImageUri(uri: Uri) {
        _userImageUrl.value = uri.toString()
    }

    fun initIsSuccessChange(){
        _isSuccessChange.value = false
    }
}