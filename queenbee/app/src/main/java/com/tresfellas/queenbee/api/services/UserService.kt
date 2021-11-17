package com.tresfellas.queenbee.api.services

import com.tresfellas.queenbee.data.model.*
import io.reactivex.rxjava3.core.Single
import retrofit2.http.*

interface UserService {
    @GET("users")
    fun getAllUsers(
        @Query ("page") page: Int,
        @Query ("limit") limit : Int,
        @Query ("nearestWithin") nearestWithin : Int
    ): Single<UserListDTO>

    @GET("users/me")
    fun getCurrentUser(
    ): Single<UserDTO>

    @PATCH("users/me")
    fun updateCurrentUser(
        @Body currentUserDTO: UserDTO
    ): Single<UserDTO>

    @PATCH("royaljelly/promotions/{promotionName}")
    fun updatePromotionState(
        @Path("promotionName") promotionName : String,
        @Body state: RoyalJellyPromotionState
    ): Single<RoyalJellyPromotionsDTO>
}