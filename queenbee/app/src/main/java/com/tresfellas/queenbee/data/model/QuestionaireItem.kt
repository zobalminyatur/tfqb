package com.tresfellas.queenbee.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class QuestionnaireItem (
    @SerializedName("questionNumber")
    var questionNumber : Int,
    @SerializedName("question")
    var question : String,
    @SerializedName("type")
    var type : QuestionType,
    @SerializedName("response")
    var response : String?,
    @SerializedName("choices")
    var choices : List<QuestionChoices>
        ):Parcelable{
    @Parcelize
    data class QuestionChoices(
        var choiceNumber : Int,
        var choice : String,
        var isSelected : Boolean
    ):Parcelable
    enum class QuestionType(){
        @SerializedName("multipleChoice")
        MULTIPLECHOICE,
        @SerializedName("shortAnswer")
        SHORTANSWER}
}