package com.tresfellas.queenbee.data.model

import com.tresfellas.queenbee.R
import com.tresfellas.queenbee.utils.*

enum class HttpErrorCode(val code: Int, val message: Int) {
    NO_NETWORK(997, R.string.no_network),
    UNKNOWN_HOST(998, R.string.unknown_host),
    BAD_REQUEST(400, R.string.bad_request),
    UNAUTHORIZED(401, R.string.unauthorized),
    FORBIDDEN(403, R.string.forbidden),
    USER_NOT_FOUND(404, R.string.user_not_found),
    CONFLICT(409, R.string.conflict),
    UNKNOWN_ERROR(999, R.string.unknown_error);

    companion object {
        fun fromThrowable(t: Throwable) =
            when(t) {
                is NoNetworkException -> NO_NETWORK
                is ServerUnreachableException -> UNKNOWN_HOST
                is BadRequest -> BAD_REQUEST
                is Unauthorized -> UNAUTHORIZED
                is Forbidden -> FORBIDDEN
                is UserNotFound -> USER_NOT_FOUND
                is Conflict -> CONFLICT
                else -> UNKNOWN_ERROR
            }
    }
}