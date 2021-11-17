package com.tresfellas.queenbee.data.model

data class RoyalJellyUsageDTO(
    val ownerId : String,
    val balance : Int,
    val newUsage : List<RoyalJellyAmount>
) {
}