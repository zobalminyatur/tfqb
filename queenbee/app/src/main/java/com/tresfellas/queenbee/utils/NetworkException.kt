package com.tresfellas.queenbee.utils

open class NetworkException(error: Throwable): RuntimeException(error)

class NoNetworkException(error: Throwable): NetworkException(error)

class ServerUnreachableException(error: Throwable): NetworkException(error)

class HttpCallFailureException(error: Throwable): NetworkException(error)

class BadRequest(error: Throwable): NetworkException(error)

class Unauthorized(error: Throwable): NetworkException(error)

class Forbidden(error: Throwable): NetworkException(error)

class UserNotFound(error: Throwable): NetworkException(error)

class Conflict(error: Throwable): NetworkException(error)