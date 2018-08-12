package com.zmalinowski.contactslist.data.validation

import com.zmalinowski.contactslist.core.ContactData
import com.zmalinowski.contactslist.core.ContactsDataSource
import io.reactivex.Single

class ValidatingContactsDataSource(
        private val wrapped: ContactsDataSource,
        private val validator: DataAccessValidator)
    : ContactsDataSource {

    override fun getAllContacts(): Single<List<ContactData>> = validator.validate().andThen(wrapped.getAllContacts())
}