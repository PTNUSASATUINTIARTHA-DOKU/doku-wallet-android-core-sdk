package com.dokuwallet.verificationpagesdk.ui.identity

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.EditText
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.widget.doOnTextChanged
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.dokuwallet.coresdk.data.network.parameter.vpparameter.UpdateOcrReq
import com.dokuwallet.verificationpagesdk.R
import com.dokuwallet.verificationpagesdk.VpSDKBaseFragment
import com.dokuwallet.verificationpagesdk.databinding.VpSdkFragmentIdentityBinding
import com.dokuwallet.verificationpagesdk.databinding.VpSdkPreviewImageBinding
import com.dokuwallet.verificationpagesdk.utils.DateHelper
import com.dokuwallet.verificationpagesdk.utils.DateHelper.withTimeAtStartOfDay
import com.dokuwallet.verificationpagesdk.utils.PermissionHelper
import com.dokuwallet.verificationpagesdk.utils.VPUtils.onlyCapsFirst
import com.dokuwallet.verificationpagesdk.utils.VPUtils.stringTrim
import com.dokuwallet.verificationpagesdk.utils.VPUtils.updateColor
import com.dokuwallet.verificationpagesdk.utils.VerificationPage
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textview.MaterialTextView
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale


internal class IdentityFragment : VpSDKBaseFragment<VpSdkFragmentIdentityBinding>(
    VpSdkFragmentIdentityBinding::inflate
) {
    private val viewModel: IdentityViewModel by viewModel()

    private val cal = Calendar.getInstance()
    private var isNeedUpdateOcr = false

    private val sharedPref by lazy {
        activity?.getPreferences(Context.MODE_PRIVATE)
    }

    private lateinit var listField: List<TextInputEditText>

    private lateinit var listErrText: List<MaterialTextView>

    private lateinit var errorMessageMap: Map<TextInputEditText, String>

    private var isKtp: Boolean = true

    private var isNewCreation = true
    private var editTextFocus: EditText? = null

    private var identityValid = false
    private var nameValid = false
    private var birthValid = false
    private var othersValid = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setClientTheme()

        with(binding) {
            listField = listOf(
                edtNomorIdentitas,
                edtNamaLengkap,
                edtTempatLahir,
                edtTanggalLahir,
                edtPekerjaan,
                edtAlamat,
            )

            listErrText = listOf(
                errNomorIdentitas,
                errNamaLengkap,
                errTempatLahir,
                errTanggalLahir,
                errPekerjaan,
                errAlamat,
            )

            errorMessageMap = mapOf(
                edtNomorIdentitas to getString(R.string.nomor_identitas),
                edtNamaLengkap to getString(R.string.nama_lengkap),
                edtTempatLahir to getString(R.string.tempat_lahir),
                edtTanggalLahir to getString(R.string.tanggal_lahir),
                edtPekerjaan to getString(R.string.pekerjaan),
                edtAlamat to getString(R.string.alamat)
            )
        }

        getVerificationData()
        fieldErrorHandler()
    }

    override fun onResume() {
        super.onResume()
        listField.forEach { editText ->
            editText.setText(sharedPref?.getString(editText.id.toString(), ""))
        }
    }

    override fun onPause() {
        super.onPause()
        val editor = sharedPref?.edit()
        listField.forEach { editText ->
            editor?.putString(editText.id.toString(), editText.text.toString().trim())
        }
        editor?.apply()
    }

    private fun setClientTheme() {
        with(binding) {
            txtTitle.setTextColor(Color.parseColor(VerificationPage.clientTheme.primaryButton.background))
            viewProgress1.backgroundTintList =
                ColorStateList.valueOf(Color.parseColor(VerificationPage.clientTheme.primaryButton.background))
            viewProgress2.backgroundTintList =
                ColorStateList.valueOf(Color.parseColor(VerificationPage.clientTheme.primaryButton.background))
            viewProgress3.backgroundTintList =
                ColorStateList.valueOf(Color.parseColor(VerificationPage.clientTheme.primaryButton.background))
            btnNext.updateColor()
        }
        setupKycFooter()
    }

    private fun setupKycFooter() {
        if (VerificationPage.verificationPageConfig.hideKycFooter) {
            binding.footerInformation.root.visibility = View.GONE
        } else {
            binding.footerInformation.root.visibility = View.VISIBLE
        }
    }

    override fun onClickHandler() {
        with(binding) {
            btnNext.setOnClickListener {
                resultHandler.isLoading(true)
                if (isNeedUpdateOcr) {
                    updateOcr()
                } else {
                    setStage()
                }
            }
            txtPreviewKtp.setOnClickListener {
                if (imgKtp.drawable != null) {
                    loadImagePreview(imgKtp.drawable!!)
                }
            }
            txtTakeKtp.setOnClickListener {
                isKtp = true
                if (PermissionHelper.cameraPermissionsGranted(requireContext())) {
                    retakeKtp()
                } else {
                    PermissionHelper.showBottomPermissionCamera(requireContext()) {
                        cameraPermissionLauncher.launch(PermissionHelper.CAMERA_PERMISSION)
                    }
                }
            }
            txtPreviewSelfie.setOnClickListener {
                if (imgSelfie.drawable != null) {
                    loadImagePreview(imgSelfie.drawable!!)
                }
            }
            txtTakeSelfie.setOnClickListener {
                isKtp = false
                if (PermissionHelper.cameraPermissionsGranted(requireContext())) {
                    retakeSelfie()
                } else {
                    PermissionHelper.showBottomPermissionCamera(requireContext()) {
                        cameraPermissionLauncher.launch(PermissionHelper.CAMERA_PERMISSION)
                    }
                }
            }
            edtTanggalLahir.setOnClickListener {
                showCalendar(edtTanggalLahir)
            }
        }
    }

    private fun loadImagePreview(drawable: Drawable) {
        val previewBinding = VpSdkPreviewImageBinding.inflate(LayoutInflater.from(context))
        val builder = Dialog(
            requireContext(),
            com.dokuwallet.coresdk.R.style.Theme_DokuSDK_WrapContentDialog
        )

        previewBinding.apply {
            btnClose.setOnClickListener {
                builder.dismiss()
            }
            imgPreview.setImageDrawable(drawable)
        }

        builder.apply {
            setCancelable(true)
            setContentView(previewBinding.root)

            val layoutParams = WindowManager.LayoutParams()
            layoutParams.copyFrom(window!!.attributes)
            layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT
            layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT
            window!!.attributes = layoutParams

            show()
        }
    }

    private fun getVerificationData() {
        resultHandler.isLoading(true)
        viewModel.getVerificationData(token.toString()).observe(viewLifecycleOwner) { result ->
            resultHandler.handle(result) {
                if (it?.data == null) return@handle
                val identityData = it.data!!

                with(binding) {
                    if (edtNomorIdentitas.text.isNullOrEmpty()) edtNomorIdentitas.setText(
                        identityData.idNumber
                    )
                    if (edtNamaLengkap.text.isNullOrEmpty()) edtNamaLengkap.setText(identityData.name)
                    if (edtAlamat.text.isNullOrEmpty()) edtAlamat.setText(identityData.address)
                }
                getCapturedId()
            }
        }
    }

    private fun getCapturedId() {
        viewModel.getCapturedId(token.toString()).observe(viewLifecycleOwner) { result ->
            resultHandler.handle(result) {
                if (it?.data == null) return@handle

                Glide.with(requireContext())
                    .load(it.data?.url)
                    .into(binding.imgKtp)

                getCapturedSelfie()
            }
        }
    }

    private fun getCapturedSelfie() {
        viewModel.getCapturedSelfie(token.toString()).observe(viewLifecycleOwner) { result ->
            resultHandler.handle(result) {
                if (it?.data == null) return@handle

                Glide.with(requireContext())
                    .load(it.data!!.url)
                    .into(binding.imgSelfie)

                resultHandler.isLoading(false)
            }
        }
    }

    private fun updateOcr() {
        with(binding) {
            val updateOcr = UpdateOcrReq(
                address = edtAlamat.stringTrim(),
                gender = "-",
                district = "-",
                name = edtNamaLengkap.stringTrim(),
                idNumber = edtNomorIdentitas.stringTrim(),
                subDistrict = "-",
                religion = "-"
            )
            viewModel.updateOcr(token.toString(), updateOcr).observe(viewLifecycleOwner) { result ->
                resultHandler.handle(result) {
                    navigateToGuideLiveness()
                    sharedPref?.edit()?.clear()?.apply()
                }
            }
        }
    }

    private fun setStage() {
        if (isBackPressed) {
            navigateToGuideLiveness()
        } else {
            viewModel.setStage(token.toString(), 5).observe(viewLifecycleOwner) { result ->
                resultHandler.handle(result) {
                    navigateToGuideLiveness()
                }
            }
        }
    }

    private fun navigateToGuideLiveness() {
        findNavController().navigate(
            IdentityFragmentDirections.actionIdentityFragmentToGuideLivenessFragment()
        )
        sharedPref?.edit()?.clear()?.apply()
        resultHandler.isLoading(false)
    }

    private fun showCalendar(editText: EditText) {
        val today = Calendar.getInstance().timeInMillis

        val datePicker = DatePickerDialog(
            requireContext(),
            AlertDialog.THEME_HOLO_LIGHT,
            { _, year, month, dayOfMonth ->
                cal.set(year, month, dayOfMonth)
                editText.setText(DateHelper.calendarToString(cal, DateHelper.birthDateFormat))
            },
            cal[Calendar.YEAR],
            cal[Calendar.MONTH],
            cal[Calendar.DAY_OF_MONTH],
        )
        datePicker.datePicker.maxDate = today

        datePicker.show()
    }

    private fun fieldErrorHandler() {
        with(binding) {
            listField.forEachIndexed { index, editText ->
                setEmptyError(index)
                isNeedUpdateOcr = isNeedUpdateOcr || editText.text.toString().isEmpty()
                when (editText) {
                    edtNomorIdentitas -> validateIdNumber(editText, index)
                    edtNamaLengkap -> validateName(editText, index)
                    edtTanggalLahir -> validateBirthdate(editText, index)
                    else -> editText.errorHandler(index)
                }
            }
        }
    }

    private fun validateIdNumber(editText: EditText, index: Int) {
        editText.errorHandler(index) {
            identityValid = it.length == 16
            updateButton()

            if (it.length != 16) {
                listErrText[index].text = getString(R.string.ktp_invalid)
                listErrText[index].visibility = View.VISIBLE
            } else {
                listErrText[index].visibility = View.GONE
            }
        }
    }

    private fun validateName(editText: EditText, index: Int) {
        editText.errorHandler(index) {
            nameValid = it.length >= 3
            updateButton()

            if (it.length < 3) {
                listErrText[index].text = getString(R.string.name_invalid)
                listErrText[index].visibility = View.VISIBLE
            } else {
                listErrText[index].visibility = View.GONE
            }
        }
    }

    private fun validateBirthdate(editText: EditText, index: Int) {
        editText.errorHandler(index) {
            val dateFormat = SimpleDateFormat(DateHelper.birthDateFormat, Locale.getDefault())
            val cal = Calendar.getInstance()
            try {
                cal.time = dateFormat.parse(editText.text.toString())!!
            } catch (e: ParseException) {
                e.printStackTrace()
            }
            val minAgeCal = Calendar.getInstance()
            minAgeCal.apply {
                add(Calendar.YEAR, -17)
                withTimeAtStartOfDay()
            }

            birthValid = !cal.after(minAgeCal)
            updateButton()

            if (cal.after(minAgeCal)) {
                listErrText[index].text = getString(R.string.age_under_17)
                listErrText[index].visibility = View.VISIBLE
            } else {
                listErrText[index].visibility = View.GONE
            }
        }
    }

    private fun EditText.errorHandler(
        index: Int,
        otherCheck: (text: CharSequence) -> Unit = {}
    ) {
        doOnTextChanged { text, _, _, _ ->
            othersValid = true

            listField.forEach {
                if (it.text.isNullOrEmpty()) {
                    othersValid = false
                    return@forEach
                }
            }

            updateButton()

            if (text.isNullOrEmpty()) {
                if (editTextFocus == null) {
                    editTextFocus = this
                    editTextFocus?.requestFocus()
                }
                if (isNewCreation) return@doOnTextChanged
                setEmptyError(index)
                return@doOnTextChanged
            } else {
                isNewCreation = false
                listErrText[index].visibility = View.GONE
                otherCheck(text)
            }
        }
    }

    private fun updateButton() {
        binding.btnNext.apply {
            isEnabled = identityValid && nameValid && birthValid && othersValid
            updateColor()
        }
    }

    private fun retakeKtp() {
        findNavController().navigate(
            IdentityFragmentDirections.actionIdentityFragmentToCamKtpFragment()
                .setIsRetake(true)
        )
    }

    private fun retakeSelfie() {
        findNavController().navigate(
            IdentityFragmentDirections.actionIdentityFragmentToCamSelfieFragment()
                .setIsRetake(true)
        )
    }

    private val cameraPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { granted ->
        if (granted) {
            if (isKtp) retakeKtp() else retakeSelfie()
        } else {
            Toast.makeText(context, "Izin Camera Ditolak", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setEmptyError(index: Int) {
        if (isNewCreation) return
        val errorMessage = errorMessageMap.getValue(listField[index])
        val errorText = getString(
            R.string.empty_error_message,
            errorMessage.onlyCapsFirst(),
            getString(R.string.wajib_diisi)
        )
        listErrText[index].text = errorText
        listErrText[index].visibility = View.VISIBLE
    }
}
