package com.tresfellas.queenbee.ui.main.ui.chat

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tresfellas.queenbee.api.methods.ChatsApi
import com.tresfellas.queenbee.data.model.ChatRoomsDTO
import com.tresfellas.queenbee.data.model.ChatsDTO
import com.tresfellas.queenbee.data.model.HttpErrorCode
import com.tresfellas.queenbee.utils.RxUtil

class ChatListViewModel : ViewModel() {

    private val chatsApi = ChatsApi()

    val isProcessBar = MutableLiveData<Boolean>()
    val errorStatus = MutableLiveData<Int>()
    val chatRoomsDTO = MutableLiveData<ChatRoomsDTO>()

    fun getChatRoomList(){
        isProcessBar.value = true
        chatsApi.getChatRoomList(1,100)
            .compose(RxUtil.applySingleSchedulers())
            .subscribe(
                {
                    println("@@@@@@@@@getChatRoomList$it")
                    chatRoomsDTO.value = it
                    isProcessBar.value = false
                },
                {
                    println("@@@@@@@@@FuckgetChatRoomList$it")
                    errorStatus.value = HttpErrorCode.fromThrowable(it).message
                    isProcessBar.value = false
                }
            )
    }

}