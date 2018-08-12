package com.zmalinowski.contactslist.ui.list

sealed class ListModel {
    object Loading : ListModel()
    data class Contacts(val list: List<ListItem>) : ListModel()
    object Empty : ListModel()
    object GeneralError : ListModel()
    object PermissionDenied : ListModel()
}