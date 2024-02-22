package com.dokuwallet.coresdk.data.network.api

import com.dokuwallet.coresdk.data.network.parameter.vpparameter.CaptureIdReq
import com.dokuwallet.coresdk.data.network.parameter.vpparameter.RequestVerificationReq
import com.dokuwallet.coresdk.data.network.parameter.vpparameter.UpdateOcrReq
import com.dokuwallet.coresdk.data.network.response.vpresponse.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*

internal interface VPApiService {
    @POST("request-verification")
    suspend fun requestVerification(
        @Header("Client-Id") clientId: String,
        @Header("Request-Id") requestId: String,
        @Header("Request-Timestamp") timestamp: String,
        @Header("Signature") signature: String,
        @Body param: RequestVerificationReq
    ): ResultRes<VerificationDataRes>

    @POST("validate-token")
    suspend fun validateToken(
        @Header("Authorization") token: String
    ): ResultRes<VerificationDataRes>

    @GET("client/theme")
    suspend fun getClientTheme(
        @Header("Authorization") token: String
    ): ResultRes<ThemePropertyResponse>

    @POST("kyc/capture-id")
    suspend fun captureId(
        @Header("Authorization") token: String,
        @Body param: CaptureIdReq
    ): ResultRes<CaptureIdDataRes>

    @POST("next-stage/{stage}")
    suspend fun setStage(
        @Header("Authorization") token: String,
        @Path("stage") stage: Int
    ): ResultRes<VerificationDataRes>

    @POST("kyc/ocr")
    suspend fun requestOcr(
        @Header("Authorization") token: String,
    ): ResultRes<VerificationDataRes>

    @POST("kyc/capture-selfie")
    suspend fun captureSelfie(
        @Header("Authorization") token: String,
        @Body param: CaptureIdReq
    ): ResultRes<CaptureIdDataRes>

    @Multipart
    @POST("liveness/eye-blink")
    suspend fun eyeBlink(
        @Header("Authorization") token: String,
        @Part images: MultipartBody.Part,
        @Part("index") fileName: RequestBody,
        @Part("last") folder: RequestBody,
    ): ResultRes<String>

    @GET("kyc/verification-data")
    suspend fun getVerificationData(
        @Header("Authorization") token: String
    ): ResultRes<IdentityDataRes>

    @GET("kyc/capture-id/url")
    suspend fun capturedIdUrl(
        @Header("Authorization") token: String,
    ): ResultRes<CaptureIdDataRes>

    @GET("kyc/capture-selfie/url")
    suspend fun capturedSelfieUrl(
        @Header("Authorization") token: String,
    ): ResultRes<CaptureIdDataRes>

    @POST("kyc/face-match")
    suspend fun faceMatch(
        @Header("Authorization") token: String,
    ): ResultRes<String>

    @POST("kyc/ocr/update")
    suspend fun updateOcr(
        @Header("Authorization") token: String,
        @Body param: UpdateOcrReq
    ): ResultRes<String>

    @Multipart
    @POST("2fa/authentication")
    suspend fun authentication(
        @Header("Authorization") token: String,
        @Part images: MultipartBody.Part,
        @Part("index") fileName: RequestBody,
        @Part("last") folder: RequestBody,
    ): ResultRes<String>

    @POST("2fa/register")
    suspend fun register(
        @Header("Authorization") token: String,
        @Body param: CaptureIdReq
    ): ResultRes<String>

    @POST("2fa/update")
    suspend fun update(
        @Header("Authorization") token: String,
        @Body param: CaptureIdReq
    ): ResultRes<String>
}