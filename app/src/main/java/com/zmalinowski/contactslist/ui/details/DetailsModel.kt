package com.zmalinowski.contactslist.ui.details

sealed class DetailsModel{

    object Loading : DetailsModel()
    data class Details(
            val name : String,
            val photo : String?,
            val color : Int
    )
    object GeneralError : DetailsModel()
    object PermissionDenied : DetailsModel()
}