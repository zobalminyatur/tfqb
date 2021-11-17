package com.tresfellas.queenbee.api.methods

import com.tresfellas.queenbee.api.managers.TokenManager
import com.tresfellas.queenbee.api.services.MembershipService
import com.tresfellas.queenbee.utils.Constants
import io.reactivex.rxjava3.core.Single
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AuthApi {

    private fun buildRequest(request: Request): Request {
        return request.newBuilder()
            .addHeader("refresh", "Bearer " + TokenManager.refreshToken)
            .build()
    }

    val retrofit = Retrofit.Builder()
        .baseUrl(Constants.API_GATEWAY_BASE_URL)
        .client(OkHttpClient())
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val service = retrofit.create(MembershipService::class.java)

    fun refreshToken(): Single<Response> {
        return service.refreshToken()
    }
}