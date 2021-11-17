package com.tresfellas.queenbee.data.model

data class RoyalJellyPromotionsDTO(
    var balance : Int,
    var promotions : AllRoyalJellyPromotions
) {
    data class AllRoyalJellyPromotions(
        var dailyLogin : RoyalJellyPromotion,
        var dailyMoment : RoyalJellyPromotion,
        var boldProfile : RoyalJellyPromotion,
        var firstMoment : RoyalJellyPromotion,
        var std : RoyalJellyPromotion,
        var hpv : RoyalJellyPromotion,
        var covid : RoyalJellyPromotion
    ){
        data class RoyalJellyPromotion(
            var gotJellyAt : String? = null,
            var amount : Int,
            var state : String
        )
    }
}