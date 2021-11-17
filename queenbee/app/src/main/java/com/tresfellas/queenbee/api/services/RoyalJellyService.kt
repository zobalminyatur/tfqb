package com.tresfellas.queenbee.api.services

import com.tresfellas.queenbee.data.model.*
import io.reactivex.rxjava3.core.Single
import retrofit2.http.*

interface RoyalJellyService {
    @GET("royaljelly/purchase")
    fun getRoyalJellyPurchaseHistory(
        @Query("limit") limit: Int,
        @Query("page") page: Int,
    ): Single<RoyalJellyPurchaseHistoryDTO>

    @POST("royaljelly/purchase")
    fun purchaseRoyalJelly(
        @Body royalJellyPurchase: RoyalJellyPurchase,
    ): Single<RoyalJellyPurchaseDTO>


    @GET("royaljelly/promotions")
    fun getMyRoyalJellyPromotions() : Single<RoyalJellyPromotionsDTO>


}