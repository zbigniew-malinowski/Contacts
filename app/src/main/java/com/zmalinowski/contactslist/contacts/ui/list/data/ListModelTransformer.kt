package com.zmalinowski.contactslist.contacts.ui.list.data

import com.zmalinowski.contactslist.contacts.core.ContactData
import com.zmalinowski.contactslist.contacts.core.ContactsDataSource.PermissionDeniedException
import com.zmalinowski.contactslist.contacts.core.State
import com.zmalinowski.contactslist.contacts.ui.list.data.ListModel.*
import com.zmalinowski.contactslist.framework.Transformer

object ListModelTransformer : Transformer<State, ListModel> {

    override fun invoke(state: State): ListModel? = when (state) {
        is State.Loading -> Loading
        is State.Error -> when (state.cause) {
            is PermissionDeniedException -> PermissionDenied
            else -> GeneralError
        }
        is State.Loaded -> when {
            state.data.isNotEmpty() -> Contacts(state.data.map { it.toListItem() })
            else -> Empty
        }
        is State.Initial -> null
    }

    private fun ContactData.toListItem(): ListItem = ListItem(
            contactId = id,
            displayName = displayName,
            thumbnail = thumbnail
    )
}