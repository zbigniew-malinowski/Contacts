package com.zmalinowski.contactslist.contacts.data

import com.zmalinowski.contactslist.contacts.core.ContactData
import com.zmalinowski.contactslist.framework.Transformer
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import org.junit.Test

private val TRANSFORMER: Transformer<String, ContactData> = { ContactData(id = it, displayName = it, photo = null, thumbnail = null) }
private val COMPARATOR: Comparator<ContactData> = compareBy { it.displayName }
private val INPUT_DATA = listOf("c", "b", "a")
private val OUTPUT_DATA = INPUT_DATA.mapNotNull(TRANSFORMER).sortedWith(COMPARATOR)

class ContactsDataSourceImplTest {

    private val dataProvider: () -> Observable<String> = { Observable.fromIterable(INPUT_DATA) }
    private val dataSource = ContactsDataSourceImpl(dataProvider, TRANSFORMER, COMPARATOR, Schedulers.trampoline(), Schedulers.trampoline())

    @Test
    fun `dataSource returns sorted data`() {

        val result = dataSource.getAllContacts().test()

        result.assertValue(OUTPUT_DATA)
    }
}