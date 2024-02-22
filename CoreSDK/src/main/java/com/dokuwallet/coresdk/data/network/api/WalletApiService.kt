package com.dokuwallet.coresdk.data.network.api

import com.dokuwallet.coresdk.data.network.parameter.walletparameter.AccountBindingReq
import com.dokuwallet.coresdk.data.network.parameter.walletparameter.AccountCreationReq
import com.dokuwallet.coresdk.data.network.parameter.walletparameter.BankInquiryReq
import com.dokuwallet.coresdk.data.network.parameter.walletparameter.BankTransferReq
import com.dokuwallet.coresdk.data.network.parameter.walletparameter.DecodeQrReq
import com.dokuwallet.coresdk.data.network.parameter.walletparameter.GenerateQrReq
import com.dokuwallet.coresdk.data.network.parameter.walletparameter.GetTokenB2B2CReq
import com.dokuwallet.coresdk.data.network.parameter.walletparameter.GetTokenB2BReq
import com.dokuwallet.coresdk.data.network.parameter.walletparameter.PaymentQrReq
import com.dokuwallet.coresdk.data.network.parameter.walletparameter.QueryAccountBindingReq
import com.dokuwallet.coresdk.data.network.parameter.walletparameter.QueryQrReq
import com.dokuwallet.coresdk.data.network.parameter.walletparameter.VerifyOtpReq
import com.dokuwallet.coresdk.data.network.response.walletresponse.*
import com.dokuwallet.coresdk.utils.WalletEndPoint
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Header
import retrofit2.http.POST

internal interface WalletApiService {
    @FormUrlEncoded
    @POST("signon")
    suspend fun signOn(
        @Field("clientId") clientId: String,
        @Field("clientSecret") clientSecret: String,
        @Field("systrace") systrace: String,
        @Field("words") words: String,
        @Field("version") version: String,
    ): SignOnRes

    @FormUrlEncoded
    @POST("transferp2pweb")
    suspend fun transferP2PWeb(
        @Field("accessToken") accessToken: String,
        @Field("clientId") clientId: String,
        @Field("accountId") accountId: String,
        @Field("urlIntent") urlIntent: String,
        @Field("systrace") systrace: String,
        @Field("words") words: String,
        @Field("version") version: String,
    ): TransferP2PRes

    @FormUrlEncoded
    @POST("transferbankweb")
    suspend fun transferBankWeb(
        @Field("accessToken") accessToken: String,
        @Field("clientId") clientId: String,
        @Field("accountId") accountId: String,
        @Field("urlIntent") urlIntent: String,
        @Field("transactionId") transactionId: String,
        @Field("systrace") systrace: String,
        @Field("words") words: String,
        @Field("version") version: String,
    ): TransferBankRes

    @FormUrlEncoded
    @POST("generateQris")
    suspend fun generateQris(
        @Field("accessToken") accessToken: String,
        @Field("clientId") clientId: String,
        @Field("dpMallId") dpMallId: String,
        @Field("terminalId") terminalId: String,
        @Field("postalCode") postalCode: String,
        @Field("amount") amount: String? = null,
        @Field("feeType") feeType: String? = null,
        @Field("conveniencesFee") conveniencesFee: String? = null,
        @Field("uuid") uuid: String? = null,
        @Field("clientRequestDateTime") clientRequestDateTime: String? = null,
        @Field("initiatorProduct") initiatorProduct: String? = null,
        @Field("initiatorSystem") initiatorSystem: String? = null,
        @Field("expiryDate") expiryDate: String? = null,
        @Field("words") words: String,
        @Field("version") version: String,
    ): GenerateQrisRes

    @FormUrlEncoded
    @POST("checkstatusqris")
    suspend fun checkStatusQris(
        @Field("accessToken") accessToken: String,
        @Field("clientId") clientId: String,
        @Field("transactionId") transactionId: String,
        @Field("dpMallId") dpMallId: String,
        @Field("words") words: String,
        @Field("version") version: String,
    ): CheckQrisRes

    @POST(WalletEndPoint.getTokenB2B)
    suspend fun getTokenB2B(
        @Header("X-CLIENT-KEY") clientKey: String,
        @Header("X-TIMESTAMP") timestamp: String,
        @Header("X-SIGNATURE") signature: String,
        @Body request: GetTokenB2BReq
    ): GetTokenB2BRes

    @POST(WalletEndPoint.getTokenB2B2C)
    suspend fun getTokenB2B2C(
        @Header("X-CLIENT-KEY") clientKey: String,
        @Header("X-TIMESTAMP") timestamp: String,
        @Header("X-SIGNATURE") signature: String,
        @Body request: GetTokenB2B2CReq
    ): GetTokenB2B2CRes

    @POST(WalletEndPoint.generateQr)
    suspend fun generateQr(
        @Header("X-TIMESTAMP") timestamp: String,
        @Header("X-PARTNER-ID") xPartnerId: String,
        @Header("X-EXTERNAL-ID") xExternalId: String,
        @Header("X-SIGNATURE") signature: String,
        @Header("Authorization") authorization: String,
        @Body request: GenerateQrReq
    ): GenerateQrRes

    @POST(WalletEndPoint.queryQr)
    suspend fun queryQr(
        @Header("X-TIMESTAMP") timestamp: String,
        @Header("X-PARTNER-ID") xPartnerId: String,
        @Header("X-EXTERNAL-ID") xExternalId: String,
        @Header("X-SIGNATURE") signature: String,
        @Header("Authorization") authorization: String,
        @Body request: QueryQrReq
    ): QueryQrRes

    @POST(WalletEndPoint.accountCreation)
    suspend fun accountCreation(
        @Header("X-TIMESTAMP") timestamp: String,
        @Header("X-PARTNER-ID") xPartnerId: String,
        @Header("X-EXTERNAL-ID") xExternalId: String,
        @Header("X-SIGNATURE") signature: String,
        @Header("Authorization") authorization: String,
        @Body request: AccountCreationReq
    ): AccountCreationRes

    @POST(WalletEndPoint.verifyOtp)
    suspend fun verifyOtp(
        @Header("X-TIMESTAMP") timestamp: String,
        @Header("X-PARTNER-ID") xPartnerId: String,
        @Header("X-EXTERNAL-ID") xExternalId: String,
        @Header("X-SIGNATURE") signature: String,
        @Header("Authorization") authorization: String,
        @Body request: VerifyOtpReq
    ): VerifyOtpRes

    @POST(WalletEndPoint.accountBinding)
    suspend fun accountBinding(
        @Header("X-TIMESTAMP") timestamp: String,
        @Header("X-PARTNER-ID") xPartnerId: String,
        @Header("X-EXTERNAL-ID") xExternalId: String,
        @Header("X-SIGNATURE") signature: String,
        @Header("Authorization") authorization: String,
        @Body request: AccountBindingReq
    ): AccountBindingRes

    @POST(WalletEndPoint.queryAccountBinding)
    suspend fun queryAccountBinding(
        @Header("X-TIMESTAMP") timestamp: String,
        @Header("X-PARTNER-ID") xPartnerId: String,
        @Header("X-EXTERNAL-ID") xExternalId: String,
        @Header("X-SIGNATURE") signature: String,
        @Header("Authorization") authorization: String,
        @Body request: QueryAccountBindingReq
    ): QueryAccountBindingRes

    @POST(WalletEndPoint.bankInquiry)
    suspend fun bankInquiry(
        @Header("X-TIMESTAMP") timestamp: String,
        @Header("X-PARTNER-ID") xPartnerId: String,
        @Header("X-EXTERNAL-ID") xExternalId: String,
        @Header("X-SIGNATURE") signature: String,
        @Header("Authorization") authorization: String,
        @Body request: BankInquiryReq
    ): BankInquiryRes

    @POST(WalletEndPoint.bankTransfer)
    suspend fun bankTransfer(
        @Header("X-TIMESTAMP") timestamp: String,
        @Header("X-PARTNER-ID") xPartnerId: String,
        @Header("X-EXTERNAL-ID") xExternalId: String,
        @Header("X-SIGNATURE") signature: String,
        @Header("Authorization") authorization: String,
        @Header("Authorization-Customer") authorizationCustomer: String,
        @Body request: BankTransferReq
    ): BankTransferRes

    @POST(WalletEndPoint.decodeQr)
    suspend fun decodeQr(
        @Header("X-TIMESTAMP") timestamp: String,
        @Header("X-PARTNER-ID") xPartnerId: String,
        @Header("X-EXTERNAL-ID") xExternalId: String,
        @Header("X-SIGNATURE") signature: String,
        @Header("Authorization") authorization: String,
        @Body request: DecodeQrReq
    ): DecodeQrRes

    @POST(WalletEndPoint.paymentQr)
    suspend fun paymentQr(
        @Header("X-TIMESTAMP") timestamp: String,
        @Header("X-PARTNER-ID") xPartnerId: String,
        @Header("X-EXTERNAL-ID") xExternalId: String,
        @Header("X-SIGNATURE") signature: String,
        @Header("Authorization") authorization: String,
        @Header("Authorization-Customer") authorizationCustomer: String,
        @Body request: PaymentQrReq
    ): PaymentQrRes
}