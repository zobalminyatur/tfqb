package com.tresfellas.queenbee.api.methods

import com.tresfellas.queenbee.api.managers.CustomInterceptor
import com.tresfellas.queenbee.api.managers.RetrofitBuilder
import com.tresfellas.queenbee.api.services.MembershipService
import com.tresfellas.queenbee.data.model.RegisterDTO
import com.tresfellas.queenbee.data.model.SMSOTPDTO
import com.tresfellas.queenbee.extensions.mapNetworkErrors
import com.tresfellas.queenbee.utils.Constants
import io.reactivex.rxjava3.core.Single
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MembershipApi {
    private var interceptor: Interceptor = CustomInterceptor()
    private var client = RetrofitBuilder(interceptor).buildRetrofit()
    private var authClient = client.create(MembershipService::class.java)

    fun requestSMSOTP(phoneNumber: SMSOTPDTO): Single<SMSOTPDTO> {
        return authClient.requestSMSOTP(phoneNumber).mapNetworkErrors()
    }

    fun verifySMSOTP(smsOtpDto: SMSOTPDTO): Single<Response<RegisterDTO>> {
        return authClient.verifySMSOTP(smsOtpDto).mapNetworkErrors()
    }

}