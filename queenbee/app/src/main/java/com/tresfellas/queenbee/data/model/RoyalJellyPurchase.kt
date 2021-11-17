package com.tresfellas.queenbee.data.model

data class RoyalJellyPurchase(
    var amount : Int,
    var cost : Int,
    var recordType : String,
    var createdAt : String?= null
    ) {
    }