package com.basecode.app.commonClass.daggerComponent

import com.localfolks.app.commonClass.appController.AppController
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException


internal class LoggingInterceptor : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val newRequest = chain.request().newBuilder()
            .addHeader("Authorization","Bearer "+ AppController.token!!)
            .build()

        return chain.proceed(newRequest)
    }
}
