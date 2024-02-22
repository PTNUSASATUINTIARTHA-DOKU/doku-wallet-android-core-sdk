package com.dokuwallet.verificationpagesdk.ui.ktp

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.dokuwallet.coresdk.data.network.parameter.vpparameter.CaptureIdReq
import com.dokuwallet.coresdk.domain.model.vpmodel.CaptureIdDataDomain
import com.dokuwallet.coresdk.domain.model.vpmodel.ResultDomain
import com.dokuwallet.coresdk.domain.model.vpmodel.VerificationDataDomain
import com.dokuwallet.coresdk.domain.usecase.VPDataUseCase
import com.dokuwallet.coresdk.vo.Resource

internal class KtpViewModel(private val dataUseCase: VPDataUseCase) : ViewModel() {
    fun captureId(
        token: String,
        images: CaptureIdReq
    ): LiveData<Resource<ResultDomain<CaptureIdDataDomain>>> =
        dataUseCase.captureId(token, images).asLiveData()

    fun setStage(
        token: String,
        stage: Int
    ): LiveData<Resource<ResultDomain<VerificationDataDomain>>> =
        dataUseCase.setStage(token, stage).asLiveData()

    fun requestOcr(token: String): LiveData<Resource<ResultDomain<VerificationDataDomain>>> =
        dataUseCase.requestOcr(token).asLiveData()
}