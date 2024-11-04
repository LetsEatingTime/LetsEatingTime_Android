package com.alt.letseatingtime_android.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.alt.letseatingtime_android.network.retrofit.RetrofitClient
import com.alt.letseatingtime_android.network.retrofit.response.util.BaseResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignupViewModel : ViewModel() {
    val _id : MutableLiveData<String> = MutableLiveData("")
    val _password : MutableLiveData<String> = MutableLiveData("")
    val _name : MutableLiveData<String> = MutableLiveData("")
    val _grade : MutableLiveData<Int> = MutableLiveData(-1)
    val _className : MutableLiveData<Int> = MutableLiveData(-1)
    val _classNo : MutableLiveData<Int> = MutableLiveData(-1)
    val id : LiveData<String> =  _id
    val password : LiveData<String> =  _password
    val name : LiveData<String> =  _name
    val grade : LiveData<Int> =  _grade
    val className : LiveData<Int> =  _className
    val classNo : LiveData<Int> =  _classNo

    fun setId(inputId: String){
        _id.value = inputId
    }
    fun setPassword(inputPassword: String){
        _password.value = inputPassword
    }
    fun setName(inputName: String){
        _name.value = inputName
    }
    fun setGrade(inputGrade: Int){
        _grade.value = inputGrade
    }
    fun setClassName(inputClassName: Int){
        _className.value = inputClassName
    }
    fun setClassNo(inputClassNo: Int){
        _classNo.value = inputClassNo
    }

    val _message : MutableLiveData<String> = MutableLiveData("")
    val message : LiveData<String> =  _message



    fun idDuplicateCheck(inputId : String){
        RetrofitClient.api.idDuplicateCheck(inputId).enqueue(object :
            Callback<BaseResponse<String>> {
            override fun onResponse(
                call: Call<BaseResponse<String>>,
                response: Response<BaseResponse<String>>
            ) {
                val response = response.body()
                _message.value = response?.data
            }

            override fun onFailure(call: Call<BaseResponse<String>>, t: Throwable) {
            }
        })
    }
}