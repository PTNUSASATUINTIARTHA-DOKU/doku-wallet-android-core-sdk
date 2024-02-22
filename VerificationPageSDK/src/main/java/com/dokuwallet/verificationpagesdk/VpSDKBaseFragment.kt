package com.dokuwallet.verificationpagesdk

import android.app.Dialog
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding
import com.dokuwallet.coresdk.utils.CommonUtils
import com.dokuwallet.verificationpagesdk.ui.VpSDKHomeActivity
import com.dokuwallet.verificationpagesdk.utils.PreferenceManager
import com.dokuwallet.verificationpagesdk.utils.ResultHandler
import com.dokuwallet.verificationpagesdk.utils.VerificationPage
import com.google.android.material.bottomsheet.BottomSheetDialog

internal abstract class VpSDKBaseFragment<VB : ViewBinding>(
    private val inflate: (LayoutInflater, ViewGroup?, Boolean) -> VB,
    private val hideToolbar: Boolean = false,
) : Fragment() {

    private var _binding: VB? = null
    val binding get() = _binding!!

    private var indicatorView: Dialog? = null

    val bottomSheet by lazy {
        BottomSheetDialog(requireContext())
    }

    val resultHandler by lazy {
        ResultHandler(requireContext(), indicatorView, bottomSheet)
    }

    val baseCachePath: String by lazy {
        requireActivity().cacheDir.absolutePath
    }

    val prefManager: PreferenceManager by lazy {
        PreferenceManager(requireContext())
    }

    var token: String? = null

    var isBackPressed = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = inflate.invoke(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.root.setBackgroundColor(Color.parseColor(VerificationPage.clientTheme.backgroundColor))
        onClickHandler()

        isBackPressed = (requireActivity() as VpSDKHomeActivity).isBackPressed

        token = prefManager.getVerificationToken()
    }

    fun showActivityIndicator() {
        hideActivityIndicator()
        indicatorView = CommonUtils.showLoadingDialog(requireContext())
    }

    fun hideActivityIndicator() {
        if (indicatorView != null && indicatorView?.isShowing == true) {
            indicatorView!!.cancel()
        }
    }

    override fun onResume() {
        super.onResume()
        if (hideToolbar) (activity as AppCompatActivity).supportActionBar?.hide()
    }

    override fun onStop() {
        super.onStop()
        if (hideToolbar) (activity as AppCompatActivity).supportActionBar?.show()
    }

    protected abstract fun onClickHandler()

    fun restartFragment(fragmentId: Int) {
        val navController = findNavController()
        navController.run {
            popBackStack()
            navigate(fragmentId)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}