package com.dokuwallet.coresdk.domain.usecase

import com.dokuwallet.coresdk.data.network.parameter.vpparameter.CaptureIdReq
import com.dokuwallet.coresdk.data.network.parameter.vpparameter.RequestVerificationReq
import com.dokuwallet.coresdk.data.network.parameter.vpparameter.UpdateOcrReq
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
import com.dokuwallet.coresdk.domain.model.vpmodel.CaptureIdDataDomain
import com.dokuwallet.coresdk.domain.model.vpmodel.IdentityDataDomain
import com.dokuwallet.coresdk.domain.model.vpmodel.ResultDomain
import com.dokuwallet.coresdk.domain.model.vpmodel.ThemePropertyDomain
import com.dokuwallet.coresdk.domain.model.vpmodel.VerificationDataDomain
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
import com.dokuwallet.coresdk.domain.repository.IDataRepository
import com.dokuwallet.coresdk.vo.Resource
import kotlinx.coroutines.flow.Flow
import java.io.File


internal class DataInteractor(
    private val iDataRepository: IDataRepository
) : VPDataUseCase, WalletDataUseCase {
    // VP SDK
    override fun requestVerification(
        clientId: String,
        requestId: String,
        requestTimestamp: String,
        signature: String,
        requestVerificationReq: RequestVerificationReq
    ): Flow<Resource<ResultDomain<VerificationDataDomain>>> = iDataRepository.requestVerification(
        clientId,
        requestId,
        requestTimestamp,
        signature,
        requestVerificationReq
    )

    override fun validateToken(token: String): Flow<Resource<ResultDomain<VerificationDataDomain>>> =
        iDataRepository.validateToken(tokenBearer(token))

    override fun getClientTheme(token: String): Flow<Resource<ResultDomain<ThemePropertyDomain>>> =
        iDataRepository.getClientTheme(tokenBearer(token))

    override fun captureId(
        token: String,
        param: CaptureIdReq
    ): Flow<Resource<ResultDomain<CaptureIdDataDomain>>> =
        iDataRepository.captureId(tokenBearer(token), param)

    override fun captureSelfie(
        token: String,
        param: CaptureIdReq
    ): Flow<Resource<ResultDomain<CaptureIdDataDomain>>> =
        iDataRepository.captureSelfie(tokenBearer(token), param)

    override fun eyeBlink(
        token: String,
        file: File,
        index: Int,
        isLast: Boolean
    ): Flow<Resource<ResultDomain<String>>> =
        iDataRepository.eyeBlink(tokenBearer(token), file, index, isLast)

    override fun setStage(
        token: String,
        stage: Int
    ): Flow<Resource<ResultDomain<VerificationDataDomain>>> =
        iDataRepository.setStage(tokenBearer(token), stage)

    override fun requestOcr(token: String): Flow<Resource<ResultDomain<VerificationDataDomain>>> =
        iDataRepository.requestOcr(tokenBearer(token))

    override fun getVerificationData(token: String): Flow<Resource<ResultDomain<IdentityDataDomain>>> =
        iDataRepository.getVerificationData(tokenBearer(token))

    override fun capturedIdUrl(token: String): Flow<Resource<ResultDomain<CaptureIdDataDomain>>> =
        iDataRepository.capturedIdUrl(tokenBearer(token))

    override fun capturedSelfieUrl(token: String): Flow<Resource<ResultDomain<CaptureIdDataDomain>>> =
        iDataRepository.capturedSelfieUrl(tokenBearer(token))

    override fun updateOcr(
        token: String,
        param: UpdateOcrReq
    ): Flow<Resource<ResultDomain<String>>> =
        iDataRepository.updateOcr(tokenBearer(token), param)

    override fun authentication(
        token: String,
        file: File,
        index: Int,
        isLast: Boolean
    ): Flow<Resource<ResultDomain<String>>> =
        iDataRepository.authentication(tokenBearer(token), file, index, isLast)

    override fun register(
        token: String,
        param: CaptureIdReq
    ): Flow<Resource<ResultDomain<String>>> =
        iDataRepository.register(tokenBearer(token), param)

    override fun update(
        token: String,
        param: CaptureIdReq
    ): Flow<Resource<ResultDomain<String>>> =
        iDataRepository.update(tokenBearer(token), param)

    override fun faceMatch(token: String): Flow<Resource<ResultDomain<String>>> =
        iDataRepository.faceMatch(tokenBearer(token))

    // Wallet SDK
    override fun signOn(param: SignOnReq): Flow<Resource<SignOnDomain>> =
        iDataRepository.signOn(param)

    override fun transferP2PWeb(param: TransferP2PReq): Flow<Resource<TransferP2PDomain>> =
        iDataRepository.transferP2PWeb(param)

    override fun transferBankWeb(param: TransferBankReq): Flow<Resource<TransferBankDomain>> =
        iDataRepository.transferBankWeb(param)

    override fun generateQris(param: GenerateQrisReq): Flow<Resource<GenerateQrisDomain>> =
        iDataRepository.generateQris(param)

    override fun checkStatusQris(param: CheckQrisReq): Flow<Resource<CheckQrisDomain>> =
        iDataRepository.checkStatusQris(param)

    override fun getTokenB2B(
        header: AuthHeader,
        getTokenB2BReq: GetTokenB2BReq
    ): Flow<Resource<GetTokenB2BDomain>> =
        iDataRepository.getTokenB2B(header, getTokenB2BReq)

    override fun getTokenB2B2C(
        header: AuthHeader,
        getTokenB2B2CReq: GetTokenB2B2CReq
    ): Flow<Resource<GetTokenB2B2CDomain>> =
        iDataRepository.getTokenB2B2C(header, getTokenB2B2CReq)

    override fun generateQr(
        header: B2BHeader,
        generateQrReq: GenerateQrReq
    ): Flow<Resource<GenerateQrDomain>> =
        iDataRepository.generateQr(header, generateQrReq)

    override fun queryQr(
        header: B2BHeader,
        queryQrReq: QueryQrReq
    ): Flow<Resource<QueryQrDomain>> =
        iDataRepository.queryQr(header, queryQrReq)

    override fun accountCreation(
        header: B2BHeader,
        accountCreationReq: AccountCreationReq
    ): Flow<Resource<AccountCreationDomain>> =
        iDataRepository.accountCreation(header, accountCreationReq)

    override fun verifyOtp(
        header: B2BHeader,
        verifyOtpReq: VerifyOtpReq
    ): Flow<Resource<VerifyOtpDomain>> =
        iDataRepository.verifyOtp(header, verifyOtpReq)

    override fun accountBinding(
        header: B2BHeader,
        accoubBindingReq: AccountBindingReq
    ): Flow<Resource<AccountBindingDomain>> =
        iDataRepository.accountBinding(header, accoubBindingReq)

    override fun queryAccountBinding(
        header: B2BHeader,
        queryAccountBindingReq: QueryAccountBindingReq
    ): Flow<Resource<QueryAccountBindingDomain>> =
        iDataRepository.queryAccountBinding(header, queryAccountBindingReq)

    override fun bankInquiry(
        header: B2BHeader,
        bankInquiryReq: BankInquiryReq
    ): Flow<Resource<BankInquiryDomain>> =
        iDataRepository.bankInquiry(header, bankInquiryReq)

    override fun bankTransfer(
        header: B2B2CHeader,
        bankTransferReq: BankTransferReq
    ): Flow<Resource<BankTransferDomain>> =
        iDataRepository.bankTransfer(header, bankTransferReq)

    override fun decodeQr(
        header: B2BHeader,
        decodeQrReq: DecodeQrReq
    ): Flow<Resource<DecodeQrDomain>> =
        iDataRepository.decodeQr(header, decodeQrReq)

    override fun paymentQr(
        header: B2B2CHeader,
        paymentQrReq: PaymentQrReq
    ): Flow<Resource<PaymentQrDomain>> =
        iDataRepository.paymentQr(header, paymentQrReq)

    private fun tokenBearer(token: String): String = "Bearer ".plus(token)
}