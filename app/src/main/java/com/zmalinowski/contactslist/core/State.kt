package com.zmalinowski.contactslist.core

sealed class State {

    object Initial : State()
    object Loading : State()
    data class Loaded(val data: List<ContactData>) : State()
    data class Error(val cause: Throwable) : State()

    override fun toString(): String = javaClass.simpleName
}
