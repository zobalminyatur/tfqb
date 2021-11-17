package com.tresfellas.queenbee.api.methods

import com.tresfellas.queenbee.api.managers.RetrofitBuilder
import com.tresfellas.queenbee.api.managers.TokenInterceptor
import com.tresfellas.queenbee.api.services.MediaServices
import com.tresfellas.queenbee.api.services.UserService
import com.tresfellas.queenbee.data.model.*
import com.tresfellas.queenbee.extensions.mapNetworkErrors
import io.reactivex.rxjava3.core.Single
import okhttp3.Interceptor

class UserApi {
    private var interceptor: Interceptor = TokenInterceptor()
    private var client = RetrofitBuilder(interceptor).buildRetrofit()
    private var userClient = client.create(UserService::class.java)

    fun updateCurrentUser(currentUserDTO: UserDTO): Single<UserDTO> {
        return userClient.updateCurrentUser(currentUserDTO).mapNetworkErrors()
    }

    fun getCurrentUser(): Single<UserDTO> {
        return userClient.getCurrentUser().mapNetworkErrors()
    }

    fun getAllUsers(page: Int, limit : Int, nearestWithin : Int): Single<UserListDTO> {
        return userClient.getAllUsers(page,limit,nearestWithin).mapNetworkErrors()
    }
    fun updatePromotionState(promotionName : String, state: RoyalJellyPromotionState): Single<RoyalJellyPromotionsDTO> {
        return userClient.updatePromotionState(promotionName, state).mapNetworkErrors()
    }

}