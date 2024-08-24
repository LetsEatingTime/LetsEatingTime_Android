package com.alt.letseatingtime_android.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.alt.letseatingtime_android.ui.adapter.store.storedata.GoodsItem

class StoreViewModel : ViewModel() {
    private var _goodsData = MutableLiveData<GoodsItem>()
    val goodsData: LiveData<GoodsItem> = _goodsData

    fun setGoodsData(data : GoodsItem)  {
        _goodsData.value = data
    }
}