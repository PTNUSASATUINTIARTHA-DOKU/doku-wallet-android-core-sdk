package com.dokuwallet.walletsdk.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
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
import com.dokuwallet.coresdk.domain.usecase.WalletDataUseCase
import com.dokuwallet.coresdk.vo.Resource

class HomeViewModel(private val dataUseCase: WalletDataUseCase) : ViewModel() {
    fun signOn(param: SignOnReq): LiveData<Resource<SignOnDomain>> =
        dataUseCase.signOn(param).asLiveData()

    fun transferP2PWeb(param: TransferP2PReq): LiveData<Resource<TransferP2PDomain>> =
        dataUseCase.transferP2PWeb(param).asLiveData()

    fun transferBankWeb(param: TransferBankReq): LiveData<Resource<TransferBankDomain>> =
        dataUseCase.transferBankWeb(param).asLiveData()

    fun generateQris(param: GenerateQrisReq): LiveData<Resource<GenerateQrisDomain>> =
        dataUseCase.generateQris(param).asLiveData()

    fun checkStatusQris(param: CheckQrisReq): LiveData<Resource<CheckQrisDomain>> =
        dataUseCase.checkStatusQris(param).asLiveData()

    fun getTokenB2B(
        authHeader: AuthHeader,
        getTokenB2BReq: GetTokenB2BReq
    ): LiveData<Resource<GetTokenB2BDomain>> =
        dataUseCase.getTokenB2B(authHeader, getTokenB2BReq).asLiveData()

    fun getTokenB2B2C(
        authHeader: AuthHeader,
        getTokenB2B2CReq: GetTokenB2B2CReq
    ): LiveData<Resource<GetTokenB2B2CDomain>> =
        dataUseCase.getTokenB2B2C(authHeader, getTokenB2B2CReq).asLiveData()

    fun generateQr(
        b2BHeader: B2BHeader,
        generateQrReq: GenerateQrReq
    ): LiveData<Resource<GenerateQrDomain>> =
        dataUseCase.generateQr(b2BHeader, generateQrReq).asLiveData()

    fun queryQr(
        b2BHeader: B2BHeader,
        queryQrReq: QueryQrReq
    ): LiveData<Resource<QueryQrDomain>> =
        dataUseCase.queryQr(b2BHeader, queryQrReq).asLiveData()

    fun accountCreation(
        header: B2BHeader,
        accountCreationReq: AccountCreationReq
    ): LiveData<Resource<AccountCreationDomain>> =
        dataUseCase.accountCreation(header, accountCreationReq).asLiveData()

    fun verifyOtp(
        header: B2BHeader,
        verifyOtpReq: VerifyOtpReq
    ): LiveData<Resource<VerifyOtpDomain>> =
        dataUseCase.verifyOtp(header, verifyOtpReq).asLiveData()

    fun accountBinding(
        header: B2BHeader,
        accountBindingReq: AccountBindingReq
    ): LiveData<Resource<AccountBindingDomain>> =
        dataUseCase.accountBinding(header, accountBindingReq).asLiveData()

    fun queryAccountBinding(
        header: B2BHeader,
        queryAccountBindingReq: QueryAccountBindingReq
    ): LiveData<Resource<QueryAccountBindingDomain>> =
        dataUseCase.queryAccountBinding(header, queryAccountBindingReq).asLiveData()

    fun bankInquiry(
        header: B2BHeader,
        bankInquiryReq: BankInquiryReq
    ): LiveData<Resource<BankInquiryDomain>> =
        dataUseCase.bankInquiry(header, bankInquiryReq).asLiveData()

    fun bankTransfer(
        header: B2B2CHeader,
        bankTransferReq: BankTransferReq
    ): LiveData<Resource<BankTransferDomain>> =
        dataUseCase.bankTransfer(header, bankTransferReq).asLiveData()

    fun decodeQr(
        header: B2BHeader,
        decodeQrReq: DecodeQrReq
    ): LiveData<Resource<DecodeQrDomain>> =
        dataUseCase.decodeQr(header, decodeQrReq).asLiveData()

    fun paymentQr(
        header: B2B2CHeader,
        paymentQrReq: PaymentQrReq
    ): LiveData<Resource<PaymentQrDomain>> =
        dataUseCase.paymentQr(header, paymentQrReq).asLiveData()
}