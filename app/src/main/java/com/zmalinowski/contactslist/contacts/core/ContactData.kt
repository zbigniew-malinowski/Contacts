package com.zmalinowski.contactslist.contacts.core

import android.net.Uri

/**
 * Representation of a single contact in core/domain layer.
 */
data class ContactData(
        val id : String,
        val displayName : String,
        val photo : Uri?,
        val thumbnail : Uri?
)