package com.dokuwallet.coresdk.data.network.parameter.walletparameter

import android.os.Parcelable
import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Keep
data class QueryQrReq(
    @field:SerializedName("originalReferenceNo")
    val originalReferenceNo: String,

    @field:SerializedName("originalPartnerReferenceNo")
    val originalPartnerReferenceNo: String,

    @field:SerializedName("serviceCode")
    val serviceCode: String,

    @field:SerializedName("merchantId")
    val merchantId: String,
)