package com.tresfellas.queenbee.api.methods

import com.tresfellas.queenbee.api.managers.RetrofitBuilder
import com.tresfellas.queenbee.api.managers.TokenInterceptor
import com.tresfellas.queenbee.api.services.AdminService
import com.tresfellas.queenbee.api.services.UserService
import com.tresfellas.queenbee.data.model.BoldProfileQuestionnaireDTO
import com.tresfellas.queenbee.data.model.UserDTO
import com.tresfellas.queenbee.extensions.mapNetworkErrors
import io.reactivex.rxjava3.core.Single
import okhttp3.Interceptor

class AdminApi {
    private var interceptor: Interceptor = TokenInterceptor()
    private var client = RetrofitBuilder(interceptor).buildRetrofit()
    private var adminClient = client.create(AdminService::class.java)

    fun getFemaleBoldProfiles(): Single<BoldProfileQuestionnaireDTO> {
        return adminClient.getFemaleBoldProfiles().mapNetworkErrors()
    }
    fun getMaleBoldProfiles(): Single<BoldProfileQuestionnaireDTO> {
        return adminClient.getMaleBoldProfiles().mapNetworkErrors()
    }
}