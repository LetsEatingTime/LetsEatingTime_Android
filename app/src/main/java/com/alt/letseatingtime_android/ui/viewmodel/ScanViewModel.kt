package com.alt.letseatingtime_android.ui.viewmodel

import android.app.Application
import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import coil.network.HttpException
import com.alt.letseatingtime_android.MyApplication
import com.alt.letseatingtime_android.network.retrofit.RetrofitClient
import com.alt.letseatingtime_android.network.retrofit.response.BaseResponse
import com.alt.letseatingtime_android.network.retrofit.response.profile.ProfileResponse
import com.alt.letseatingtime_android.network.retrofit.response.scan.ScanResponse
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.io.FileOutputStream

class ScanViewModel(application: Application) : AndroidViewModel(application) {

    init {
        getProfile()
    }

    private val context = getApplication<Application>().applicationContext

    private var _imageData = MutableLiveData<Uri>()
    private var _userData = MutableLiveData<ProfileResponse>()

    private var _toastMessage = MutableLiveData<String>()
    val toastMessage: LiveData<String> = _toastMessage

    val imageData: LiveData<Uri> = _imageData

    fun setUri(uri: Uri){
        _imageData.value = uri
    }

    fun scanningMeal(onAction : ()->Unit){
        val body = prepareFilePart(_imageData.value!!)
        RetrofitClient.api.scanMenu(
            userId = _userData.value?.data?.user?.idx?.toInt() ?: -1,
            file = body!!
        ).enqueue(
            object  : Callback<ScanResponse>{
                override fun onResponse(
                    call: Call<ScanResponse>,
                    response: Response<ScanResponse>
                ) {
                    Log.d("ScanResult", "result : ${response.body()}")
                    onAction()
                }

                override fun onFailure(call: Call<ScanResponse>, t: Throwable) {
                    t.printStackTrace()
                    if(t is HttpException){
                        Log.e("ScanResult", "e : ${t.response.body}")
                    }
                    _toastMessage.value = "인터넷에 연결 되어있는지 확인 해주세요"
                }

            }
        )
    }

    private fun prepareFilePart(uri: Uri): MultipartBody.Part? {
        val file = getFileFromUri(uri = uri)
        return file?.let {
            val requestFile = it.asRequestBody("image/*".toMediaTypeOrNull())
            MultipartBody.Part.createFormData("file", it.name, requestFile)
        }
    }

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
                    getProfile()
                }
            }

            override fun onFailure(call: Call<ProfileResponse>, t: Throwable) {
            }
        })

    }

    private fun getFileFromUri(uri: Uri): File? {
        return try {
            val inputStream = context.contentResolver.openInputStream(uri)
            val file = File(context.cacheDir, "temp_file")
            val outputStream = FileOutputStream(file)
            inputStream?.copyTo(outputStream)
            inputStream?.close()
            outputStream.close()
            file
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}