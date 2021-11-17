package com.tresfellas.queenbee.data

import android.graphics.Bitmap
import com.tresfellas.queenbee.api.methods.MembershipApi
import com.tresfellas.queenbee.api.methods.UploadMedia
import com.tresfellas.queenbee.api.methods.UserApi
import com.tresfellas.queenbee.data.model.LoggedInUser
import com.tresfellas.queenbee.data.model.RegisterDTO
import com.tresfellas.queenbee.data.model.SMSOTPDTO
import com.tresfellas.queenbee.data.model.UserDTO
import com.tresfellas.queenbee.extensions.mapNetworkErrors
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import okhttp3.MediaType
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Response
import java.io.ByteArrayOutputStream

/**
 * Class that requests authentication and user information from the remote data source and
 * maintains an in-memory cache of login status and user credentials information.
 */

class LoginRepository(val dataSource: LoginDataSource) {

    private val membershipApi = MembershipApi()
    private val uploadMedia = UploadMedia()
    private val userApi = UserApi()

    fun requestSMSOTP(phoneNumber: SMSOTPDTO): Single<SMSOTPDTO> {
        return membershipApi.requestSMSOTP(phoneNumber).mapNetworkErrors()

    }

    fun verifySMSOTP(smsOtpDto: SMSOTPDTO): Single<Response<RegisterDTO>> {
        return membershipApi.verifySMSOTP(smsOtpDto).mapNetworkErrors()
    }

    fun uploadImage(bitmap: Bitmap) : Completable{
        return Single.just(bitmap).map {
            val stream = ByteArrayOutputStream()
            it.compress(Bitmap.CompressFormat.JPEG, 50, stream)
            return@map stream.toByteArray()
        }.map {
            return@map RequestBody.create(MediaType.parse("image/jpeg"), it)
        }.flatMapCompletable {
            return@flatMapCompletable uploadMedia.uploadImage(it)
        }.mapNetworkErrors()
    }

    fun uploadImagetest(requestBody: RequestBody) : Call<okhttp3.ResponseBody> {
        return uploadMedia.uploadImagetest(requestBody)
    }

    fun updateCurrentUser(userDTO: UserDTO): Single<UserDTO>{
        return userApi.updateCurrentUser(userDTO).mapNetworkErrors()
    }


    // in-memory cache of the loggedInUser object
    var user: LoggedInUser? = null
        private set

    val isLoggedIn: Boolean
        get() = user != null

    init {
        // If user credentials will be cached in local storage, it is recommended it be encrypted
        // @see https://developer.android.com/training/articles/keystore
        user = null
    }

    fun logout() {
        user = null
        dataSource.logout()
    }

    fun login(username: String, password: String): Result<LoggedInUser> {
        // handle login
        val result = dataSource.login(username, password)

        if (result is Result.Success) {
            setLoggedInUser(result.data)
        }

        return result
    }

    private fun setLoggedInUser(loggedInUser: LoggedInUser) {
        this.user = loggedInUser
        // If user credentials will be cached in local storage, it is recommended it be encrypted
        // @see https://developer.android.com/training/articles/keystore
    }
}