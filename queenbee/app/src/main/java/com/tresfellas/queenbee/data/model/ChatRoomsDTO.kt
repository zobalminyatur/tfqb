package com.tresfellas.queenbee.data.model

data class ChatRoomsDTO(
    val chats : List<ChatRooms>,
    val totalCount : Int,
    val currentPage : Int,
    val totalPages : Int
) {
    data class ChatRooms(
        val roomId : String,
        val compatibility : Double,
        val participant1 : String,
        val participant2 : String,
        val lastMessageReceivedAt : String,
        val lastMessage : String,
        val isUnreadMessage :Boolean,
        val userId : String,
        val nickName : String,
        val place : String,
        val age : Int,
        val lastMomentCreatedAt : String
    )
}