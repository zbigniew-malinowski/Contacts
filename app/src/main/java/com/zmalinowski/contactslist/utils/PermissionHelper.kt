package com.zmalinowski.contactslist.utils

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.Settings
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment

private const val REQUEST_CODE_DIALOG = 1234
private const val REQUEST_CODE_SETTINGS = 1235

/**
 * Encapsulation of Android permission API.
 */
class PermissionHelper(
        private val fragment: Fragment,
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
            !permissionList.any { fragment.shouldShowRequestPermissionRationale(it) }

    private fun goToSettings() {
        fragment.startActivityForResult(
                Intent().apply {
                    action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                    data = Uri.fromParts("package", fragment.activity!!.packageName, null)
                },
                REQUEST_CODE_SETTINGS)
    }

    private fun showDialog() {
        fragment.requestPermissions(permissionList,REQUEST_CODE_DIALOG)
    }

    fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (requestCode == REQUEST_CODE_DIALOG && grantResults.all { it == PackageManager.PERMISSION_GRANTED }) {
            onPermissionsGranted()
        }
    }

    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_CODE_SETTINGS && permissionList.all { hasPermission(it) }) {
            onPermissionsGranted()
        }
    }

    private fun hasPermission(permission: String): Boolean =
            ContextCompat.checkSelfPermission(fragment.context!!, permission) == PackageManager.PERMISSION_GRANTED
}