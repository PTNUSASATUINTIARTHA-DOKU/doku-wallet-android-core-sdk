package com.dokuwallet.verificationpagesdk.utils

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import com.dokuwallet.verificationpagesdk.R

internal object PermissionHelper {

    const val CAMERA_PERMISSION = Manifest.permission.CAMERA

    val LOCATION_PERMISSIONS = arrayOf(
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION
    )

    fun locationPermissionsGranted(context: Context) = LOCATION_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(
            context, it
        ) == PackageManager.PERMISSION_GRANTED
    }

    fun cameraPermissionsGranted(context: Context) = ContextCompat.checkSelfPermission(
        context,
        CAMERA_PERMISSION
    ) == PackageManager.PERMISSION_GRANTED

    fun showBottomPermissionLocation(context: Context, onAllowClick: () -> Unit) {
        VPUtils.showBottomSheetKey(
            context = context,
            title = context.getString(R.string.access_location_title),
            caption = context.getString(R.string.access_location_caption),
            onAllowClick = {
                onAllowClick()
            }
        )
    }

    fun showBottomPermissionCamera(context: Context, onAllowClick: () -> Unit) {
        VPUtils.showBottomSheetKey(
            context = context,
            title = context.getString(R.string.access_camera_title),
            caption = context.getString(R.string.access_camera_caption),
            onAllowClick = {
                onAllowClick()
            }
        )
    }
}