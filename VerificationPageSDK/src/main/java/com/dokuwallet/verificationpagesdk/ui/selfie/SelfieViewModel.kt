package com.dokuwallet.verificationpagesdk.ui.selfie

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.dokuwallet.coresdk.data.network.parameter.vpparameter.CaptureIdReq
import com.dokuwallet.coresdk.domain.model.vpmodel.CaptureIdDataDomain
import com.dokuwallet.coresdk.domain.model.vpmodel.ResultDomain
import com.dokuwallet.coresdk.domain.usecase.VPDataUseCase
import com.dokuwallet.coresdk.vo.Resource

internal class SelfieViewModel(private val dataUseCase: VPDataUseCase) : ViewModel() {
    fun captureSelfie(
        token: String,
        images: CaptureIdReq
    ): LiveData<Resource<ResultDomain<CaptureIdDataDomain>>> =
        dataUseCase.captureSelfie(token, images).asLiveData()
}