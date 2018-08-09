package com.zmalinowski.contactslist.core

import io.reactivex.Observable

interface ContactsDataSource {

    fun getAllContacts(): Observable<ContactData>
}