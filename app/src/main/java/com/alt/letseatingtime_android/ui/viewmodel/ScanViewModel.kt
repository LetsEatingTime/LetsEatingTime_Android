package com.alt.letseatingtime_android.ui.viewmodel

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ScanViewModel : ViewModel() {

    private var _imageData = MutableLiveData<Uri>()
    val imageData: LiveData<Uri> = _imageData

    fun setUri(uri: Uri){
        _imageData.value = uri
    }

    fun scanningMeal(){
        //TODO : 급식 스캔 부분 구현하기
    }
}