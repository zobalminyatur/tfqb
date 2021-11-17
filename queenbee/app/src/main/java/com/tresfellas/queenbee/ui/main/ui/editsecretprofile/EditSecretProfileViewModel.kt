package com.tresfellas.queenbee.ui.main.ui.editsecretprofile

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tresfellas.queenbee.api.methods.AdminApi
import com.tresfellas.queenbee.data.model.*
import com.tresfellas.queenbee.utils.RxUtil

class EditSecretProfileViewModel : ViewModel() {

    val adminApi = AdminApi()

    val isProcessBar = MutableLiveData<Boolean>()
    val questionnaireItemsLiveData = MutableLiveData<List<QuestionnaireItem>>()
    var questionnaireItems = listOf<QuestionnaireItem>()
    val errorStatus = MutableLiveData<Int>()

    var toUploadBoldProfileDTO = arrayListOf<UserDTO.BoldProfileDTO>()

    fun sortBoldProfileByQuestionNumber(): ArrayList<UserDTO.BoldProfileDTO> {
        val list =  toUploadBoldProfileDTO.sortedBy { it.questionNumber }
        val arraylist = arrayListOf<UserDTO.BoldProfileDTO>()
        arraylist.addAll(list)
        return arraylist
    }

    fun getFemaleBoldProfiles() {
        isProcessBar.value = true
        adminApi.getFemaleBoldProfiles()
            .compose(RxUtil.applySingleSchedulers())
            .subscribe(
                {
                    println("@@@@@@@@@getFemaleboldprofiles")
                    questionnaireItemsLiveData.value = it.questionnaireItems
                    toUploadBoldProfileDTO = parseToUploadBoldProfile(it.questionnaireItems)
                    isProcessBar.value = false
                },
                {
                    println("@@@@@@@@@FUCKFemaleboldprofile$it")
                    errorStatus.value = HttpErrorCode.fromThrowable(it).message
                    isProcessBar.value = false
                }
            )
    }

    fun getMaleBoldProfiles() {
        isProcessBar.value = true
        adminApi.getMaleBoldProfiles()
            .compose(RxUtil.applySingleSchedulers())
            .subscribe(
                {
                    println("@@@@@@@@@getMaleboldprofiles$it")
                    questionnaireItemsLiveData.value = it.questionnaireItems
                    toUploadBoldProfileDTO = parseToUploadBoldProfile(it.questionnaireItems)
                    isProcessBar.value = false
                },
                {
                    println("@@@@@@@@@FUCKMaleboldprofile$it")
                    errorStatus.value = HttpErrorCode.fromThrowable(it).message
                    isProcessBar.value = false
                }
            )
    }

    fun parseToUploadBoldProfile(list : List<QuestionnaireItem>): ArrayList<UserDTO.BoldProfileDTO> {
        val arraylist = arrayListOf<UserDTO.BoldProfileDTO>()
        for(item in list){
            arraylist.add(UserDTO.BoldProfileDTO(item.questionNumber, item.question))
        }
        return arraylist
    }

    fun parseCurrentUserBoldProfileToUpdate(userDTO: UserDTO){
        userDTO.boldProfile

    }
}