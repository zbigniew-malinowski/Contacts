package com.zmalinowski.contactslist.contacts.data

import com.zmalinowski.contactslist.contacts.core.ContactData
import com.zmalinowski.contactslist.framework.Transformer
import ir.mirrajabi.rxcontacts.Contact

/**
 * Transforms data obtained from the library to the domain format.
 */
object ContactTransformer : Transformer<Contact, ContactData> {

    override fun invoke(contact: Contact): ContactData? = contact.toContactData().takeIf { contact.isVisible() }

    private fun Contact.isVisible() = inVisibleGroup == 1

    private fun Contact.toContactData(): ContactData = ContactData(
            id = id.toString(),
            displayName = displayName,
            photo = photo,
            thumbnail = thumbnail
    )
}