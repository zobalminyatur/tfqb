package com.tresfellas.queenbee.api.managers

object TokenManager {
    var accessToken = ""
    var refreshToken = ""

    fun clearToken() {
        accessToken = ""
        refreshToken = ""
    }
}