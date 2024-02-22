package com.dokuwallet.coresdk.data.network.api

import com.dokuwallet.coresdk.vo.ErrorData

internal sealed class ApiRes<out R> {
    data class Success<out T>(val data: T) : ApiRes<T>()
    data class Error(val error: ErrorData) : ApiRes<Nothing>()
}