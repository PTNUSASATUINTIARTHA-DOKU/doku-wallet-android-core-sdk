package com.dokuwallet.coresdk.data.network.response.walletresponse

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
internal data class TransferP2PRes(
    @field:SerializedName("urlTransferP2P")
    val urlTransferP2P: String? = null,

    @field:SerializedName("responseCode")
    val responseCode: String? = null,

    @field:SerializedName("responseMessage")
    val responseMessage: ResponseMessageRes? = null
)