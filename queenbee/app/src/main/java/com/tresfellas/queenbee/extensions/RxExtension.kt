package com.tresfellas.queenbee.extensions


import com.tresfellas.queenbee.utils.*
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import retrofit2.HttpException

import java.net.SocketTimeoutException
import java.net.UnknownHostException

fun <T> Single<T>.mapNetworkErrors(): Single<T> =
    this.onErrorResumeNext { error : Throwable ->
        when (error) {
            is SocketTimeoutException -> {
                Single.error(
                    NoNetworkException(error)
                )
            }
            is UnknownHostException -> Single.error(
                ServerUnreachableException(error)
            )
            is HttpException -> Single.error(
                RxExtension.parseHttpError(error.response()?.code(), error)
            )
            else -> Single.error(error)
        }
    }

fun <T> Observable<T>.mapNetworkErrors(): Observable<T> =
    this.onErrorResumeNext { error : Throwable ->
        when (error) {
            is SocketTimeoutException -> Observable.error(
                NoNetworkException(error)
            )
            is UnknownHostException -> Observable.error(
                ServerUnreachableException(error)
            )
            is HttpException -> Observable.error(
                RxExtension.parseHttpError(error.response()?.code(), error)
            )
            else -> Observable.error(error)
        }
    }

fun Completable.mapNetworkErrors(): Completable =
    this.onErrorResumeNext { error : Throwable ->
        when (error) {
            is SocketTimeoutException -> Completable.error(
                NoNetworkException(error)
            )
            is UnknownHostException -> Completable.error(
                ServerUnreachableException(error)
            )
            is HttpException -> Completable.error(
                RxExtension.parseHttpError(error.response()?.code(), error)
            )
            else -> Completable.error(error)
        }
    }

object RxExtension {
    fun parseHttpError(errorCode: Int?, httpException: HttpException): Throwable {

        return when (errorCode) {
            400 -> BadRequest(httpException)
            401 -> Unauthorized(httpException)
            403 -> Forbidden(httpException)
            404 -> UserNotFound(httpException)
            409 -> Conflict(httpException)
            else -> HttpCallFailureException(httpException)
        }
    }
}