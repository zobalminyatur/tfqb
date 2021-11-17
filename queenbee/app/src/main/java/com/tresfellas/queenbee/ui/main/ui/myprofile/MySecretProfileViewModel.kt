package com.tresfellas.queenbee.ui.main.ui.myprofile

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tresfellas.queenbee.api.managers.CurrentUserManager
import com.tresfellas.queenbee.api.methods.AdminApi
import com.tresfellas.queenbee.data.model.HttpErrorCode
import com.tresfellas.queenbee.data.model.QuestionnaireItem
import com.tresfellas.queenbee.data.model.UserDTO
import com.tresfellas.queenbee.utils.RxUtil

class MySecretProfileViewModel : ViewModel() {

    val adminApi = AdminApi()

    val isProcessBar = MutableLiveData<Boolean>()
    var questionnaireItems = listOf<QuestionnaireItem>()
    val errorStatus = MutableLiveData<Int>()

    var toUploadBoldProfileDTO = arrayListOf<UserDTO.BoldProfileDTO>()

    fun sortBoldProfileByQuestionNumber(): ArrayList<UserDTO.BoldProfileDTO> {
        val list =  toUploadBoldProfileDTO.sortedBy { it.questionNumber }
        val arraylist = arrayListOf<UserDTO.BoldProfileDTO>()
        arraylist.addAll(list)
        return arraylist
    }

    fun getBoldProfiles(){
        if(questionnaireItems.isEmpty()){
            if(CurrentUserManager.currentUser.sex == "male"){
                getMaleBoldProfiles()
            }else{
                getFemaleBoldProfiles()
            }
        }
    }

    private fun getFemaleBoldProfiles() {
        isProcessBar.value = true
        adminApi.getFemaleBoldProfiles()
            .compose(RxUtil.applySingleSchedulers())
            .subscribe(
                {
                    println("@@@@@@@@@getFemaleboldprofiles")
                    questionnaireItems = it.questionnaireItems
                    isProcessBar.value = false
                },
                {
                    println("@@@@@@@@@FUCKFemaleboldprofile$it")
                    errorStatus.value = HttpErrorCode.fromThrowable(it).message
                    isProcessBar.value = false
                }
            )
    }

    private fun getMaleBoldProfiles() {
        isProcessBar.value = true
        adminApi.getMaleBoldProfiles()
            .compose(RxUtil.applySingleSchedulers())
            .subscribe(
                {
                    println("@@@@@@@@@getMaleboldprofiles$it")
                    questionnaireItems = it.questionnaireItems
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