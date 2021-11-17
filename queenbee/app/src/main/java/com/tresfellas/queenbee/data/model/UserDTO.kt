package com.tresfellas.queenbee.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UserDTO(

    var _id: String,
    var placeName: String? = null,
    var location: LocationDTO,
    var distance : Double,
    var phoneNumber: String? = null,
    var agreedToTermsAndConditions: Boolean,
    var accountStatus: String,
    var nickName: String,
    var sex: String,
    var age: Int,
    var selfIntroduction: String? = null,
    var safetyProfile: ArrayList<SafetyProfileDTO>,
    var boldProfile: ArrayList<BoldProfileDTO>,
    var moment: MomentDTO? = null,
    var compatibilityIndex : Double,
    var royalJelly : Int

    ):Parcelable {

    @Parcelize
    data class LocationDTO(
        var type: String? = null,
        var coordinates: ArrayList<Double>
    ):Parcelable {

//        data class LngLat(
//            var lng: Double,
//            var lat: Double
//        )
    }
    @Parcelize
    data class BoldProfileDTO(
        var questionNumber: Int?=null,
        var question: String?=null,
        var choiceNumber: Int?=null,
        var choice : String?=null
    ):Parcelable

    @Parcelize
    data class SafetyProfileDTO(
        var id : String,
        var createdAt : String,
        var updatedAt : String,
        var title: String,
        var imageURL: String,
        var status: String
    ) : Parcelable


    @Parcelize
    data class MomentDTO(
        var createdAt : String? = null,
        var description: String
    ) : Parcelable
}
