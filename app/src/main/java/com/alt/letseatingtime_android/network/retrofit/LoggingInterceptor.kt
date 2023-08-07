package com.alt.letseatingtime_android.network.retrofit

import android.util.Log
import okhttp3.Interceptor

class LoggingInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
        val request = chain.request()
        // 최종 URL을 확인할 수 있습니다.
        val url = request.url()
        Log.d("URL", url.toString())
        // 다른 작업을 수행할 수 있습니다.
        return chain.proceed(request)
    }
}