package com.tresfellas.queenbee.utils

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.CompletableTransformer
import io.reactivex.rxjava3.core.MaybeTransformer
import io.reactivex.rxjava3.core.ObservableTransformer
import io.reactivex.rxjava3.core.SingleTransformer
import io.reactivex.rxjava3.schedulers.Schedulers

object RxUtil {
    private val schedulersSingleTransformer: SingleTransformer<Any, Any> =
        SingleTransformer { single ->
            single
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
        }

    internal fun <T> applySingleSchedulers(): SingleTransformer<T, T> {
        return schedulersSingleTransformer as SingleTransformer<T, T>
    }

    private val schedulersObservableTransformer: ObservableTransformer<Any, Any> =
        ObservableTransformer { upstream ->
            upstream
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
        }

    internal fun <T> applyObservableSchedulers(): ObservableTransformer<T, T> {
        return schedulersObservableTransformer as ObservableTransformer<T, T>
    }

    private val schedulersMaybeTransformer: MaybeTransformer<Any, Any> =
        MaybeTransformer { maybe ->
            maybe
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
        }

    internal fun <T> applyMaybeSchedulers(): MaybeTransformer<T, T> {
        return schedulersMaybeTransformer as MaybeTransformer<T, T>
    }

    fun completableTransformer(): CompletableTransformer =
        CompletableTransformer { completable ->
            completable
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
        }

    fun completableTransformerObserveOnUI(): CompletableTransformer =
        CompletableTransformer { completable ->
            completable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
        }
}