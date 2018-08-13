package com.zmalinowski.contactslist.contacts.data.validation

import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import com.github.ajalt.timberkt.w
import com.zmalinowski.contactslist.contacts.core.ContactsDataSource.PermissionDeniedException
import io.reactivex.Completable

/**
 * A gatekeeper checking if an access to a particular data is allowed, like verifying permissions,
 * checking if the user is signed in or has an active subscription.
 */
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