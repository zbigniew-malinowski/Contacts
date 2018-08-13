package com.zmalinowski.contactslist.contacts.core

/**
 * For this use case there is only one action (a request to load the data).
 *
 * This approach allows to easily add more without big changes in the interfaces
 * and propagate ui events through reactive chains.
 */
sealed class Action {

    object Load : Action()

    override fun toString(): String = javaClass.simpleName
}