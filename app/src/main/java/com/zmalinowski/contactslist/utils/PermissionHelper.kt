package com.zmalinowski.contactslist.utils

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.Settings
import androidx.core.app.ActivityCompat
import androidx.fragment.app.FragmentActivity

private const val REQUEST_CODE = 1234

class PermissionHelper(
        private val activity: FragmentActivity,
        private vararg val permissionList: String,
        private val onPermissionsGranted: () -> Unit
) {

    fun requestPermission() {
        if (canShowDialog()) {
            showDialog()
        } else {
            goToSettings()
        }
    }

    private fun canShowDialog(): Boolean =
            !permissionList.any { ActivityCompat.shouldShowRequestPermissionRationale(activity, it) }

    private fun goToSettings() {
        activity.startActivity(Intent().apply {
            action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
            data = Uri.fromParts("package", activity.callingPackage, null)
        })
    }

    private fun showDialog() {
        ActivityCompat.requestPermissions(activity,
                permissionList,
                REQUEST_CODE)
    }

    fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (requestCode == REQUEST_CODE && grantResults.all { it == PackageManager.PERMISSION_GRANTED }) {
            onPermissionsGranted()
        }
    }
}