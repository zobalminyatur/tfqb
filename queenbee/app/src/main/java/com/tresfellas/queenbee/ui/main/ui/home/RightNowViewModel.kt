package com.tresfellas.queenbee.ui.main.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tresfellas.queenbee.api.methods.UserApi
import com.tresfellas.queenbee.data.model.HttpErrorCode
import com.tresfellas.queenbee.data.model.UserDTO
import com.tresfellas.queenbee.utils.RxUtil

class RightNowViewModel : ViewModel() {

    val userApi = UserApi()

    val isProcessBar = MutableLiveData<Boolean>()
    val userListLiveData = MutableLiveData<List<UserDTO>>()
    val errorStatus = MutableLiveData<Int>()

    fun getAllUsers(){
        isProcessBar.value = true
        userApi.getAllUsers(1,1000,1000)
            .compose(RxUtil.applySingleSchedulers())
            .subscribe(
                {
                    println("@@@@@@@@@MOMENTSEXGETALLUSERS")
                    userListLiveData.value = it.users
                    isProcessBar.value = false
                },
                {
                    println("@@@@@@@@@<MONETFUCKGETALLUSERS$it")
                    errorStatus.value = HttpErrorCode.fromThrowable(it).message
                    isProcessBar.value = false
                }
            )
    }



}