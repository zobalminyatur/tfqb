package com.tresfellas.queenbee.api.managers

import android.content.Context
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException
import javax.inject.Inject
import kotlin.jvm.Throws

class CustomInterceptor () : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val newRequest =
            request.newBuilder()
                .addHeader("Content-Type", "application/json")
//                .addHeader("DEVICETYPE", Helpers.getDeviceType())
//                .addHeader("DEVICEUUID", Helpers.getDeviceUUID(context))
//                .addHeader("CLIENTVERSION", Constants.CLIENT_VERSION)
//                .addHeader("DEVICELANGUAGE", Helpers.getDeviceLanguage())

        return chain.proceed(newRequest.build())
    }
}