package com.tresfellas.queenbee.data.model

data class UserListDTO (
    var users: List<UserDTO>,
    var totalCount : Int,
    var currentPage	: Int,
    var totalPages : Int
        ){
}