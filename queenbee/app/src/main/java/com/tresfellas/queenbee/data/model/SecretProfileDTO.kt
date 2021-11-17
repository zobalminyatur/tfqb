package com.tresfellas.queenbee.data.model

data class SecretProfileDTO(
    val version : String,
    val questionaireItems : List<QuestionnaireItem>
) {
}