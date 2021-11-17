package com.tresfellas.queenbee.api.managers

import com.tresfellas.queenbee.utils.Constants
import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitBuilder(
    interceptor: Interceptor
) {

    private val client: OkHttpClient

    init {
        val builder = OkHttpClient.Builder()
        client = builder
            .addInterceptor(interceptor)
//            .addInterceptor(tokenAuthenticator)
            .retryOnConnectionFailure(true)
            .build()
    }

    fun buildRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Constants.API_GATEWAY_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .client(client)
            .build()
    }
}