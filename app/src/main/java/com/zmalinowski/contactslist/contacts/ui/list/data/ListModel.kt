package com.zmalinowski.contactslist.contacts.ui.list.data

import android.net.Uri

/**
 * Data model for the contact list screen.
 */
sealed class ListModel {
    object Loading : ListModel()
    data class Contacts(val list: List<ListItem>) : ListModel()
    object Empty : ListModel()
    object GeneralError : ListModel()
    object PermissionDenied : ListModel()
}

data class ListItem(
        val contactId : String,
        val displayName: String,
        val thumbnail: Uri?
)