package com.tresfellas.queenbee.ui.main.ui.chat

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tresfellas.queenbee.api.methods.ChatsApi
import com.tresfellas.queenbee.api.methods.UserApi
import com.tresfellas.queenbee.data.model.*
import com.tresfellas.queenbee.utils.RxUtil

class ChatViewModel : ViewModel() {

    val chatsApi = ChatsApi()

    val isProcessBar = MutableLiveData<Boolean>()
    val errorStatus = MutableLiveData<Int>()
    val chatsDTO = MutableLiveData<ChatsDTO>()
    var royalJellyUsageDTO = MutableLiveData<RoyalJellyUsageDTO>()

    fun getChatRoom(userId : String){
        isProcessBar.value = true
        chatsApi.getChatRoom(1,100, userId)
            .compose(RxUtil.applySingleSchedulers())
            .subscribe(
                {
                    println("@@@@@@@@@sexgetChatROOM$it")
                    chatsDTO.value = it
//                    isProcessBar.value = false
                },
                {
                    println("@@@@@@@@@FuckgetChatROOM$it")
                    errorStatus.value = HttpErrorCode.fromThrowable(it).message
//                    isProcessBar.value = false
                }
            )
    }

    fun useRoyalJelly(amount: Int){
        isProcessBar.value = true
        val amountDTO = RoyalJellyAmount(amount = amount)
        chatsApi.useRoyalJelly(amount = amountDTO)
            .compose(RxUtil.applySingleSchedulers())
            .subscribe(
                {
                    royalJellyUsageDTO.value = it
                    isProcessBar.value = false
                },
                {
                    errorStatus.value = HttpErrorCode.fromThrowable(it).message
                    isProcessBar.value = false
                }
            )

    }



}