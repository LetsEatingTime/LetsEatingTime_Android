package com.alt.letseatingtime_android.network.retrofit.response.scan

import android.view.Menu

data class ScanResponse(
    val menu : List<ScanMenuResult>,
    val total : Float
)
