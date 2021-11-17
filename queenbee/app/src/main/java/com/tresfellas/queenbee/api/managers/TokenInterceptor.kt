package com.tresfellas.queenbee.api.managers

import com.tresfellas.queenbee.api.methods.AuthApi
import com.tresfellas.queenbee.di.scope.AppScoped
import com.tresfellas.queenbee.utils.SharedPreference
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.io.IOException
import javax.inject.Inject

@AppScoped
class TokenInterceptor @Inject constructor(
//    private val authApi: AuthApi,
//    private val sharedPreference: SharedPreference
) : Interceptor {

    private val HEADER_NAME = "Authorization"
    private val HEADER_VALUE = "Bearer "

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response? {
        synchronized(this) {
            val originalRequest = chain.request()
            val authenticationRequest = buildRequest(originalRequest)
            val initialResponse = chain.proceed(authenticationRequest)

            return when (initialResponse.code()) {
                401 -> {
                    if (TokenManager.refreshToken == "null") {
                        return initialResponse
//                        logout
//                        clientCredentials(initialResponse,originalRequest,chain)
                    } else {
                        chain.proceed(buildRefreshRequest(chain.request()))
//                        passwordCredentials(initialResponse,originalRequest,chain)
                    }
                }
                403 -> {
                    if(TokenManager.refreshToken == "null"){
                        return initialResponse
                    }else{
                        chain.proceed(buildRefreshRequest(chain.request()))
                    }
                }
                else -> initialResponse
            }
        }
    }

    private fun buildRequest(request: Request): Request {
        return request.newBuilder()
            .addHeader(HEADER_NAME, HEADER_VALUE + TokenManager.accessToken)
            .build()
    }
    private fun buildRefreshRequest(request: Request): Request {
        return request.newBuilder()
            .addHeader("refresh", HEADER_VALUE + TokenManager.refreshToken)
            .build()
    }
}