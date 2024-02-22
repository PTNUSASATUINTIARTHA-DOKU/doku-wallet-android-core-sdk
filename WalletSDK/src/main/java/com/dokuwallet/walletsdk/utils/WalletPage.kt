package com.dokuwallet.walletsdk.utils

import android.content.Context
import android.graphics.Bitmap
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.activity.ComponentActivity
import com.dokuwallet.coresdk.data.network.parameter.walletparameter.AccountBindingReq
import com.dokuwallet.coresdk.data.network.parameter.walletparameter.AccountCreationReq
import com.dokuwallet.coresdk.data.network.parameter.walletparameter.AuthHeader
import com.dokuwallet.coresdk.data.network.parameter.walletparameter.B2B2CHeader
import com.dokuwallet.coresdk.data.network.parameter.walletparameter.B2BHeader
import com.dokuwallet.coresdk.data.network.parameter.walletparameter.BankInquiryReq
import com.dokuwallet.coresdk.data.network.parameter.walletparameter.BankTransferReq
import com.dokuwallet.coresdk.data.network.parameter.walletparameter.DecodeQrReq
import com.dokuwallet.coresdk.data.network.parameter.walletparameter.GenerateQrReq
import com.dokuwallet.coresdk.data.network.parameter.walletparameter.GetTokenB2B2CReq
import com.dokuwallet.coresdk.data.network.parameter.walletparameter.GetTokenB2BReq
import com.dokuwallet.coresdk.data.network.parameter.walletparameter.PaymentQrReq
import com.dokuwallet.coresdk.data.network.parameter.walletparameter.QueryAccountBindingReq
import com.dokuwallet.coresdk.data.network.parameter.walletparameter.QueryQrReq
import com.dokuwallet.coresdk.data.network.parameter.walletparameter.VerifyOtpReq
import com.dokuwallet.coresdk.domain.model.walletmodel.AccountBindingDomain
import com.dokuwallet.coresdk.domain.model.walletmodel.AccountCreationDomain
import com.dokuwallet.coresdk.domain.model.walletmodel.BankInquiryDomain
import com.dokuwallet.coresdk.domain.model.walletmodel.BankTransferDomain
import com.dokuwallet.coresdk.domain.model.walletmodel.DecodeQrDomain
import com.dokuwallet.coresdk.domain.model.walletmodel.GenerateQrDomain
import com.dokuwallet.coresdk.domain.model.walletmodel.GetTokenB2B2CDomain
import com.dokuwallet.coresdk.domain.model.walletmodel.GetTokenB2BDomain
import com.dokuwallet.coresdk.domain.model.walletmodel.PaymentQrDomain
import com.dokuwallet.coresdk.domain.model.walletmodel.QueryAccountBindingDomain
import com.dokuwallet.coresdk.domain.model.walletmodel.QueryQrDomain
import com.dokuwallet.coresdk.domain.model.walletmodel.VerifyOtpDomain
import com.dokuwallet.coresdk.vo.ErrorData
import com.dokuwallet.walletsdk.viewmodel.HomeViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

object WalletPage {
    private fun configVariable(
        currentViewController: ComponentActivity,
        onFinish: (HomeViewModel, ResultHandler) -> Unit,
    ) {
        var rootFragment: ComponentActivity = currentViewController
        val viewModel: HomeViewModel by rootFragment.viewModel()
        val resultHandler = ResultHandler(rootFragment)
        onFinish(viewModel, resultHandler)
    }

    private fun isNetworkAvailable(context: Context): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val network = cm.activeNetwork
            val networkCapabilities = cm.getNetworkCapabilities(network)

            return networkCapabilities != null &&
                    (networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                            networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ||
                            networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET))
        } else {
            val activeNetwork = cm.activeNetworkInfo

            return activeNetwork != null && activeNetwork.isConnectedOrConnecting
        }
    }

    fun startAccountBinding(
        currentViewController: ComponentActivity,
        b2BHeader: B2BHeader, accountBindingReq: AccountBindingReq,
        onFailure: (ErrorData) -> Unit,
        onSuccess: (AccountBindingDomain) -> Unit) {

        if(isNetworkAvailable(currentViewController)) {
            configVariable(currentViewController
            ) {
                    homeViewModel, resultHandler ->
                homeViewModel!!.accountBinding(b2BHeader, accountBindingReq)
                    .observe(currentViewController) { result ->
                        resultHandler!!.handle(result, handleError = {
                            if (it != null) {
                                onFailure(it)
                            }
                        }) { data ->
                            onSuccess(data!!)
                        }
                    }
            }
        }
    }

    fun startAccountCreation(
        currentViewController: ComponentActivity,
        b2BHeader: B2BHeader,
        accountCreationReq: AccountCreationReq,
        onFailure: (ErrorData) -> Unit,
        onSuccess: (AccountCreationDomain) -> Unit
    ) {
        if(isNetworkAvailable(currentViewController)) {
            configVariable(currentViewController)
            { homeViewModel, resultHandler ->
                homeViewModel!!.accountCreation(b2BHeader, accountCreationReq)
                    .observe(currentViewController) { result ->
                        resultHandler!!.handle(
                            result, handleError = {
                                if (it != null) {
                                    onFailure(it)
                                }
                            }) { data ->
                            onSuccess(data!!)
                        }
                    }
            }
        }

    }

    fun startBankInquiry(
        currentViewController: ComponentActivity,
        b2BHeader: B2BHeader,
        bankInquiryReq: BankInquiryReq,
        onFailure: (ErrorData) -> Unit,
        onSuccess: (BankInquiryDomain) -> Unit
    ) {
        if(isNetworkAvailable(currentViewController)) {
            configVariable(currentViewController)
            { homeViewModel, resultHandler ->
                homeViewModel!!.bankInquiry(b2BHeader, bankInquiryReq)
                    .observe(currentViewController) { result ->
                        resultHandler!!.handle(
                            result, handleError = {
                                if (it != null) {
                                    onFailure(it)
                                }
                            }) { data ->
                            onSuccess(data)
                        }
                    }
            }
        }
    }

    fun startBankTransfer(
        currentViewController: ComponentActivity,
        b2B2CHeader: B2B2CHeader,
        bankTransferReq: BankTransferReq,
        onFailure: (ErrorData) -> Unit,
        onSuccess: (BankTransferDomain) -> Unit
    ) {
        if(isNetworkAvailable(currentViewController)) {
            configVariable(currentViewController)
            { homeViewModel, resultHandler ->
                homeViewModel!!.bankTransfer(b2B2CHeader, bankTransferReq)
                    .observe(currentViewController) { result ->
                        resultHandler!!.handle(
                            result, handleError = {
                                if (it != null) {
                                    onFailure(it)
                                }
                            }) { data ->
                            onSuccess(data!!)
                        }
                    }
            }
        }
    }

    fun startDecodeQris(
        currentViewController: ComponentActivity,
        b2BHeader: B2BHeader,
        decodeQrReq: DecodeQrReq,
        onFailure: (ErrorData) -> Unit,
        onSuccess: (DecodeQrDomain) -> Unit
    ) {
        if(isNetworkAvailable(currentViewController)) {
            configVariable(currentViewController)
            { homeViewModel, resultHandler ->
                homeViewModel!!.decodeQr(b2BHeader, decodeQrReq)
                    .observe(currentViewController) { result ->
                        resultHandler!!.handle(
                            result, handleError = {
                                if (it != null) {
                                    onFailure(it)
                                }
                            }) { data ->
                            onSuccess(data!!)
                        }
                    }
            }
        }
    }

    fun startGenerateQris(
        currentViewController: ComponentActivity,
        b2BHeader: B2BHeader,
        generateQrReq: GenerateQrReq,
        onFailure: (ErrorData) -> Unit,
        onSuccess: (GenerateQrDomain, Bitmap) -> Unit
    ) {
        if(isNetworkAvailable(currentViewController)) {
            configVariable(currentViewController)
            { homeViewModel, resultHandler ->
                homeViewModel!!.generateQr(b2BHeader, generateQrReq)
                    .observe(currentViewController) { result ->
                        resultHandler!!.handle(
                            result, handleError = {
                                if (it != null) {
                                    onFailure(it)
                                }
                            }) { data ->
                            var bitmap = data.qrContent?.let { WalletUtils.generateQrImage(it) }
                            if (bitmap != null) {
                                onSuccess(data!!, bitmap)
                            }
                        }
                    }
            }
        }
    }

    fun startGetB2B2CToken(
        currentViewController: ComponentActivity,
        authHeader : AuthHeader,
        getTokenB2B2CReq: GetTokenB2B2CReq,
        onFailure: (ErrorData) -> Unit,
        onSuccess: (GetTokenB2B2CDomain) -> Unit
    ) {
        if(isNetworkAvailable(currentViewController)) {
            configVariable(currentViewController)
            { homeViewModel, resultHandler ->
                homeViewModel!!.getTokenB2B2C(authHeader, getTokenB2B2CReq)
                    .observe(currentViewController) { result ->
                        resultHandler!!.handle(
                            result, handleError = {
                                if (it != null) {
                                    onFailure(it)
                                }
                            }) { data ->
                            onSuccess(data!!)
                        }
                    }
            }
        }
    }

    fun startGetB2BToken(
        currentViewController: ComponentActivity,
        authHeader: AuthHeader,
        getTokenB2BReq: GetTokenB2BReq,
        onFailure: (ErrorData) -> Unit,
        onSuccess: (GetTokenB2BDomain) -> Unit
    ) {
        if(isNetworkAvailable(currentViewController)) {
            configVariable(currentViewController)
            { homeViewModel, resultHandler ->
                homeViewModel!!.getTokenB2B(authHeader, getTokenB2BReq)
                    .observe(currentViewController) { result ->
                        resultHandler!!.handle(
                            result, handleError = {
                                if (it != null) {
                                    onFailure(it)
                                }
                            }) { data ->
                            onSuccess(data!!)
                        }
                    }
            }
        }
    }

    fun startPaymentQris(
        currentViewController: ComponentActivity,
        b2B2CHeader: B2B2CHeader,
        paymentQrReq: PaymentQrReq,
        onFailure: (ErrorData) -> Unit,
        onSuccess: (PaymentQrDomain) -> Unit
    ) {
        if(isNetworkAvailable(currentViewController)) {
            configVariable(currentViewController)
            { homeViewModel, resultHandler ->
                homeViewModel!!.paymentQr(b2B2CHeader, paymentQrReq)
                    .observe(currentViewController) { result ->
                        resultHandler!!.handle(
                            result, handleError = {
                                if (it != null) {
                                    onFailure(it)
                                }
                            }) { data ->
                            onSuccess(data!!)
                        }
                    }
            }
        }
    }

    fun startQueryAccountBinding(
        currentViewController: ComponentActivity,
        b2BHeader: B2BHeader,
        queryAccountBindingReq: QueryAccountBindingReq,
        onFailure: (ErrorData) -> Unit,
        onSuccess: (QueryAccountBindingDomain) -> Unit
    ) {
        if(isNetworkAvailable(currentViewController)) {
            configVariable(currentViewController)
            { homeViewModel, resultHandler ->
                homeViewModel!!.queryAccountBinding(b2BHeader, queryAccountBindingReq)
                    .observe(currentViewController) { result ->
                        resultHandler!!.handle(
                            result, handleError = {
                                if (it != null) {
                                    onFailure(it)
                                }
                            }) { data ->
                            onSuccess(data!!)
                        }
                    }
            }
        }
    }

    fun startQueryQris(
        currentViewController: ComponentActivity,
        b2BHeader: B2BHeader,
        queryQrReq: QueryQrReq,
        onFailure: (ErrorData) -> Unit,
        onSuccess: (QueryQrDomain) -> Unit
    ) {
        if(isNetworkAvailable(currentViewController)) {
            configVariable(currentViewController)
            { homeViewModel, resultHandler ->
                homeViewModel!!.queryQr(b2BHeader, queryQrReq)
                    .observe(currentViewController) { result ->
                        resultHandler!!.handle(
                            result, handleError = {
                                if (it != null) {
                                    onFailure(it)
                                }
                            }) { data ->
                            onSuccess(data!!)
                        }
                    }
            }
        }
    }

    fun startVerifyOtp(
        currentViewController: ComponentActivity,
        b2BHeader: B2BHeader,
        verifyOtpReq: VerifyOtpReq,
        onFailure: (ErrorData) -> Unit,
        onSuccess: (VerifyOtpDomain) -> Unit
    ) {
        if(isNetworkAvailable(currentViewController)) {
            configVariable(currentViewController)
            { homeViewModel, resultHandler ->
                homeViewModel!!.verifyOtp(b2BHeader, verifyOtpReq)
                    .observe(currentViewController) { result ->
                        resultHandler!!.handle(
                            result, handleError = {
                                if (it != null) {
                                    onFailure(it)
                                }
                            }) { data ->
                            onSuccess(data!!)
                        }
                    }
            }
        }
    }
}