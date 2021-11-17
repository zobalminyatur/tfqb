package com.tresfellas.queenbee.data

import android.graphics.Bitmap
import com.tresfellas.queenbee.api.methods.UserApi
import com.tresfellas.queenbee.data.model.*
import com.tresfellas.queenbee.extensions.mapNetworkErrors
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import okhttp3.MediaType
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import java.io.ByteArrayOutputStream

class MainRepository {
    private val userApi = UserApi()

    fun updateCurrentUser(userDTO: UserDTO): Single<UserDTO> {
        return userApi.updateCurrentUser(userDTO).mapNetworkErrors()
    }
    fun updatePromotionState(promotionName : String, state: RoyalJellyPromotionState): Single<RoyalJellyPromotionsDTO> {
        return userApi.updatePromotionState(promotionName, state).mapNetworkErrors()
    }

}