package com.zmalinowski.contactslist.core

sealed class Action {

    object Load : Action()

    override fun toString(): String = javaClass.simpleName
}