package com.tresfellas.queenbee.data.model

data class RoyalJellyPurchaseDTO(
    var ownerId : String,
    var balance : Int,
    var newPurchase : RoyalJellyPurchase
) {
}