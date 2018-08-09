package com.zmalinowski.contactslist.data

import android.content.Context
import com.zmalinowski.contactslist.core.ContactData
import com.zmalinowski.contactslist.core.ContactsDataSource
import io.reactivex.Observable

class ContactsDataSourceImpl(private val context: Context) : ContactsDataSource{

    override fun getAllContacts(): Observable<ContactData> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}