package com.zmalinowski.contactslist.contacts.data.validation

import com.zmalinowski.contactslist.contacts.core.ContactData
import com.zmalinowski.contactslist.contacts.core.ContactsDataSource
import io.reactivex.Single

/**
 * A wrapper for a "real" [ContactsDataSource] that is able to verify if it can be queried.
 * Allows to separate data-fetching logic from access checks.
 */
class ValidatingContactsDataSource(
        private val wrapped: ContactsDataSource,
        private val validator: DataAccessValidator)
    : ContactsDataSource {

    override fun getAllContacts(): Single<List<ContactData>> = validator.validate().andThen(wrapped.getAllContacts())
}