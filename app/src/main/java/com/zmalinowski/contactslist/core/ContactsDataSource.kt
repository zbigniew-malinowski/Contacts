package com.zmalinowski.contactslist.core

import io.reactivex.Single

interface ContactsDataSource {

    fun getAllContacts(): Single<List<ContactData>>

    class PermissionDeniedException(message : String) : Exception(message)
}