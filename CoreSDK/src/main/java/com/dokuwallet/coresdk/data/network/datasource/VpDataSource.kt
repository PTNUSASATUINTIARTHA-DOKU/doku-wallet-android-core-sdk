package com.dokuwallet.coresdk.data.network.datasource

import com.dokuwallet.coresdk.data.network.api.ApiRes
import com.dokuwallet.coresdk.data.network.api.VPApiService
import com.dokuwallet.coresdk.data.network.parameter.vpparameter.CaptureIdReq
import com.dokuwallet.coresdk.data.network.parameter.vpparameter.RequestVerificationReq
import com.dokuwallet.coresdk.data.network.parameter.vpparameter.UpdateOcrReq
import com.dokuwallet.coresdk.data.network.response.vpresponse.CaptureIdDataRes
import com.dokuwallet.coresdk.data.network.response.vpresponse.IdentityDataRes
import com.dokuwallet.coresdk.data.network.response.vpresponse.ResultRes
import com.dokuwallet.coresdk.data.network.response.vpresponse.ThemePropertyResponse
import com.dokuwallet.coresdk.data.network.response.vpresponse.VerificationDataRes
import com.dokuwallet.coresdk.vo.ErrorData
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.HttpException
import java.io.File

internal class VpDataSource(private val apiService: VPApiService) {
    suspend fun requestVerification(
        clientId: String,
        requestId: String,
        requestTimestamp: String,
        signature: String,
        requestVerificationReq: RequestVerificationReq
    ): Flow<ApiRes<ResultRes<VerificationDataRes>>> =
        networkHandler {
            apiService.requestVerification(
                clientId,
                requestId,
                requestTimestamp,
                signature,
                requestVerificationReq
            )
        }

    suspend fun validateToken(token: String): Flow<ApiRes<ResultRes<VerificationDataRes>>> =
        networkHandler { apiService.validateToken(token) }

    suspend fun getClientTheme(token: String): Flow<ApiRes<ResultRes<ThemePropertyResponse>>> =
        networkHandler { apiService.getClientTheme(token) }

    suspend fun captureId(
        token: String,
        images: CaptureIdReq
    ): Flow<ApiRes<ResultRes<CaptureIdDataRes>>> =
        networkHandler { apiService.captureId(token, images) }

    suspend fun captureSelfie(
        token: String,
        images: CaptureIdReq
    ): Flow<ApiRes<ResultRes<CaptureIdDataRes>>> =
        networkHandler { apiService.captureSelfie(token, images) }

    suspend fun setStage(
        token: String,
        stage: Int
    ): Flow<ApiRes<ResultRes<VerificationDataRes>>> =
        networkHandler(true) { apiService.setStage(token, stage) }

    suspend fun requestOcr(token: String): Flow<ApiRes<ResultRes<VerificationDataRes>>> =
        networkHandler(true) { apiService.requestOcr(token) }

    suspend fun eyeBlink(
        token: String,
        file: File,
        index: Int,
        isLast: Boolean
    ): Flow<ApiRes<ResultRes<String>>> =
        networkHandler(true) {
            apiService.eyeBlink(
                token,
                MultipartBody.Part.createFormData(
                    "images",
                    file.name,
                    file.asRequestBody("image/jpeg".toMediaType())
                ),
                index.toString().toRequestBody("text/plain".toMediaTypeOrNull()),
                isLast.toString().toRequestBody("text/plain".toMediaTypeOrNull())
            )
        }

    suspend fun getVerificationData(token: String): Flow<ApiRes<ResultRes<IdentityDataRes>>> =
        networkHandler { apiService.getVerificationData(token) }

    suspend fun capturedIdUrl(token: String): Flow<ApiRes<ResultRes<CaptureIdDataRes>>> =
        networkHandler { apiService.capturedIdUrl(token) }

    suspend fun capturedSelfieUrl(token: String): Flow<ApiRes<ResultRes<CaptureIdDataRes>>> =
        networkHandler { apiService.capturedSelfieUrl(token) }

    suspend fun faceMatch(token: String): Flow<ApiRes<ResultRes<String>>> =
        networkHandler(true) { apiService.faceMatch(token) }

    suspend fun updateOcr(
        token: String,
        param: UpdateOcrReq
    ): Flow<ApiRes<ResultRes<String>>> =
        networkHandler(true) { apiService.updateOcr(token, param) }

    suspend fun authentication(
        token: String,
        file: File,
        index: Int,
        isLast: Boolean
    ): Flow<ApiRes<ResultRes<String>>> =
        networkHandler(true) {
            apiService.authentication(
                token,
                MultipartBody.Part.createFormData(
                    "images",
                    file.name,
                    file.asRequestBody("image/jpeg".toMediaType())
                ),
                index.toString().toRequestBody("text/plain".toMediaTypeOrNull()),
                isLast.toString().toRequestBody("text/plain".toMediaTypeOrNull())
            )
        }

    suspend fun register(
        token: String,
        images: CaptureIdReq
    ): Flow<ApiRes<ResultRes<String>>> =
        networkHandler(true) { apiService.register(token, images) }

    suspend fun update(
        token: String,
        images: CaptureIdReq
    ): Flow<ApiRes<ResultRes<String>>> =
        networkHandler(true) { apiService.update(token, images) }

    private suspend fun <T> networkHandler(
        isDataNull: Boolean = false,
        responseCall: suspend () -> ResultRes<T>
    ): Flow<ApiRes<ResultRes<T>>> =
        flow {
            try {
                val response = responseCall()

                if (isDataNull) {
                    emit(ApiRes.Success(response))
                } else {
                    if (response.data != null) {
                        emit(ApiRes.Success(response))
                    } else {
                        emit(ApiRes.Error(ErrorData(response.errorCode, response.message)))
                    }
                }
            } catch (e: Exception) {
                when (e) {
                    is HttpException -> {
                        val responseBody = e.response()?.errorBody()?.string()
                        val errorResponse =
                            Gson().fromJson(responseBody, ResultRes::class.java)
                        emit(
                            ApiRes.Error(
                                ErrorData(
                                    errorResponse.responseCode,
                                    errorResponse.message
                                )
                            )
                        )
                    }
                    else -> {
                        emit(ApiRes.Error(ErrorData(null, e.message)))
                    }
                }
            }
        }.flowOn(Dispatchers.IO)
}