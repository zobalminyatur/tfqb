package com.tresfellas.queenbee.api.methods

import com.tresfellas.queenbee.api.managers.RetrofitBuilder
import com.tresfellas.queenbee.api.managers.TokenInterceptor
import com.tresfellas.queenbee.api.services.AdminService
import com.tresfellas.queenbee.api.services.ChatService
import com.tresfellas.queenbee.data.model.*
import com.tresfellas.queenbee.extensions.mapNetworkErrors
import io.reactivex.rxjava3.core.Single
import okhttp3.Interceptor

class ChatsApi {
    private var interceptor: Interceptor = TokenInterceptor()
    private var client = RetrofitBuilder(interceptor).buildRetrofit()
    private var chatsClient = client.create(ChatService::class.java)

    fun getChatRoomList(page: Int, limit : Int): Single<ChatRoomsDTO> {
        return chatsClient.getChatRoomList(page,limit).mapNetworkErrors()
    }
    fun getChatRoom(page: Int, limit : Int, userId : String): Single<ChatsDTO> {
        return chatsClient.getChatRoom(userId,page,limit).mapNetworkErrors()
    }
    fun useRoyalJelly(amount: RoyalJellyAmount): Single<RoyalJellyUsageDTO> {
        return chatsClient.useRoyalJelly(amount).mapNetworkErrors()
    }
}