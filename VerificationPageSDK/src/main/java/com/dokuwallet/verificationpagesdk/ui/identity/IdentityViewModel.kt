package com.dokuwallet.verificationpagesdk.ui.identity

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.dokuwallet.coresdk.data.network.parameter.vpparameter.UpdateOcrReq
import com.dokuwallet.coresdk.domain.model.vpmodel.CaptureIdDataDomain
import com.dokuwallet.coresdk.domain.model.vpmodel.IdentityDataDomain
import com.dokuwallet.coresdk.domain.model.vpmodel.ResultDomain
import com.dokuwallet.coresdk.domain.model.vpmodel.VerificationDataDomain
import com.dokuwallet.coresdk.domain.usecase.VPDataUseCase
import com.dokuwallet.coresdk.vo.Resource

internal class IdentityViewModel(private val dataUseCase: VPDataUseCase) : ViewModel() {
    fun getVerificationData(token: String): LiveData<Resource<ResultDomain<IdentityDataDomain>>> =
        dataUseCase.getVerificationData(token).asLiveData()

    fun getCapturedId(token: String): LiveData<Resource<ResultDomain<CaptureIdDataDomain>>> =
        dataUseCase.capturedIdUrl(token).asLiveData()

    fun getCapturedSelfie(token: String): LiveData<Resource<ResultDomain<CaptureIdDataDomain>>> =
        dataUseCase.capturedSelfieUrl(token).asLiveData()

    fun updateOcr(
        token: String,
        param: UpdateOcrReq
    ): LiveData<Resource<ResultDomain<String>>> =
        dataUseCase.updateOcr(token, param).asLiveData()

    fun setStage(
        token: String,
        stage: Int
    ): LiveData<Resource<ResultDomain<VerificationDataDomain>>> =
        dataUseCase.setStage(token, stage).asLiveData()
}