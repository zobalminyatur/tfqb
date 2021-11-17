package com.tresfellas.queenbee.ui.register

import android.graphics.Bitmap
import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tresfellas.queenbee.data.LoginRepository
import com.tresfellas.queenbee.api.managers.TokenManager
import com.tresfellas.queenbee.data.model.*
import com.tresfellas.queenbee.utils.RxUtil
import okhttp3.RequestBody
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback

class LoginViewModel(private val loginRepository: LoginRepository) : ViewModel() {

    val toNextPage = MutableLiveData<Boolean>()

    val isProcessBar = MutableLiveData<Boolean>()
    val smsList = MutableLiveData<SMSOTPDTO>()
    private val _loginResult = MutableLiveData<Boolean>()
    val loginResult: LiveData<Boolean> = _loginResult

    val errorStatus = MutableLiveData<Int>()
    val smsOtpDto = MutableLiveData<SMSOTPDTO>()
    val registerDTO = MutableLiveData<RegisterDTO>()
    val userLiveData = MutableLiveData<UserDTO>()

    var uriItems : ArrayList<Uri> = arrayListOf()

    val gender = MutableLiveData<String>()

    fun login(username: String, password: String) {
        // can be launched in a separate asynchronous job
        val result = loginRepository.login(username, password)

//        if (result is Result.Success) {
//            _loginResult.value = RegisterDTO(cellphone = )
//        } else {
//            _loginResult.value = LoginResult(error = R.string.login_failed)
//        }
    }

    fun requestMobileVerificationCode(phoneNumber: SMSOTPDTO) {
        loginRepository.requestSMSOTP(phoneNumber)
            .compose(RxUtil.applySingleSchedulers())
            .subscribe(
                {
                    smsOtpDto.value = it
                    toNextPage.value = true
                    isProcessBar.value = false
                },
                {
                    errorStatus.value = HttpErrorCode.fromThrowable(it).message
                    isProcessBar.value = false
                }
            )
    }

    fun verifyMobileVerificationCode(smsotpdto: SMSOTPDTO) {
        loginRepository.verifySMSOTP(smsotpdto)
            .compose(RxUtil.applySingleSchedulers())
            .subscribe(
                {
                    if (it.code() == 200) {
                        val headers = it.headers()
                        val accessToken = headers.get("access")!!
                        val refreshToken = headers.get("refresh")!!
                        TokenManager.accessToken = accessToken
                        TokenManager.refreshToken = refreshToken
                        println("@@@@AccessToken=$accessToken")
                        println("@@@@RefreshToken=$refreshToken")
                        registerDTO.value = it.body()
                    } else if (it.code() == 401) {
                        errorStatus.value = HttpErrorCode.UNAUTHORIZED.message
                        println("@@@@@@@${errorStatus.value}")
                    }
                    isProcessBar.value = false
                },
                {
                    errorStatus.value = HttpErrorCode.fromThrowable(it).message
                    isProcessBar.value = false
                }
            )
    }

    fun uploadImage(bitmap: Bitmap) {
        println("@@@UPDATELMAGE@${TokenManager.accessToken}")
        loginRepository.uploadImage(bitmap)
            .compose(RxUtil.completableTransformer())
            .subscribe(
                {
                    println("@@@@SUCCESSXX")
//                        isProcessBar.value = false
                },
                {
                    println("@@@@@@$it")
                    errorStatus.postValue(HttpErrorCode.fromThrowable(it).message)
//                        isProcessBar.value = false
                }
            )
    }

    fun uploadImageTest(requestBody: RequestBody) {
        loginRepository.uploadImagetest(requestBody).enqueue(object : Callback<ResponseBody> {
            override fun onResponse(
                call: Call<ResponseBody>,
                response: retrofit2.Response<ResponseBody>
            ) {
                val message = response.message()
                val header = response.headers()
                val code = response.code()
                val errorbody  = response.errorBody()
                val issuccesex = response.isSuccessful
                println("@@@@@@$message@@$header@@@$code@@@$errorbody@@@$issuccesex")
                println("@@@@@${response.body().toString()}")
//                val json = JSONObject(response.body().toString())
//                val imageUrl = json.get("image")
//                println("@@@@UploadImageSUCCESSXX${imageUrl}")
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                errorStatus.postValue(HttpErrorCode.fromThrowable(t).message)
            }
        })
    }

    fun updateCurrentUser(userDTO: UserDTO) {
        isProcessBar.value = true
        println("@@@@@@@@$userDTO")
        loginRepository.updateCurrentUser(userDTO)
            .compose(RxUtil.applySingleSchedulers())
            .subscribe(
                {
                    println("@@@@UpdateSueccessex")
                    userLiveData.value = it
                    isProcessBar.value = false
                    toNextPage.value = true
                },
                {
                    println("@@@@@@$it")
                    errorStatus.postValue(HttpErrorCode.fromThrowable(it).message)
                    isProcessBar.value = false
                }
            )
    }

    fun register() {
        println("REGISTER@@@@$registerDTO")
    }

}