package com.tresfellas.queenbee.api.methods

import com.tresfellas.queenbee.api.managers.RetrofitBuilder
import com.tresfellas.queenbee.api.managers.TokenInterceptor
import com.tresfellas.queenbee.api.services.RoyalJellyService
import com.tresfellas.queenbee.data.model.*
import com.tresfellas.queenbee.extensions.mapNetworkErrors
import io.reactivex.rxjava3.core.Single
import okhttp3.Interceptor
import retrofit2.http.Body
import retrofit2.http.Path

class RoyalJellyApi {
    private var interceptor: Interceptor = TokenInterceptor()
    private var client = RetrofitBuilder(interceptor).buildRetrofit()
    private var royalJellyClient = client.create(RoyalJellyService::class.java)

    fun getRoyalJellyPurchaseHistory(limit: Int, page : Int): Single<RoyalJellyPurchaseHistoryDTO> {
        return royalJellyClient.getRoyalJellyPurchaseHistory(limit,page).mapNetworkErrors()
    }
    fun purchaseRoyalJelly(royalJellyPurchase: RoyalJellyPurchase): Single<RoyalJellyPurchaseDTO> {
        return royalJellyClient.purchaseRoyalJelly(royalJellyPurchase).mapNetworkErrors()
    }

    fun getMyRoyalJellyPromotions(): Single<RoyalJellyPromotionsDTO> {
        return royalJellyClient.getMyRoyalJellyPromotions().mapNetworkErrors()
    }

}