package com.zmalinowski.contactslist.data

import com.zmalinowski.contactslist.core.ContactData
import com.zmalinowski.contactslist.framework.Transformer
import ir.mirrajabi.rxcontacts.Contact

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