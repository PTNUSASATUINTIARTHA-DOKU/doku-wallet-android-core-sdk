package com.dokuwallet.verificationpagesdk.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.dokuwallet.coresdk.data.network.parameter.vpparameter.RequestVerificationReq
import com.dokuwallet.coresdk.domain.model.vpmodel.ResultDomain
import com.dokuwallet.coresdk.domain.model.vpmodel.ThemePropertyDomain
import com.dokuwallet.coresdk.domain.model.vpmodel.VerificationDataDomain
import com.dokuwallet.coresdk.domain.usecase.VPDataUseCase
import com.dokuwallet.coresdk.utils.CommonUtils
import com.dokuwallet.coresdk.vo.Resource
import com.dokuwallet.verificationpagesdk.enums.VerificationType
import com.dokuwallet.verificationpagesdk.utils.VerificationPage
import org.json.JSONObject

internal class HomeViewModel(private val dataUseCase: VPDataUseCase) : ViewModel() {
    fun getClientTheme(token: String): LiveData<Resource<ResultDomain<ThemePropertyDomain>>> =
        dataUseCase.getClientTheme(token).asLiveData()

    fun validateToken(token: String): LiveData<Resource<ResultDomain<VerificationDataDomain>>> =
        dataUseCase.validateToken(token).asLiveData()

    fun requestVerification(): LiveData<Resource<ResultDomain<VerificationDataDomain>>> {

        val verificationPageConfig = VerificationPage.verificationPageConfig
        val verificationType: String = when (VerificationPage.SharedInstance.verificationType) {
            VerificationType.KYC -> "KYC"
            VerificationType.PAY_V -> "PAY_V"
            VerificationType.TWO_FA_REG -> "TWO_FA_REGISTER"
            VerificationType.TWO_FA_AUTH -> "TWO_FA_AUTHENTICATION"
            VerificationType.TWO_FA_UPDATE -> "TWO_FA_UPDATE"
            VerificationType.CRAWLING -> "WEB_V"
            null -> ""
        }

        val verificationParameter = RequestVerificationReq(
            projectId = verificationPageConfig.projectId,
            customerId = verificationPageConfig.customerId,
            email = verificationPageConfig.email,
            phone = verificationPageConfig.phone,
            verificationType = verificationType,
            integrationMethod = "SDK_ANDROID"
        )

        val requestId = CommonUtils.getCurrentTimestamp()
        val requestTimestamp = CommonUtils.getCurrentTimestamp()
        val secret = verificationPageConfig.secret
        val requestTarget = "/vp-web-api/request-verification"

        val jGroup = JSONObject()
        var jsonStringData = ""
        try {
            jGroup.put("customerId", verificationParameter.customerId)
            jGroup.put("email", verificationParameter.email)
            jGroup.put("integrationMethod", verificationParameter.integrationMethod)
            jGroup.put("phone", verificationParameter.phone)
            jGroup.put("projectId", verificationParameter.projectId)
            jGroup.put("verificationType", verificationParameter.verificationType)
            jsonStringData = jGroup.toString()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        val digest = CommonUtils.generateSHA256(jsonStringData.toByteArray())
        val digestBase64 = CommonUtils.toBase64(digest)
        val signatureComponent = "Client-Id:${verificationPageConfig.clientId}\n" +
                "Request-Id:${requestId}\n" +
                "Request-Timestamp:${requestTimestamp}\n" +
                "Request-Target:${requestTarget}\n" +
                "Digest:${digestBase64}"

        val signature = CommonUtils.generatedHmacSha256(signatureComponent, secret)
        val signatureBase64 = CommonUtils.toBase64(signature)
        val signatureString = "HMACSHA256=${signatureBase64}"

        // Reassign signature to parameter
        return dataUseCase.requestVerification(
            verificationPageConfig.clientId,
            requestId,
            requestTimestamp,
            signatureString,
            verificationParameter
        ).asLiveData()
    }
}