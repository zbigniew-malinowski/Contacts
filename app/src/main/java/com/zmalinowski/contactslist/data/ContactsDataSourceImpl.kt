package com.zmalinowski.contactslist.data

import com.zmalinowski.contactslist.core.ContactData
import com.zmalinowski.contactslist.core.ContactsDataSource
import com.zmalinowski.contactslist.framework.Transformer
import com.zmalinowski.contactslist.utils.mapNotNull
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class ContactsDataSourceImpl<RawContactData : Any>(
        private val rawDataProvider: DataProvider<RawContactData>,
        private val transformer: Transformer<RawContactData, ContactData>,
        private val comparator: Comparator<ContactData> = compareBy { it.displayName },
        private val subscribeOnScheduler: Scheduler = Schedulers.io(),
        private val observeOnScheduler: Scheduler = AndroidSchedulers.mainThread()
) : ContactsDataSource {

    override fun getAllContacts(): Single<List<ContactData>> =
            rawDataProvider.invoke()
                    .mapNotNull(transformer)
                    .toSortedList(comparator)
                    .subscribeOn(subscribeOnScheduler)
                    .observeOn(observeOnScheduler)
}

typealias DataProvider<T> = () -> Observable<T>