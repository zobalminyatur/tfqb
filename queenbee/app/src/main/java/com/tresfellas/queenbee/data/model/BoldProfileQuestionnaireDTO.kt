package com.tresfellas.queenbee.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class BoldProfileQuestionnaireDTO(
    val version : String,
    val questionnaireItems : List<QuestionnaireItem>
) : Parcelable {
}