package com.zmalinowski.contactslist.core

import io.reactivex.Observable

interface ContactsRepository : ContactsDataSource {

    fun getContactById(id: String): Observable<ContactData>
}