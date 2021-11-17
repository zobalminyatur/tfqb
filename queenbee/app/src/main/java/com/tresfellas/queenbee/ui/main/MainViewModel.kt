package com.tresfellas.queenbee.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tresfellas.queenbee.api.managers.CurrentUserManager
import com.tresfellas.queenbee.data.MainRepository
import com.tresfellas.queenbee.data.model.*
import com.tresfellas.queenbee.utils.RxUtil

class MainViewModel(private val mainRepository: MainRepository) : ViewModel() {

    val toPopBackStack = MutableLiveData<Boolean>()
    val toNextPage = MutableLiveData<Boolean>()
    val isProcessBar = MutableLiveData<Boolean>()
    val errorStatus = MutableLiveData<Int>()
    val smsOtpDto = MutableLiveData<SMSOTPDTO>()
    val registerDTO = MutableLiveData<RegisterDTO>()
//    val userLiveData = MutableLiveData<UserDTO>()

    val currentUserDTO = MutableLiveData<UserDTO>()

    fun updateCurrentUser(userDTO: UserDTO) {
        isProcessBar.value = true
        mainRepository.updateCurrentUser(userDTO)
            .compose(RxUtil.applySingleSchedulers())
            .subscribe(
                {
                    println("@@@@UpdateSueccessex$it")
//                    userLiveData.value = it
                    CurrentUserManager.currentUser = it
                    currentUserDTO.value = it
                    isProcessBar.value = false
                    toPopBackStack.value = true
                },
                {
                    println("@@@@@@$it")
                    errorStatus.postValue(HttpErrorCode.fromThrowable(it).message)
                    isProcessBar.value = false
                }
            )
    }
    fun activatePromotionState(promotionName : String, state: String) {
        isProcessBar.value = true
        val toActivatePromotionState = RoyalJellyPromotionState(state)
        mainRepository.updatePromotionState(promotionName,toActivatePromotionState)
            .compose(RxUtil.applySingleSchedulers())
            .subscribe(
                {
                    println("@@@@PromotionUpdateSueccessex$it")
//                    userLiveData.value = it
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
}