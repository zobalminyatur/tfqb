package com.tresfellas.queenbee.api.services

import com.tresfellas.queenbee.data.model.*
import io.reactivex.rxjava3.core.Single
import retrofit2.http.*

interface ChatService {

    @GET("chats")
    fun getChatRoomList(
        @Query("page") page: Int,
        @Query("limit") limit : Int,
    ): Single<ChatRoomsDTO>

    @GET("chats/rooms/{userId}")
    fun getChatRoom(
        @Path("userId") userId : String,
        @Query("page") page: Int,
        @Query("limit") limit : Int,
    ): Single<ChatsDTO>

    @POST("royaljelly/usage")
    fun useRoyalJelly(
        @Body amount: RoyalJellyAmount,
    ): Single<RoyalJellyUsageDTO>
}