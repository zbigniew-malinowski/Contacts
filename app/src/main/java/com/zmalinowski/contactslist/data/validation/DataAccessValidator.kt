package com.zmalinowski.contactslist.data.validation

import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import com.github.ajalt.timberkt.w
import com.zmalinowski.contactslist.core.ContactsDataSource.PermissionDeniedException
import io.reactivex.Completable

interface DataAccessValidator {

    fun validate(): Completable

    class PermissionValidator(
            private val context: Context,
            private val permission: String
    ) : DataAccessValidator {

        override fun validate(): Completable = if (!hasPermission()) {
            w { "permission denied: $permission" }
            Completable.error(PermissionDeniedException(permission))
        } else {
            Completable.complete()
        }

        private fun hasPermission() =
                ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED
    }
}