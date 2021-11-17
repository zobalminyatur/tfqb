package com.tresfellas.queenbee.api.services

import io.reactivex.rxjava3.core.Completable
import okhttp3.RequestBody
import okhttp3.Response
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Url

interface MediaServices {
    @POST("uploads")
    fun uploadImage(
        @Body image: RequestBody
    ): Completable

    @POST("uploads")
    fun uploadImagetest(
        @Body image: RequestBody
    ): Call<ResponseBody>
}