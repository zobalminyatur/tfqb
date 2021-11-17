package com.tresfellas.queenbee.data.model

data class WriteMessageDTO(
    var roomId : String,
    var text : String,
    var from : String,
    var to : String

) {
}