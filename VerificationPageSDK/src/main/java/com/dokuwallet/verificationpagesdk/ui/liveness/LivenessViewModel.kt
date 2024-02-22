package com.dokuwallet.verificationpagesdk.ui.liveness

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.dokuwallet.coresdk.data.network.parameter.vpparameter.CaptureIdReq
import com.dokuwallet.coresdk.domain.model.vpmodel.ResultDomain
import com.dokuwallet.coresdk.domain.model.vpmodel.VerificationDataDomain
import com.dokuwallet.coresdk.domain.usecase.VPDataUseCase
import com.dokuwallet.coresdk.vo.Resource
import com.dokuwallet.verificationpagesdk.enums.VerificationType
import java.io.File

internal class LivenessViewModel(private val dataUseCase: VPDataUseCase) : ViewModel() {
    fun kycOrAuth(
        type: VerificationType,
        token: String,
        file: File,
        index: Int,
        isLast: Boolean
    ): LiveData<Resource<ResultDomain<String>>> =
        if (type == VerificationType.KYC) {
            dataUseCase.eyeBlink(token, file, index, isLast).asLiveData()
        } else {
            dataUseCase.authentication(token, file, index, isLast).asLiveData()
        }

    fun regOrUpdate(
        type: VerificationType,
        token: String,
        param: CaptureIdReq
    ): LiveData<Resource<ResultDomain<String>>> =
        if (type == VerificationType.TWO_FA_REG) {
            dataUseCase.register(token, param).asLiveData()
        } else {
            dataUseCase.update(token, param).asLiveData()
        }

    fun setStage(
        token: String,
        stage: Int
    ): LiveData<Resource<ResultDomain<VerificationDataDomain>>> =
        dataUseCase.setStage(token, stage).asLiveData()

    fun faceMatch(token: String): LiveData<Resource<ResultDomain<String>>> =
        dataUseCase.faceMatch(token).asLiveData()
}