package com.tresfellas.queenbee.api.services

import com.tresfellas.queenbee.data.model.BoldProfileQuestionnaireDTO
import com.tresfellas.queenbee.data.model.UserDTO
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET

interface AdminService {
    @GET("admin/boldprofiles/female")
    fun getFemaleBoldProfiles(
    ): Single<BoldProfileQuestionnaireDTO>

    @GET("admin/boldprofiles/male")
    fun getMaleBoldProfiles(
    ): Single<BoldProfileQuestionnaireDTO>
}