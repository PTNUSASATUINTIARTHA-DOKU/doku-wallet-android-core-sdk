package com.dokuwallet.coresdk.utils

import com.dokuwallet.coresdk.data.network.response.vpresponse.ButtonPropertyRes
import com.dokuwallet.coresdk.data.network.response.vpresponse.CaptureIdDataRes
import com.dokuwallet.coresdk.data.network.response.vpresponse.IdentityDataRes
import com.dokuwallet.coresdk.data.network.response.vpresponse.ResultRes
import com.dokuwallet.coresdk.data.network.response.vpresponse.ThemePropertyResponse
import com.dokuwallet.coresdk.data.network.response.vpresponse.VerificationDataRes
import com.dokuwallet.coresdk.data.network.response.walletresponse.AccountBindingRes
import com.dokuwallet.coresdk.data.network.response.walletresponse.AccountCreationRes
import com.dokuwallet.coresdk.data.network.response.walletresponse.AdditionalInfoRes
import com.dokuwallet.coresdk.data.network.response.walletresponse.AmountRes
import com.dokuwallet.coresdk.data.network.response.walletresponse.BankInquiryRes
import com.dokuwallet.coresdk.data.network.response.walletresponse.BankTransferRes
import com.dokuwallet.coresdk.data.network.response.walletresponse.CheckQrisRes
import com.dokuwallet.coresdk.data.network.response.walletresponse.DecodeQrRes
import com.dokuwallet.coresdk.data.network.response.walletresponse.GenerateQrRes
import com.dokuwallet.coresdk.data.network.response.walletresponse.GenerateQrisRes
import com.dokuwallet.coresdk.data.network.response.walletresponse.GetTokenB2B2CRes
import com.dokuwallet.coresdk.data.network.response.walletresponse.GetTokenB2BRes
import com.dokuwallet.coresdk.data.network.response.walletresponse.PaymentQrRes
import com.dokuwallet.coresdk.data.network.response.walletresponse.QParamsRes
import com.dokuwallet.coresdk.data.network.response.walletresponse.QueryAccountBindingRes
import com.dokuwallet.coresdk.data.network.response.walletresponse.QueryQrRes
import com.dokuwallet.coresdk.data.network.response.walletresponse.SignOnRes
import com.dokuwallet.coresdk.data.network.response.walletresponse.TransferBankRes
import com.dokuwallet.coresdk.data.network.response.walletresponse.TransferP2PRes
import com.dokuwallet.coresdk.data.network.response.walletresponse.UserInfoRes
import com.dokuwallet.coresdk.data.network.response.walletresponse.VerifyOtpRes
import com.dokuwallet.coresdk.domain.model.vpmodel.ButtonPropertyDomain
import com.dokuwallet.coresdk.domain.model.vpmodel.CaptureIdDataDomain
import com.dokuwallet.coresdk.domain.model.vpmodel.IdentityDataDomain
import com.dokuwallet.coresdk.domain.model.vpmodel.ResultDomain
import com.dokuwallet.coresdk.domain.model.vpmodel.ThemePropertyDomain
import com.dokuwallet.coresdk.domain.model.vpmodel.VerificationDataDomain
import com.dokuwallet.coresdk.domain.model.walletmodel.AccountBindingDomain
import com.dokuwallet.coresdk.domain.model.walletmodel.AccountCreationDomain
import com.dokuwallet.coresdk.domain.model.walletmodel.AdditionalInfoDomain
import com.dokuwallet.coresdk.domain.model.walletmodel.AmountDomain
import com.dokuwallet.coresdk.domain.model.walletmodel.BankInquiryDomain
import com.dokuwallet.coresdk.domain.model.walletmodel.BankTransferDomain
import com.dokuwallet.coresdk.domain.model.walletmodel.CheckQrisDomain
import com.dokuwallet.coresdk.domain.model.walletmodel.DecodeQrDomain
import com.dokuwallet.coresdk.domain.model.walletmodel.GenerateQrDomain
import com.dokuwallet.coresdk.domain.model.walletmodel.GenerateQrisDomain
import com.dokuwallet.coresdk.domain.model.walletmodel.GetTokenB2B2CDomain
import com.dokuwallet.coresdk.domain.model.walletmodel.GetTokenB2BDomain
import com.dokuwallet.coresdk.domain.model.walletmodel.PaymentQrDomain
import com.dokuwallet.coresdk.domain.model.walletmodel.QParamsDomain
import com.dokuwallet.coresdk.domain.model.walletmodel.QueryAccountBindingDomain
import com.dokuwallet.coresdk.domain.model.walletmodel.QueryQrDomain
import com.dokuwallet.coresdk.domain.model.walletmodel.SignOnDomain
import com.dokuwallet.coresdk.domain.model.walletmodel.TransferBankDomain
import com.dokuwallet.coresdk.domain.model.walletmodel.TransferP2PDomain
import com.dokuwallet.coresdk.domain.model.walletmodel.UserInfoDomain
import com.dokuwallet.coresdk.domain.model.walletmodel.VerifyOtpDomain

internal object DataMapper {
    fun verificationDataResponseToDomain(input: VerificationDataRes): VerificationDataDomain =
        VerificationDataDomain(
            input.link,
            input.stage
        )

    fun themePropertyResponseToDomain(input: ThemePropertyResponse): ThemePropertyDomain =
        ThemePropertyDomain(
            logoLink = input.logoLink,
            backgroundColor = input.backgroundColor,
            primaryButton = buttonPropertyResponseToDomain(input.primaryButton),
            secondaryButton = buttonPropertyResponseToDomain(input.secondaryButton)
        )

    private fun buttonPropertyResponseToDomain(input: ButtonPropertyRes): ButtonPropertyDomain =
        ButtonPropertyDomain(
            background = input.background,
            border = input.border,
            color = input.color
        )

    fun captureIdDataResponseToDomain(input: CaptureIdDataRes): CaptureIdDataDomain =
        CaptureIdDataDomain(
            url = input.url
        )

    fun identityDataResponseToDomain(input: IdentityDataRes): IdentityDataDomain =
        IdentityDataDomain(
            idNumber = input.idNumber,
            name = input.name,
            religion = input.religion,
            gender = input.gender,
            address = input.address,
            bloodType = input.bloodType,
            district = input.district
        )

    fun signOnResponseToDomain(input: SignOnRes): SignOnDomain =
        SignOnDomain(
            accessToken = input.accessToken,
            expiresIn = input.expiresIn,
            clientId = input.clientId,
            responseCode = input.responseCode,
            responseMessage = input.responseMessage?.id,
        )

    fun transferP2PResponseToDomain(input: TransferP2PRes): TransferP2PDomain =
        TransferP2PDomain(
            urlTransferP2P = input.urlTransferP2P,
            responseCode = input.responseCode,
            responseMessage = input.responseMessage?.id,
        )

    fun transferBankResponseToDomain(input: TransferBankRes): TransferBankDomain =
        TransferBankDomain(
            urlTransferBank = input.urlTransferBank,
            clientId = input.clientId,
            responseCode = input.responseCode,
            responseMessage = input.responseMessage?.id,
        )

    fun generateQrisResponseToDomain(input: GenerateQrisRes): GenerateQrisDomain =
        GenerateQrisDomain(
            qrCode = input.qrCode,
            transactionId = input.transactionId,
            responseCode = input.responseCode,
            responseMessage = input.responseMessage?.id
        )

    fun checkQrisResponseToDomain(input: CheckQrisRes): CheckQrisDomain =
        CheckQrisDomain(
            dpMallId = input.dpMallId,
            transactionId = input.transactionId,
            amount = input.amount,
            result = input.result,
            transactionDateTime = input.transactionDateTime,
            referenceId = input.referenceId,
            clientId = input.clientId,
            responseCode = input.responseCode,
            responseMessage = input.responseMessage?.id
        )

    fun GetTokenB2BRes.toDomain(): GetTokenB2BDomain =
        GetTokenB2BDomain(
            responseCode = responseCode,
            responseMessage = responseMessage,
            accessToken = accessToken,
            tokenType = tokenType,
            expiresIn = expiresIn,
            additionalInfo = additionalInfo
        )

    fun GetTokenB2B2CRes.toDomain(): GetTokenB2B2CDomain =
        GetTokenB2B2CDomain(
            responseCode = responseCode,
            responseMessage = responseMessage,
            accessToken = accessToken,
            tokenType = tokenType,
            accessTokenExpiryTime = accessTokenExpiryTime,
            refreshToken = refreshToken,
            refreshTokenExpiryTime = accessTokenExpiryTime,
            additionalInfo = additionalInfo
        )

    fun GenerateQrRes.toDomain(): GenerateQrDomain =
        GenerateQrDomain(
            responseCode = responseCode,
            responseMessage = responseMessage,
            referenceNo = partnerReferenceNo,
            partnerReferenceNo = referenceNo,
            qrContent = qrContent,
            terminalId = terminalId,
        )

    fun QueryQrRes.toDomain(): QueryQrDomain =
        QueryQrDomain(
            responseCode = responseCode,
            responseMessage = responseMessage,
            originalReferenceNo = originalReferenceNo,
            originalPartnerReferenceNo = originalPartnerReferenceNo,
            serviceCode = serviceCode,
            latestTransactionStatus = latestTransactionStatus,
            transactionStatusDesc = transactionStatusDesc,
            paidTime = paidTime,
            amount = amount?.toDomain(),
            feeAmount = feeAmount?.toDomain(),
            additionalInfo = additionalInfo?.toDomain(),
        )

    fun AccountCreationRes.toDomain(): AccountCreationDomain =
        AccountCreationDomain(
            responseCode = responseCode,
            responseMessage = responseMessage,
            referenceNo = referenceNo,
            partnerReferenceNo = partnerReferenceNo
        )

    fun VerifyOtpRes.toDomain(): VerifyOtpDomain =
        VerifyOtpDomain(
            responseCode = responseCode,
            responseMessage = responseMessage,
            originalPartnerReferenceNo = originalPartnerReferenceNo,
            originalReferenceNo = originalReferenceNo,
            email = email,
            phoneNo = phoneNo,
            sendOtpFlag = sendOtpFlag,
            additionalInfo = additionalInfo?.toDomain(),
            qparamsURL = qparamsURL,
            qparams = qparams?.toDomain()
        )

    fun AccountBindingRes.toDomain(): AccountBindingDomain =
        AccountBindingDomain(
            responseCode = responseCode,
            responseMessage = responseMessage,
            referenceNo = referenceNo,
            partnerReferenceNo = partnerReferenceNo,
            linkId = linkId,
            nextAction = nextAction,
            redirectUrl = redirectUrl,
            userInfo = userInfo?.toDomain()
        )

    fun QueryAccountBindingRes.toDomain(): QueryAccountBindingDomain =
        QueryAccountBindingDomain(
            responseCode = responseCode,
            responseMessage = responseMessage,
            partnerReferenceNo = partnerReferenceNo,
            additionalInfo = additionalInfo?.toDomain()
        )

    fun BankInquiryRes.toDomain(): BankInquiryDomain =
        BankInquiryDomain(
            responseCode,
            responseMessage,
            referenceNo,
            partnerReferenceNo,
            beneficiaryAccountNumber,
            beneficiaryAccountName,
            beneficiaryBankCode,
            amount?.toDomain()
        )

    fun BankTransferRes.toDomain(): BankTransferDomain =
        BankTransferDomain(
            responseCode,
            responseMessage,
            partnerReferenceNo,
            referenceNo,
            transactionDate,
            referenceNumber
        )

    fun DecodeQrRes.toDomain(): DecodeQrDomain =
        DecodeQrDomain(
            responseCode = responseCode,
            responseMessage = responseMessage,
            referenceNo = referenceNo,
            partnerReferenceNo = partnerReferenceNo,
            merchantName = merchantName,
            merchantCategory = merchantCategory,
            merchantLocation = merchantLocation,
            transactionAmount?.toDomain(),
            feeAmount?.toDomain(),
            additionalInfo?.toDomain(),
        )

    fun PaymentQrRes.toDomain(): PaymentQrDomain =
        PaymentQrDomain(
            responseCode = responseCode,
            responseMessage = responseMessage,
            referenceNo = referenceNo,
            partnerReferenceNo = partnerReferenceNo,
            transactionDate = transactionDate,
            amount = amount?.toDomain(),
            feeAmount = feeAmount?.toDomain(),
            additionalInfo = additionalInfo?.toDomain()
        )

    private fun UserInfoRes.toDomain(): UserInfoDomain =
        UserInfoDomain(
            publicUserId
        )

    private fun AmountRes.toDomain(): AmountDomain =
        AmountDomain(
            value,
            currency
        )

    private fun QParamsRes.toDomain(): QParamsDomain =
        QParamsDomain(token)

    private fun AdditionalInfoRes.toDomain(): AdditionalInfoDomain =
        AdditionalInfoDomain(
            approvalCode = approvalCode,
            deviceId = deviceId,
            linkId = linkId,
            authCode = authCode,
            remarks = remarks,
            merchantId = merchantId,
            pointOfInitiationMethod = pointOfInitiationMethod,
            pointOfInitiationMethodDescription = pointOfInitiationMethodDescription,
            feeType = feeType,
            feeTypeDescription = feeTypeDescription
        )

    fun <Input, Output> resultResponseToDomain(
        input: ResultRes<Input>,
        output: Output?
    ): ResultDomain<Output> =
        ResultDomain(
            result = input.result,
            message = input.message,
            data = output,
            dateTime = input.dateTime,
            errorDesc = input.errorDesc,
            errorCode = input.errorCode
        )

    fun <Input> resultResponseToDomain(input: ResultRes<Input>): ResultDomain<Input> =
        ResultDomain(
            result = input.result,
            message = input.message,
            data = input.data,
            dateTime = input.dateTime,
            errorDesc = input.errorDesc,
            errorCode = input.errorCode
        )
}