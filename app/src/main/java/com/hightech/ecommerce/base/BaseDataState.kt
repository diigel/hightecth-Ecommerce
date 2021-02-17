package com.hightech.ecommerce.base

import androidx.appcompat.app.AlertDialog


interface DataStateListener<T> {
    fun onLoading()
    fun onIdle()
    fun onSuccess(data: T)
    fun onFailed(t: Throwable)
}

fun <T>DataState<T>.bindToAlertDialog(alertDialog: AlertDialog?){
    if (this is DataState.Loading){
        if (alertDialog?.isShowing == true) {
            alertDialog.dismiss()
            alertDialog.show()
        }else{
            alertDialog?.show()
        }
    }else {
        alertDialog?.dismiss()
    }
}

fun <T> DataState<T>.setOnListerner(listener: DataStateListener<T>) {
    when (this) {
        is DataState.Idle -> listener.onIdle()
        is DataState.Loading -> listener.onLoading()
        is DataState.Success -> listener.onSuccess(this.data)
        is DataState.Failed -> listener.onFailed(this.exception)
    }
}

sealed class DataState<T> {
    data class Success<T>(val data: T) : DataState<T>()
    data class Failed<T>(val exception: Throwable) : DataState<T>()
    data class Idle<T>(val idle: String = "Idle ...") : DataState<T>()
    data class Loading<T>(val loading: String = "Loading ...") : DataState<T>()
}