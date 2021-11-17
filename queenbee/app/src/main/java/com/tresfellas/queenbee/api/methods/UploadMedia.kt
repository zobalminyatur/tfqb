package com.tresfellas.queenbee.api.methods

import com.tresfellas.queenbee.api.managers.CustomInterceptor
import com.tresfellas.queenbee.api.managers.RetrofitBuilder
import com.tresfellas.queenbee.api.managers.TokenInterceptor
import com.tresfellas.queenbee.api.services.MediaServices
import com.tresfellas.queenbee.api.services.MembershipService
import com.tresfellas.queenbee.utils.Constants
import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory
import io.reactivex.rxjava3.core.Completable
import okhttp3.*
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit

class UploadMedia {

//    private fun buildRetrofit(): Retrofit {
//        val client = OkHttpClient.Builder()
//            .retryOnConnectionFailure(true)
//            .build()
//
//        return Retrofit.Builder()
//            .baseUrl(Constants.API_GATEWAY_BASE_URL)
//            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
//            .client(client)
//            .build()
//    }
//
//    private var api = buildRetrofit().create(MediaServices::class.java)

    private var interceptor: Interceptor = TokenInterceptor()
    private var client = RetrofitBuilder(interceptor).buildRetrofit()
    private var mediaClient = client.create(MediaServices::class.java)



    fun uploadImage(file : RequestBody) : Completable {
//        val body = RequestBody.create(MediaType.parse("image/jpg"), file)
        return mediaClient.uploadImage(file).retry(2)
    }
    fun uploadImagetest(file : RequestBody) : Call<ResponseBody> {
//        val body = RequestBody.create(MediaType.parse("image/jpg"), file)
        return mediaClient.uploadImagetest(file)
    }
}