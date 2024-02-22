package com.dokuwallet.coresdk.data.network.response.walletresponse

import com.google.gson.annotations.SerializedName

data class UserInfoRes(
    @field:SerializedName("publicUserId")
    val publicUserId: String? = null,
)