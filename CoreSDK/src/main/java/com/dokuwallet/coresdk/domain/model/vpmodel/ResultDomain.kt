package com.dokuwallet.coresdk.domain.model.vpmodel

data class ResultDomain<T>(
    val result: String,
    val message: String? = null,
    val data: T? = null,
    val dateTime: String,
    val errorCode: String? = null,
    val errorDesc: String? = null
)
