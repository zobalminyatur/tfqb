package com.tresfellas.queenbee.data.model

data class RoyalJellyPurchaseHistoryDTO(
    val purchaseHistory : List<RoyalJellyPurchase>,
    val balance : Int,
    val totalCount : Int,
    val currentPage : Int,
    val totalPages : Int
) {
}