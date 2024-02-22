package com.dokuwallet.coresdk.domain.usecase

import com.dokuwallet.coresdk.data.network.parameter.vpparameter.CaptureIdReq
import com.dokuwallet.coresdk.data.network.parameter.vpparameter.RequestVerificationReq
import com.dokuwallet.coresdk.data.network.parameter.vpparameter.UpdateOcrReq
import com.dokuwallet.coresdk.domain.model.vpmodel.CaptureIdDataDomain
import com.dokuwallet.coresdk.domain.model.vpmodel.IdentityDataDomain
import com.dokuwallet.coresdk.domain.model.vpmodel.ResultDomain
import com.dokuwallet.coresdk.domain.model.vpmodel.ThemePropertyDomain
import com.dokuwallet.coresdk.domain.model.vpmodel.VerificationDataDomain
import com.dokuwallet.coresdk.vo.Resource
import kotlinx.coroutines.flow.Flow
import java.io.File

interface VPDataUseCase {
    fun requestVerification(
        clientId: String,
        requestId: String,
        requestTimestamp: String,
        signature: String,
        requestVerificationReq: RequestVerificationReq
    ): Flow<Resource<ResultDomain<VerificationDataDomain>>>

    fun validateToken(token: String): Flow<Resource<ResultDomain<VerificationDataDomain>>>

    fun getClientTheme(token: String): Flow<Resource<ResultDomain<ThemePropertyDomain>>>

    fun captureId(
        token: String,
        param: CaptureIdReq
    ): Flow<Resource<ResultDomain<CaptureIdDataDomain>>>

    fun setStage(token: String, stage: Int): Flow<Resource<ResultDomain<VerificationDataDomain>>>

    fun requestOcr(token: String): Flow<Resource<ResultDomain<VerificationDataDomain>>>

    fun captureSelfie(
        token: String,
        param: CaptureIdReq
    ): Flow<Resource<ResultDomain<CaptureIdDataDomain>>>

    fun eyeBlink(
        token: String,
        file: File,
        index: Int,
        isLast: Boolean
    ): Flow<Resource<ResultDomain<String>>>

    fun getVerificationData(token: String): Flow<Resource<ResultDomain<IdentityDataDomain>>>

    fun capturedIdUrl(token: String): Flow<Resource<ResultDomain<CaptureIdDataDomain>>>

    fun capturedSelfieUrl(token: String): Flow<Resource<ResultDomain<CaptureIdDataDomain>>>

    fun updateOcr(token: String, param: UpdateOcrReq): Flow<Resource<ResultDomain<String>>>

    fun authentication(
        token: String,
        file: File,
        index: Int,
        isLast: Boolean
    ): Flow<Resource<ResultDomain<String>>>

    fun register(
        token: String,
        param: CaptureIdReq
    ): Flow<Resource<ResultDomain<String>>>

    fun update(
        token: String,
        param: CaptureIdReq
    ): Flow<Resource<ResultDomain<String>>>

    fun faceMatch(token: String): Flow<Resource<ResultDomain<String>>>
}