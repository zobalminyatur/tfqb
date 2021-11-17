package com.tresfellas.queenbee.api.services

import com.tresfellas.queenbee.data.model.PhoneNumber
import com.tresfellas.queenbee.data.model.RegisterDTO
import com.tresfellas.queenbee.data.model.SMSOTPDTO
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.POST
import retrofit2.http.Query

interface MembershipService {
    @POST("auth/smsotp/send")
    fun requestSMSOTP(
        @Body phoneNumber: SMSOTPDTO,
    ): Single<SMSOTPDTO>

    @POST("auth/smsotp/verify")
    fun verifySMSOTP(
        @Body smsOtpDto: SMSOTPDTO,
    ): Single<Response<RegisterDTO>>

    @POST("auth/smsotp/verify")
    fun verifySMSOTP2(
        @Body smsOtpDto: SMSOTPDTO,
    ): Observable<okhttp3.Response>

    @POST("auth/refresh")
    fun refreshToken() : Single<okhttp3.Response>
}