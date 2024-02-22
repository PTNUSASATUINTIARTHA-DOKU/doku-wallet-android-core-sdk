package com.dokuwallet.coresdk.data

import com.dokuwallet.coresdk.data.local.LocalDataSource
import com.dokuwallet.coresdk.data.network.api.ApiRes
import com.dokuwallet.coresdk.data.network.datasource.VpDataSource
import com.dokuwallet.coresdk.data.network.datasource.WalletDataSource
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
import com.dokuwallet.coresdk.data.network.response.vpresponse.CaptureIdDataRes
import com.dokuwallet.coresdk.data.network.response.vpresponse.IdentityDataRes
import com.dokuwallet.coresdk.data.network.response.vpresponse.ResultRes
import com.dokuwallet.coresdk.data.network.response.vpresponse.ThemePropertyResponse
import com.dokuwallet.coresdk.data.network.response.vpresponse.VerificationDataRes
import com.dokuwallet.coresdk.data.network.response.walletresponse.AccountBindingRes
import com.dokuwallet.coresdk.data.network.response.walletresponse.AccountCreationRes
import com.dokuwallet.coresdk.data.network.response.walletresponse.BankInquiryRes
import com.dokuwallet.coresdk.data.network.response.walletresponse.BankTransferRes
import com.dokuwallet.coresdk.data.network.response.walletresponse.CheckQrisRes
import com.dokuwallet.coresdk.data.network.response.walletresponse.DecodeQrRes
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
import com.dokuwallet.coresdk.utils.DataMapper
import com.dokuwallet.coresdk.utils.DataMapper.toDomain
import com.dokuwallet.coresdk.vo.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import java.io.File

internal class DataRepository(
    private val vpDataSource: VpDataSource,
    private val walletDataSource: WalletDataSource,
    private val localDataSource: LocalDataSource
) : IDataRepository {
    // VP SDK
    override fun getVerificationData(token: String): Flow<Resource<ResultDomain<IdentityDataDomain>>> =
        object :
            NetworkBoundResource<ResultDomain<IdentityDataDomain>, ResultRes<IdentityDataRes>>() {
            override fun shouldFetch(data: ResultDomain<IdentityDataDomain>?): Boolean = true

            override suspend fun createCall(): Flow<ApiRes<ResultRes<IdentityDataRes>>> =
                vpDataSource.getVerificationData(token)

            override suspend fun load(data: ResultRes<IdentityDataRes>): Flow<ResultDomain<IdentityDataDomain>> =
                listOf(DataMapper.resultResponseToDomain(
                    data, data.data?.let { DataMapper.identityDataResponseToDomain(it) }
                )).asFlow()
        }.asFlow()

    override fun capturedIdUrl(token: String): Flow<Resource<ResultDomain<CaptureIdDataDomain>>> =
        object :
            NetworkBoundResource<ResultDomain<CaptureIdDataDomain>, ResultRes<CaptureIdDataRes>>() {
            override fun shouldFetch(data: ResultDomain<CaptureIdDataDomain>?): Boolean = true

            override suspend fun createCall(): Flow<ApiRes<ResultRes<CaptureIdDataRes>>> =
                vpDataSource.capturedIdUrl(token)

            override suspend fun load(data: ResultRes<CaptureIdDataRes>): Flow<ResultDomain<CaptureIdDataDomain>> =
                listOf(DataMapper.resultResponseToDomain(
                    data, data.data?.let { DataMapper.captureIdDataResponseToDomain(it) }
                )).asFlow()
        }.asFlow()

    override fun capturedSelfieUrl(token: String): Flow<Resource<ResultDomain<CaptureIdDataDomain>>> =
        object :
            NetworkBoundResource<ResultDomain<CaptureIdDataDomain>, ResultRes<CaptureIdDataRes>>() {
            override fun shouldFetch(data: ResultDomain<CaptureIdDataDomain>?): Boolean = true

            override suspend fun createCall(): Flow<ApiRes<ResultRes<CaptureIdDataRes>>> =
                vpDataSource.capturedSelfieUrl(token)

            override suspend fun load(data: ResultRes<CaptureIdDataRes>): Flow<ResultDomain<CaptureIdDataDomain>> =
                listOf(DataMapper.resultResponseToDomain(
                    data, data.data?.let { DataMapper.captureIdDataResponseToDomain(it) }
                )).asFlow()
        }.asFlow()

    override fun updateOcr(
        token: String,
        param: UpdateOcrReq
    ): Flow<Resource<ResultDomain<String>>> =
        object : NetworkBoundResource<ResultDomain<String>, ResultRes<String>>() {
            override fun shouldFetch(data: ResultDomain<String>?): Boolean = true

            override suspend fun createCall(): Flow<ApiRes<ResultRes<String>>> =
                vpDataSource.updateOcr(token, param)

            override suspend fun load(data: ResultRes<String>): Flow<ResultDomain<String>> =
                listOf(DataMapper.resultResponseToDomain(data)).asFlow()
        }.asFlow()

    override fun eyeBlink(
        token: String,
        file: File,
        index: Int,
        isLast: Boolean
    ): Flow<Resource<ResultDomain<String>>> =
        object : NetworkBoundResource<ResultDomain<String>, ResultRes<String>>() {
            override fun shouldFetch(data: ResultDomain<String>?): Boolean = true

            override suspend fun createCall(): Flow<ApiRes<ResultRes<String>>> =
                vpDataSource.eyeBlink(token, file, index, isLast)

            override suspend fun load(data: ResultRes<String>): Flow<ResultDomain<String>> =
                listOf(DataMapper.resultResponseToDomain(data)).asFlow()
        }.asFlow()

    override fun getClientTheme(token: String): Flow<Resource<ResultDomain<ThemePropertyDomain>>> =
        object :
            NetworkBoundResource<ResultDomain<ThemePropertyDomain>, ResultRes<ThemePropertyResponse>>() {
            override fun shouldFetch(data: ResultDomain<ThemePropertyDomain>?): Boolean = true

            override suspend fun createCall(): Flow<ApiRes<ResultRes<ThemePropertyResponse>>> =
                vpDataSource.getClientTheme(token)

            override suspend fun load(data: ResultRes<ThemePropertyResponse>): Flow<ResultDomain<ThemePropertyDomain>> =
                listOf(DataMapper.resultResponseToDomain(
                    data, data.data?.let { DataMapper.themePropertyResponseToDomain(it) }
                )).asFlow()
        }.asFlow()

    override fun validateToken(token: String): Flow<Resource<ResultDomain<VerificationDataDomain>>> =
        object :
            NetworkBoundResource<ResultDomain<VerificationDataDomain>, ResultRes<VerificationDataRes>>() {
            override fun shouldFetch(data: ResultDomain<VerificationDataDomain>?): Boolean = true

            override suspend fun createCall(): Flow<ApiRes<ResultRes<VerificationDataRes>>> =
                vpDataSource.validateToken(token)

            override suspend fun load(data: ResultRes<VerificationDataRes>): Flow<ResultDomain<VerificationDataDomain>> =
                listOf(DataMapper.resultResponseToDomain(
                    data, data.data?.let { DataMapper.verificationDataResponseToDomain(it) }
                )).asFlow()
        }.asFlow()

    override fun requestVerification(
        clientId: String,
        requestId: String,
        requestTimestamp: String,
        signature: String,
        requestVerificationReq: RequestVerificationReq
    ): Flow<Resource<ResultDomain<VerificationDataDomain>>> =
        object :
            NetworkBoundResource<ResultDomain<VerificationDataDomain>, ResultRes<VerificationDataRes>>() {
            override fun shouldFetch(data: ResultDomain<VerificationDataDomain>?): Boolean = true

            override suspend fun createCall(): Flow<ApiRes<ResultRes<VerificationDataRes>>> =
                vpDataSource.requestVerification(
                    clientId,
                    requestId,
                    requestTimestamp,
                    signature,
                    requestVerificationReq
                )

            override suspend fun load(data: ResultRes<VerificationDataRes>): Flow<ResultDomain<VerificationDataDomain>> =
                listOf(DataMapper.resultResponseToDomain(
                    data, data.data?.let { DataMapper.verificationDataResponseToDomain(it) }
                )).asFlow()
        }.asFlow()

    override fun captureId(
        token: String,
        param: CaptureIdReq
    ): Flow<Resource<ResultDomain<CaptureIdDataDomain>>> =
        object :
            NetworkBoundResource<ResultDomain<CaptureIdDataDomain>, ResultRes<CaptureIdDataRes>>() {
            override fun shouldFetch(data: ResultDomain<CaptureIdDataDomain>?): Boolean = true

            override suspend fun createCall(): Flow<ApiRes<ResultRes<CaptureIdDataRes>>> =
                vpDataSource.captureId(token, param)

            override suspend fun load(data: ResultRes<CaptureIdDataRes>): Flow<ResultDomain<CaptureIdDataDomain>> =
                listOf(DataMapper.resultResponseToDomain(
                    data, data.data?.let { DataMapper.captureIdDataResponseToDomain(it) }
                )).asFlow()
        }.asFlow()

    override fun captureSelfie(
        token: String,
        param: CaptureIdReq
    ): Flow<Resource<ResultDomain<CaptureIdDataDomain>>> =
        object :
            NetworkBoundResource<ResultDomain<CaptureIdDataDomain>, ResultRes<CaptureIdDataRes>>() {
            override fun shouldFetch(data: ResultDomain<CaptureIdDataDomain>?): Boolean = true

            override suspend fun createCall(): Flow<ApiRes<ResultRes<CaptureIdDataRes>>> =
                vpDataSource.captureSelfie(token, param)

            override suspend fun load(data: ResultRes<CaptureIdDataRes>): Flow<ResultDomain<CaptureIdDataDomain>> =
                listOf(DataMapper.resultResponseToDomain(
                    data, data.data?.let { DataMapper.captureIdDataResponseToDomain(it) }
                )).asFlow()
        }.asFlow()

    override fun setStage(
        token: String,
        stage: Int
    ): Flow<Resource<ResultDomain<VerificationDataDomain>>> =
        object :
            NetworkBoundResource<ResultDomain<VerificationDataDomain>, ResultRes<VerificationDataRes>>() {
            override fun shouldFetch(data: ResultDomain<VerificationDataDomain>?): Boolean = true

            override suspend fun createCall(): Flow<ApiRes<ResultRes<VerificationDataRes>>> =
                vpDataSource.setStage(token, stage)

            override suspend fun load(data: ResultRes<VerificationDataRes>): Flow<ResultDomain<VerificationDataDomain>> =
                listOf(DataMapper.resultResponseToDomain(
                    data, data.data?.let { DataMapper.verificationDataResponseToDomain(it) }
                )).asFlow()
        }.asFlow()

    override fun requestOcr(token: String): Flow<Resource<ResultDomain<VerificationDataDomain>>> =
        object :
            NetworkBoundResource<ResultDomain<VerificationDataDomain>, ResultRes<VerificationDataRes>>() {
            override fun shouldFetch(data: ResultDomain<VerificationDataDomain>?): Boolean =
                true

            override suspend fun createCall(): Flow<ApiRes<ResultRes<VerificationDataRes>>> =
                vpDataSource.requestOcr(token)

            override suspend fun load(data: ResultRes<VerificationDataRes>): Flow<ResultDomain<VerificationDataDomain>> =
                listOf(DataMapper.resultResponseToDomain(
                    data, data.data?.let { DataMapper.verificationDataResponseToDomain(it) }
                )).asFlow()
        }.asFlow()

    override fun authentication(
        token: String,
        file: File,
        index: Int,
        isLast: Boolean
    ): Flow<Resource<ResultDomain<String>>> =
        object : NetworkBoundResource<ResultDomain<String>, ResultRes<String>>() {
            override fun shouldFetch(data: ResultDomain<String>?): Boolean = true

            override suspend fun createCall(): Flow<ApiRes<ResultRes<String>>> =
                vpDataSource.authentication(token, file, index, isLast)

            override suspend fun load(data: ResultRes<String>): Flow<ResultDomain<String>> =
                listOf(DataMapper.resultResponseToDomain(data)).asFlow()
        }.asFlow()

    override fun register(
        token: String,
        param: CaptureIdReq
    ): Flow<Resource<ResultDomain<String>>> =
        object : NetworkBoundResource<ResultDomain<String>, ResultRes<String>>() {
            override fun shouldFetch(data: ResultDomain<String>?): Boolean = true

            override suspend fun createCall(): Flow<ApiRes<ResultRes<String>>> =
                vpDataSource.register(token, param)

            override suspend fun load(data: ResultRes<String>): Flow<ResultDomain<String>> =
                listOf(DataMapper.resultResponseToDomain(data)).asFlow()
        }.asFlow()

    override fun update(
        token: String,
        param: CaptureIdReq
    ): Flow<Resource<ResultDomain<String>>> =
        object : NetworkBoundResource<ResultDomain<String>, ResultRes<String>>() {
            override fun shouldFetch(data: ResultDomain<String>?): Boolean = true

            override suspend fun createCall(): Flow<ApiRes<ResultRes<String>>> =
                vpDataSource.update(token, param)

            override suspend fun load(data: ResultRes<String>): Flow<ResultDomain<String>> =
                listOf(DataMapper.resultResponseToDomain(data)).asFlow()
        }.asFlow()

    override fun faceMatch(token: String): Flow<Resource<ResultDomain<String>>> =
        object : NetworkBoundResource<ResultDomain<String>, ResultRes<String>>() {
            override fun shouldFetch(data: ResultDomain<String>?): Boolean = true

            override suspend fun createCall(): Flow<ApiRes<ResultRes<String>>> =
                vpDataSource.faceMatch(token)

            override suspend fun load(data: ResultRes<String>): Flow<ResultDomain<String>> =
                listOf(DataMapper.resultResponseToDomain(data)).asFlow()
        }.asFlow()

    // Wallet SDK
    override fun signOn(param: SignOnReq): Flow<Resource<SignOnDomain>> =
        object : NetworkBoundResource<SignOnDomain, SignOnRes>() {
            override fun shouldFetch(data: SignOnDomain?): Boolean = true

            override suspend fun createCall(): Flow<ApiRes<SignOnRes>> =
                walletDataSource.signOn(param)

            override suspend fun load(data: SignOnRes): Flow<SignOnDomain> =
                listOf(DataMapper.signOnResponseToDomain(data)).asFlow()
        }.asFlow()

    override fun transferP2PWeb(param: TransferP2PReq): Flow<Resource<TransferP2PDomain>> =
        object : NetworkBoundResource<TransferP2PDomain, TransferP2PRes>() {
            override fun shouldFetch(data: TransferP2PDomain?): Boolean = true

            override suspend fun createCall(): Flow<ApiRes<TransferP2PRes>> =
                walletDataSource.transferP2PWeb(param)

            override suspend fun load(data: TransferP2PRes): Flow<TransferP2PDomain> =
                listOf(DataMapper.transferP2PResponseToDomain(data)).asFlow()
        }.asFlow()

    override fun transferBankWeb(param: TransferBankReq): Flow<Resource<TransferBankDomain>> =
        object : NetworkBoundResource<TransferBankDomain, TransferBankRes>() {
            override fun shouldFetch(data: TransferBankDomain?): Boolean = true

            override suspend fun createCall(): Flow<ApiRes<TransferBankRes>> =
                walletDataSource.transferBankWeb(param)

            override suspend fun load(data: TransferBankRes): Flow<TransferBankDomain> =
                listOf(DataMapper.transferBankResponseToDomain(data)).asFlow()
        }.asFlow()

    override fun generateQris(param: GenerateQrisReq): Flow<Resource<GenerateQrisDomain>> =
        object : NetworkBoundResource<GenerateQrisDomain, GenerateQrisRes>() {
            override fun shouldFetch(data: GenerateQrisDomain?): Boolean = true

            override suspend fun createCall(): Flow<ApiRes<GenerateQrisRes>> =
                walletDataSource.generateQris(param)

            override suspend fun load(data: GenerateQrisRes): Flow<GenerateQrisDomain> =
                listOf(DataMapper.generateQrisResponseToDomain(data)).asFlow()
        }.asFlow()

    override fun checkStatusQris(param: CheckQrisReq): Flow<Resource<CheckQrisDomain>> =
        object : NetworkBoundResource<CheckQrisDomain, CheckQrisRes>() {
            override fun shouldFetch(data: CheckQrisDomain?): Boolean = true

            override suspend fun createCall(): Flow<ApiRes<CheckQrisRes>>? =
                walletDataSource.checkStatusQris(param)

            override suspend fun load(data: CheckQrisRes): Flow<CheckQrisDomain>? =
                listOf(DataMapper.checkQrisResponseToDomain(data)).asFlow()
        }.asFlow()

    override fun getTokenB2B(
        header: AuthHeader,
        getTokenB2BReq: GetTokenB2BReq
    ): Flow<Resource<GetTokenB2BDomain>> =
        object : NetworkBoundResource<GetTokenB2BDomain, GetTokenB2BRes>() {
            override fun shouldFetch(data: GetTokenB2BDomain?): Boolean = true

            override suspend fun createCall(): Flow<ApiRes<GetTokenB2BRes>>? =
                walletDataSource.getTokenB2B(header, getTokenB2BReq)

            override suspend fun load(data: GetTokenB2BRes): Flow<GetTokenB2BDomain>? =
                listOf(data.toDomain()).asFlow()
        }.asFlow()

    override fun getTokenB2B2C(
        header: AuthHeader,
        getTokenB2B2CReq: GetTokenB2B2CReq
    ): Flow<Resource<GetTokenB2B2CDomain>> =
        object : NetworkBoundResource<GetTokenB2B2CDomain, GetTokenB2B2CRes>() {
            override fun shouldFetch(data: GetTokenB2B2CDomain?): Boolean = true

            override suspend fun createCall(): Flow<ApiRes<GetTokenB2B2CRes>>? =
                walletDataSource.getTokenB2B2C(header, getTokenB2B2CReq)

            override suspend fun load(data: GetTokenB2B2CRes): Flow<GetTokenB2B2CDomain>? =
                listOf(data.toDomain()).asFlow()
        }.asFlow()

    override fun generateQr(
        header: B2BHeader,
        generateQrReq: GenerateQrReq
    ): Flow<Resource<GenerateQrDomain>> =
        object : NetworkBoundResource<GenerateQrDomain, GenerateQrRes>() {
            override fun shouldFetch(data: GenerateQrDomain?): Boolean = true

            override suspend fun createCall(): Flow<ApiRes<GenerateQrRes>>? =
                walletDataSource.generateQr(header, generateQrReq)

            override suspend fun load(data: GenerateQrRes): Flow<GenerateQrDomain>? =
                listOf(data.toDomain()).asFlow()
        }.asFlow()

    override fun queryQr(
        header: B2BHeader,
        queryQrReq: QueryQrReq
    ): Flow<Resource<QueryQrDomain>> =
        object : NetworkBoundResource<QueryQrDomain, QueryQrRes>() {
            override fun shouldFetch(data: QueryQrDomain?): Boolean = true

            override suspend fun createCall(): Flow<ApiRes<QueryQrRes>>? =
                walletDataSource.queryQr(header, queryQrReq)

            override suspend fun load(data: QueryQrRes): Flow<QueryQrDomain>? =
                listOf(data.toDomain()).asFlow()
        }.asFlow()

    override fun accountCreation(
        header: B2BHeader,
        accountCreationReq: AccountCreationReq
    ): Flow<Resource<AccountCreationDomain>> =
        object : NetworkBoundResource<AccountCreationDomain, AccountCreationRes>() {
            override fun shouldFetch(data: AccountCreationDomain?): Boolean = true

            override suspend fun createCall(): Flow<ApiRes<AccountCreationRes>>? =
                walletDataSource.accountCreation(header, accountCreationReq)

            override suspend fun load(data: AccountCreationRes): Flow<AccountCreationDomain>? =
                listOf(data.toDomain()).asFlow()
        }.asFlow()

    override fun verifyOtp(
        header: B2BHeader,
        verifyOtpReq: VerifyOtpReq
    ): Flow<Resource<VerifyOtpDomain>> =
        object : NetworkBoundResource<VerifyOtpDomain, VerifyOtpRes>() {
            override fun shouldFetch(data: VerifyOtpDomain?): Boolean = true

            override suspend fun createCall(): Flow<ApiRes<VerifyOtpRes>>? =
                walletDataSource.verifyOtp(header, verifyOtpReq)

            override suspend fun load(data: VerifyOtpRes): Flow<VerifyOtpDomain>? =
                listOf(data.toDomain()).asFlow()
        }.asFlow()

    override fun accountBinding(
        header: B2BHeader,
        accountBindingReq: AccountBindingReq
    ): Flow<Resource<AccountBindingDomain>> =
        object : NetworkBoundResource<AccountBindingDomain, AccountBindingRes>() {
            override fun shouldFetch(data: AccountBindingDomain?): Boolean = true

            override suspend fun createCall(): Flow<ApiRes<AccountBindingRes>>? =
                walletDataSource.accountBinding(header, accountBindingReq)

            override suspend fun load(data: AccountBindingRes): Flow<AccountBindingDomain>? =
                listOf(data.toDomain()).asFlow()
        }.asFlow()

    override fun queryAccountBinding(
        header: B2BHeader,
        queryAccountBindingReq: QueryAccountBindingReq
    ): Flow<Resource<QueryAccountBindingDomain>> =
        object : NetworkBoundResource<QueryAccountBindingDomain, QueryAccountBindingRes>() {
            override fun shouldFetch(data: QueryAccountBindingDomain?): Boolean = true

            override suspend fun createCall(): Flow<ApiRes<QueryAccountBindingRes>>? =
                walletDataSource.queryAccountBinding(header, queryAccountBindingReq)

            override suspend fun load(data: QueryAccountBindingRes): Flow<QueryAccountBindingDomain>? =
                listOf(data.toDomain()).asFlow()
        }.asFlow()

    override fun bankInquiry(
        header: B2BHeader,
        bankInquiryReq: BankInquiryReq
    ): Flow<Resource<BankInquiryDomain>> =
        object : NetworkBoundResource<BankInquiryDomain, BankInquiryRes>() {
            override fun shouldFetch(data: BankInquiryDomain?): Boolean = true

            override suspend fun createCall(): Flow<ApiRes<BankInquiryRes>>? =
                walletDataSource.bankInquiry(header, bankInquiryReq)

            override suspend fun load(data: BankInquiryRes): Flow<BankInquiryDomain>? =
                listOf(data.toDomain()).asFlow()
        }.asFlow()

    override fun bankTransfer(
        header: B2B2CHeader,
        bankTransferReq: BankTransferReq
    ): Flow<Resource<BankTransferDomain>> =
        object : NetworkBoundResource<BankTransferDomain, BankTransferRes>() {
            override fun shouldFetch(data: BankTransferDomain?): Boolean = true

            override suspend fun createCall(): Flow<ApiRes<BankTransferRes>>? =
                walletDataSource.bankTransfer(header, bankTransferReq)

            override suspend fun load(data: BankTransferRes): Flow<BankTransferDomain>? =
                listOf(data.toDomain()).asFlow()
        }.asFlow()

    override fun decodeQr(
        header: B2BHeader,
        decodeQrReq: DecodeQrReq
    ): Flow<Resource<DecodeQrDomain>> =
        object : NetworkBoundResource<DecodeQrDomain, DecodeQrRes>() {
            override fun shouldFetch(data: DecodeQrDomain?): Boolean = true

            override suspend fun createCall(): Flow<ApiRes<DecodeQrRes>>? =
                walletDataSource.decodeQr(header, decodeQrReq)

            override suspend fun load(data: DecodeQrRes): Flow<DecodeQrDomain>? =
                listOf(data.toDomain()).asFlow()
        }.asFlow()

    override fun paymentQr(
        header: B2B2CHeader,
        paymentQrReq: PaymentQrReq
    ): Flow<Resource<PaymentQrDomain>> =
        object : NetworkBoundResource<PaymentQrDomain, PaymentQrRes>() {
            override fun shouldFetch(data: PaymentQrDomain?): Boolean = true

            override suspend fun createCall(): Flow<ApiRes<PaymentQrRes>>? =
                walletDataSource.paymentQr(header, paymentQrReq)

            override suspend fun load(data: PaymentQrRes): Flow<PaymentQrDomain>? =
                listOf(data.toDomain()).asFlow()
        }.asFlow()
}