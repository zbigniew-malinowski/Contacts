package com.zmalinowski.contactslist.contacts.core

import com.github.ajalt.timberkt.Timber
import com.nhaarman.mockito_kotlin.whenever
import com.zmalinowski.contactslist.TestTree
import com.zmalinowski.contactslist.contacts.core.State.*
import com.zmalinowski.contactslist.framework.Store
import com.zmalinowski.contactslist.utils.toObservable
import io.reactivex.Single
import org.amshove.kluent.mock
import org.amshove.kluent.shouldEqual
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.*


private val DATA: List<ContactData> = listOf(ContactData("1", "a", null, null))
private val ERROR = Exception("ooops!")
private val DATA_LOADED = Loaded(DATA)
private val DATA_ERROR = Error(ERROR)
val LOADING_SUCCESSFUL: Single<List<ContactData>> = Single.just(DATA)
val LOADING_FAILED: Single<List<ContactData>> = Single.error(ERROR)


class ContactsActionExecutorTest : Spek({

    //    uncomment to enable logging
        Timber.plant(TestTree())

    describe(ContactsActionExecutor::class.java.simpleName) {
        val action = Action.Load
        context("loading is successful") {
            val dataSource: ContactsDataSource = mock()
            val subject = ContactsActionExecutor(dataSource)
            whenever(dataSource.getAllContacts()).thenReturn(LOADING_SUCCESSFUL)
            val testCases = mapOf(
                    Initial to listOf(Initial, Loading, DATA_LOADED),
                    Loading to listOf(Loading),
                    DATA_LOADED to listOf(DATA_LOADED),
                    DATA_ERROR to listOf(DATA_ERROR, Loading, DATA_LOADED)
            )
            testCases.forEach { initialState, expectedEmittedStates ->
                val store = Store.create(initialState, subject)
                val result = store.toObservable().test()
                given("initial state: $initialState") {
                    on("action $action") {
                        store.accept(action)
                        it("should emit $expectedEmittedStates") {
                            result.values() shouldEqual expectedEmittedStates
                        }
                    }
                }
            }
        }

        context("loading fails") {
            val dataSource: ContactsDataSource = mock()
            val subject = ContactsActionExecutor(dataSource)
            whenever(dataSource.getAllContacts()).thenReturn(LOADING_FAILED)
            val testCases = mapOf(
                    Initial to listOf(Initial, Loading, DATA_ERROR),
                    Loading to listOf(Loading),
                    DATA_LOADED to listOf(DATA_LOADED),
                    DATA_ERROR to listOf(DATA_ERROR, Loading, DATA_ERROR)
            )
            testCases.forEach { initialState, expectedEmittedStates ->
                val store = Store.create(initialState, subject)
                val result = store.toObservable().test()
                given("initial state: $initialState") {
                    on("action $action") {
                        store.accept(action)
                        it("should emit $expectedEmittedStates") {
                            result.values() shouldEqual expectedEmittedStates
                        }
                    }
                }
            }
        }
    }

})
