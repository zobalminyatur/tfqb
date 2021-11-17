package com.tresfellas.queenbee.di.module

import com.tresfellas.queenbee.api.managers.TokenInterceptor
import com.tresfellas.queenbee.di.scope.AppScoped
import com.tresfellas.queenbee.utils.SharedPreference
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import javax.inject.Named

@Module
class RetrofitModule {

    @AppScoped
    @Provides
    fun provideTokenAuthenticator(
        sharedPreference: SharedPreference
    ): TokenInterceptor {
        return TokenInterceptor()
    }
}