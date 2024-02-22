package com.dokuwallet.coresdk.domain.usecase

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
import com.dokuwallet.coresdk.domain.model.walletmodel.AccountBindingDomain
import com.dokuwallet.coresdk.domain.model.walletmodel.AccountCreationDomain
import com.dokuwallet.coresdk.domain.model.walletmodel.BankInquiryDomain
import com.dokuwallet.coresdk.domain.model.walletmodel.BankTransferDomain
import com.dokuwallet.coresdk.domain.model.walletmodel.CheckQrisDomain
import com.dokuwallet.coresdk.domain.model.walletmodel.DecodeQrDomain
import com.dokuwallet.coresdk.domain.model.walletmodel.GenerateQrDomain
import com.dokuwallet.coresdk.domain.model.walletmodel.GenerateQrisDomain
import com.dokuwallet.coresdk.domain.model.walletmodel.GetTokenB2B2CDomain
import com.dokuwallet.coresdk.domain.model.walletmodel.GetTokenB2BDomain
import com.dokuwallet.coresdk.domain.model.walletmodel.PaymentQrDomain
import com.dokuwallet.coresdk.domain.model.walletmodel.QueryAccountBindingDomain
import com.dokuwallet.coresdk.domain.model.walletmodel.QueryQrDomain
import com.dokuwallet.coresdk.domain.model.walletmodel.SignOnDomain
import com.dokuwallet.coresdk.domain.model.walletmodel.TransferBankDomain
import com.dokuwallet.coresdk.domain.model.walletmodel.TransferP2PDomain
import com.dokuwallet.coresdk.domain.model.walletmodel.VerifyOtpDomain
import com.dokuwallet.coresdk.vo.Resource
import kotlinx.coroutines.flow.Flow

interface WalletDataUseCase {
    fun signOn(param: SignOnReq): Flow<Resource<SignOnDomain>>

    fun transferP2PWeb(param: TransferP2PReq): Flow<Resource<TransferP2PDomain>>

    fun transferBankWeb(param: TransferBankReq): Flow<Resource<TransferBankDomain>>

    fun generateQris(param: GenerateQrisReq): Flow<Resource<GenerateQrisDomain>>

    fun checkStatusQris(param: CheckQrisReq): Flow<Resource<CheckQrisDomain>>

    fun getTokenB2B(
        header: AuthHeader,
        getTokenB2BReq: GetTokenB2BReq
    ): Flow<Resource<GetTokenB2BDomain>>

    fun getTokenB2B2C(
        header: AuthHeader,
        getTokenB2B2CReq: GetTokenB2B2CReq
    ): Flow<Resource<GetTokenB2B2CDomain>>

    fun generateQr(
        header: B2BHeader,
        generateQrReq: GenerateQrReq
    ): Flow<Resource<GenerateQrDomain>>

    fun queryQr(
        header: B2BHeader,
        queryQrReq: QueryQrReq
    ): Flow<Resource<QueryQrDomain>>

    fun accountCreation(
        header: B2BHeader,
        accountCreationReq: AccountCreationReq
    ): Flow<Resource<AccountCreationDomain>>

    fun verifyOtp(header: B2BHeader, verifyOtpReq: VerifyOtpReq): Flow<Resource<VerifyOtpDomain>>

    fun accountBinding(
        header: B2BHeader,
        accoubBindingReq: AccountBindingReq
    ): Flow<Resource<AccountBindingDomain>>

    fun queryAccountBinding(
        header: B2BHeader,
        queryAccountBindingReq: QueryAccountBindingReq
    ): Flow<Resource<QueryAccountBindingDomain>>

    fun bankInquiry(
        header: B2BHeader,
        bankInquiryReq: BankInquiryReq
    ): Flow<Resource<BankInquiryDomain>>

    fun bankTransfer(
        header: B2B2CHeader,
        bankTransferReq: BankTransferReq
    ): Flow<Resource<BankTransferDomain>>

    fun decodeQr(
        header: B2BHeader,
        decodeQrReq: DecodeQrReq
    ): Flow<Resource<DecodeQrDomain>>

    fun paymentQr(
        header: B2B2CHeader,
        paymentQrReq: PaymentQrReq
    ): Flow<Resource<PaymentQrDomain>>
}