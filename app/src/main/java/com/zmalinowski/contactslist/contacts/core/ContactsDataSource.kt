package com.zmalinowski.contactslist.contacts.core

import io.reactivex.Single

/**
 * Defines the entry point for the domain layer to get data in the proper format.
 *
 * Also lists all known specific errors that may happen and require special handling.
 *
 * Possible improvements: parameterized call, allowing to specify filtering, sorting and pagination.
 */
interface ContactsDataSource {

    fun getAllContacts(): Single<List<ContactData>>

    class PermissionDeniedException(message : String) : Exception(message)
}