package com.zmalinowski.contactslist.data.validation

import com.nhaarman.mockito_kotlin.whenever
import com.zmalinowski.contactslist.core.ContactData
import com.zmalinowski.contactslist.core.ContactsDataSource
import io.reactivex.Completable
import io.reactivex.Single
import org.amshove.kluent.mock
import org.junit.Before
import org.junit.Test

private val ERROR = Exception("error")
private val SUCCESSFUL_VALIDATION = Completable.complete()
private val FAILED_VALIDATION = Completable.error(ERROR)
private val DATA = listOf(ContactData("id", "name", null, null))

class ValidatingContactsDataSourceTest {

    private val wrapped: ContactsDataSource = mock()
    private val validator: DataAccessValidator = mock()
    private lateinit var dataSource: ContactsDataSource

    @Before
    fun setUp() {
        whenever(wrapped.getAllContacts()).thenReturn(Single.just(DATA))
        dataSource = ValidatingContactsDataSource(wrapped, validator)
    }

    @Test
    fun `GIVEN successful validation WHEN requesting data THEN access is granted`() {

        whenever(validator.validate()).thenReturn(SUCCESSFUL_VALIDATION)

        val result = dataSource.getAllContacts().test()

        with(result) {
            assertValue(DATA)
            assertComplete()
        }
    }

    @Test
    fun `GIVEN failed validation WHEN requesting data THEN access is denied`() {
        whenever(validator.validate()).thenReturn(FAILED_VALIDATION)

        val result = dataSource.getAllContacts().test()

        result.assertError(ERROR)
    }
}

