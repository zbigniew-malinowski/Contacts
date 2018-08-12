package com.zmalinowski.contactslist.ui.details

import android.net.Uri

sealed class DetailsModel{

    object Loading : DetailsModel()
    data class Details(
            val name : String,
            val photo : Uri?,
            val color : Int
    ) : DetailsModel()
    object GeneralError : DetailsModel()
    object PermissionDenied : DetailsModel()
}