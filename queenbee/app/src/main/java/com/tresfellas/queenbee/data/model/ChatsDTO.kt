package com.tresfellas.queenbee.data.model

data class ChatsDTO(
    val roomId : String,
    val messages : List<Messages>,
    val totalCount : Int,
    val currentPage : Int,
    val totalPages: Int
) {
    data class Messages(
        val createdAt : String,
        val from : String,
        val to : String,
        val text : String
    )
}