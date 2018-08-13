package com.zmalinowski.contactslist.contacts.ui.details.data

import android.net.Uri

/**
 * Data model for the contact details screen.
 */
sealed class DetailsModel{

    object Loading : DetailsModel()
    data class Details(
            val name : String,
            val photo : Uri?
    ) : DetailsModel()
    object GeneralError : DetailsModel()
    object PermissionDenied : DetailsModel()
}