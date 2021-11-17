package com.tresfellas.queenbee.data.model

import android.os.Parcelable
import com.google.android.gms.maps.model.LatLng
import kotlinx.android.parcel.Parcelize

data class RegisterDTO(
    var msg : String,
    var user : UserDTO

)