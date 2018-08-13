package com.zmalinowski.contactslist.contacts.core

/**
 * Represents the state in any given moment. Sealed hierarchy allows to segregate data relevant
 * to particular cases.
 */
sealed class State {

    object Initial : State()
    object Loading : State()
    data class Loaded(val data: List<ContactData>) : State()
    data class Error(val cause: Throwable) : State()

    override fun toString(): String = javaClass.simpleName
}
