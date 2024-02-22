package com.dokuwallet.coresdk.data.network.response.walletresponse

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
internal data class TransferBankRes(
    @field:SerializedName("urlTransferBank")
    val urlTransferBank: String? = null,

    @field:SerializedName("clientId")
    val clientId: String? = null,

    @field:SerializedName("responseCode")
    val responseCode: String? = null,

    @field:SerializedName("responseMessage")
    val responseMessage: ResponseMessageRes? = null
)