package com.dokuwallet.coresdk.data.network.datasource

import android.util.Log
import com.dokuwallet.coresdk.data.network.api.ApiRes
import com.dokuwallet.coresdk.data.network.api.WalletApiService
import com.dokuwallet.coresdk.data.network.parameter.walletparameter.AccountBindingReq
import com.dokuwallet.coresdk.data.network.parameter.walletparameter.AccountCreationReq
import com.dokuwallet.coresdk.data.network.parameter.walletparameter.AuthHeader
import com.dokuwallet.coresdk.data.network.parameter.walletparameter.B2B2CHeader
import com.dokuwallet.coresdk.data.network.parameter.walletparameter.B2BHeader
import com.dokuwallet.coresdk.data.network.parameter.walletparameter.BankInquiryReq
import com.dokuwallet.coresdk.data.network.parameter.walletparameter.BankTransferReq
import com.dokuwallet.coresdk.data.network.parameter.walletparameter.CheckQrisReq
import com.dokuwallet.coresdk.data.network.parameter.walletparameter.DecodeQrReq
import com.dokuwallet.coresdk.data.network.parameter.walletparameter.GenerateQrReq
import com.dokuwallet.coresdk.data.network.parameter.walletparameter.GenerateQrisReq
import com.dokuwallet.coresdk.data.network.parameter.walletparameter.GetTokenB2B2CReq
import com.dokuwallet.coresdk.data.network.parameter.walletparameter.GetTokenB2BReq
import com.dokuwallet.coresdk.data.network.parameter.walletparameter.PaymentQrReq
import com.dokuwallet.coresdk.data.network.parameter.walletparameter.QueryAccountBindingReq
import com.dokuwallet.coresdk.data.network.parameter.walletparameter.QueryQrReq
import com.dokuwallet.coresdk.data.network.parameter.walletparameter.SignOnReq
import com.dokuwallet.coresdk.data.network.parameter.walletparameter.TransferBankReq
import com.dokuwallet.coresdk.data.network.parameter.walletparameter.TransferP2PReq
import com.dokuwallet.coresdk.data.network.parameter.walletparameter.VerifyOtpReq
import com.dokuwallet.coresdk.data.network.response.walletresponse.AccountBindingRes
import com.dokuwallet.coresdk.data.network.response.walletresponse.AccountCreationRes
import com.dokuwallet.coresdk.data.network.response.walletresponse.BankInquiryRes
import com.dokuwallet.coresdk.data.network.response.walletresponse.BankTransferRes
import com.dokuwallet.coresdk.data.network.response.walletresponse.CheckQrisRes
import com.dokuwallet.coresdk.data.network.response.walletresponse.DecodeQrRes
import com.dokuwallet.coresdk.data.network.response.walletresponse.ErrorRes
import com.dokuwallet.coresdk.data.network.response.walletresponse.GenerateQrRes
import com.dokuwallet.coresdk.data.network.response.walletresponse.GenerateQrisRes
import com.dokuwallet.coresdk.data.network.response.walletresponse.GetTokenB2B2CRes
import com.dokuwallet.coresdk.data.network.response.walletresponse.GetTokenB2BRes
import com.dokuwallet.coresdk.data.network.response.walletresponse.PaymentQrRes
import com.dokuwallet.coresdk.data.network.response.walletresponse.QueryAccountBindingRes
import com.dokuwallet.coresdk.data.network.response.walletresponse.QueryQrRes
import com.dokuwallet.coresdk.data.network.response.walletresponse.SignOnRes
import com.dokuwallet.coresdk.data.network.response.walletresponse.TransferBankRes
import com.dokuwallet.coresdk.data.network.response.walletresponse.TransferP2PRes
import com.dokuwallet.coresdk.data.network.response.walletresponse.VerifyOtpRes
import com.dokuwallet.coresdk.vo.ErrorData
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.HttpException

internal class WalletDataSource(private val apiService: WalletApiService) {

    suspend fun signOn(param: SignOnReq): Flow<ApiRes<SignOnRes>> =
        walletNetworkHandler {
            apiService.signOn(
                param.clientId,
                param.clientSecret,
                param.systrace,
                param.words,
                param.version
            )
        }

    suspend fun transferP2PWeb(param: TransferP2PReq): Flow<ApiRes<TransferP2PRes>> =
        walletNetworkHandler {
            apiService.transferP2PWeb(
                param.accessToken,
                param.clientId,
                param.accountId,
                param.urlIntent,
                param.systrace,
                param.words,
                param.version
            )
        }

    suspend fun transferBankWeb(param: TransferBankReq): Flow<ApiRes<TransferBankRes>> =
        walletNetworkHandler {
            apiService.transferBankWeb(
                param.accessToken,
                param.clientId,
                param.accountId,
                param.urlIntent,
                param.transactionId,
                param.systrace,
                param.words,
                param.version
            )
        }

    suspend fun generateQris(param: GenerateQrisReq): Flow<ApiRes<GenerateQrisRes>> =
        walletNetworkHandler {
            apiService.generateQris(
                param.accessToken,
                param.clientId,
                param.dpMallId,
                param.terminalId,
                param.postalCode,
                param.amount,
                param.feeType,
                param.conveniencesFee,
                param.uuid,
                param.clientRequestDateTime,
                param.initiatorProduct,
                param.initiatorSystem,
                param.expiryDate,
                param.words,
                param.version
            )
        }

    suspend fun checkStatusQris(param: CheckQrisReq): Flow<ApiRes<CheckQrisRes>> =
        walletNetworkHandler {
            apiService.checkStatusQris(
                param.accessToken,
                param.clientId,
                param.transactionId,
                param.dpMallId,
                param.words,
                param.version
            )
        }

    suspend fun getTokenB2B(
        authHeader: AuthHeader,
        getTokenB2BReq: GetTokenB2BReq
    ): Flow<ApiRes<GetTokenB2BRes>> =
        walletNetworkHandler {
            apiService.getTokenB2B(
                authHeader.xClientKey,
                authHeader.xTimestamp,
                authHeader.xSignature,
                getTokenB2BReq
            )
        }

    suspend fun getTokenB2B2C(
        header: AuthHeader,
        getTokenB2BReq: GetTokenB2B2CReq
    ): Flow<ApiRes<GetTokenB2B2CRes>> =
        walletNetworkHandler {
            apiService.getTokenB2B2C(
                header.xClientKey,
                header.xTimestamp,
                header.xSignature,
                getTokenB2BReq
            )
        }

    suspend fun generateQr(
        header: B2BHeader,
        generateQrReq: GenerateQrReq
    ): Flow<ApiRes<GenerateQrRes>> =
        walletNetworkHandler {
            apiService.generateQr(
                header.xTimestamp,
                header.xPartnerId,
                header.xExternalId,
                header.xSignature,
                header.authorization,
                generateQrReq
            )
        }

    suspend fun queryQr(
        header: B2BHeader,
        queryQrReq: QueryQrReq
    ): Flow<ApiRes<QueryQrRes>> =
        walletNetworkHandler {
            apiService.queryQr(
                header.xTimestamp,
                header.xPartnerId,
                header.xExternalId,
                header.xSignature,
                header.authorization,
                queryQrReq
            )
        }

    suspend fun accountCreation(
        header: B2BHeader,
        accountCreationReq: AccountCreationReq
    ): Flow<ApiRes<AccountCreationRes>> =
        walletNetworkHandler {
            apiService.accountCreation(
                header.xTimestamp,
                header.xPartnerId,
                header.xExternalId,
                header.xSignature,
                header.authorization,
                accountCreationReq
            )
        }

    suspend fun verifyOtp(
        header: B2BHeader,
        verifyOtpReq: VerifyOtpReq
    ): Flow<ApiRes<VerifyOtpRes>> =
        walletNetworkHandler {
            apiService.verifyOtp(
                header.xTimestamp,
                header.xPartnerId,
                header.xExternalId,
                header.xSignature,
                header.authorization,
                verifyOtpReq
            )
        }

    suspend fun accountBinding(
        header: B2BHeader,
        accountBindingReq: AccountBindingReq
    ): Flow<ApiRes<AccountBindingRes>> =
        walletNetworkHandler {
            apiService.accountBinding(
                header.xTimestamp,
                header.xPartnerId,
                header.xExternalId,
                header.xSignature,
                header.authorization,
                accountBindingReq
            )
        }

    suspend fun queryAccountBinding(
        header: B2BHeader,
        queryAccountBindingReq: QueryAccountBindingReq
    ): Flow<ApiRes<QueryAccountBindingRes>> =
        walletNetworkHandler {
            apiService.queryAccountBinding(
                header.xTimestamp,
                header.xPartnerId,
                header.xExternalId,
                header.xSignature,
                header.authorization,
                queryAccountBindingReq
            )
        }

    suspend fun bankInquiry(
        header: B2BHeader,
        bankInquiryReq: BankInquiryReq
    ): Flow<ApiRes<BankInquiryRes>> =
        walletNetworkHandler {
            apiService.bankInquiry(
                header.xTimestamp,
                header.xPartnerId,
                header.xExternalId,
                header.xSignature,
                header.authorization,
                bankInquiryReq
            )
        }

    suspend fun bankTransfer(
        header: B2B2CHeader,
        bankTransferReq: BankTransferReq
    ): Flow<ApiRes<BankTransferRes>> =
        walletNetworkHandler {
            apiService.bankTransfer(
                header.xTimestamp,
                header.xPartnerId,
                header.xExternalId,
                header.xSignature,
                header.authorization,
                header.authorizationCustomer,
                bankTransferReq
            )
        }

    suspend fun decodeQr(
        header: B2BHeader,
        decodeQrReq: DecodeQrReq
    ): Flow<ApiRes<DecodeQrRes>> =
        walletNetworkHandler {
            apiService.decodeQr(
                header.xTimestamp,
                header.xPartnerId,
                header.xExternalId,
                header.xSignature,
                header.authorization,
                decodeQrReq
            )
        }

    suspend fun paymentQr(
        header: B2B2CHeader,
        paymentQrReq: PaymentQrReq
    ): Flow<ApiRes<PaymentQrRes>> =
        walletNetworkHandler {
            apiService.paymentQr(
                header.xTimestamp,
                header.xPartnerId,
                header.xExternalId,
                header.xSignature,
                header.authorization,
                header.authorizationCustomer,
                paymentQrReq
            )
        }

    private suspend fun <T> walletNetworkHandler(responseCall: suspend () -> T): Flow<ApiRes<T>> =
        flow {
            try {
                val response = responseCall()

                emit(ApiRes.Success(response))
            } catch (e: Exception) {
                Log.d("TAG", "Catch: ${e.message}")
                when (e) {
                    is HttpException -> {
                        val responseBody = e.response()?.errorBody()?.string()
                        val errorRes =
                            Gson().fromJson(responseBody, ErrorRes::class.java)


                        emit(
                            ApiRes.Error(
                                ErrorData(errorRes.responseCode, errorRes.responseMessage)
                            )
                        )
                    }

                    else -> emit(ApiRes.Error(ErrorData(null, e.message)))
                }
            }
        }.flowOn(Dispatchers.IO)
}