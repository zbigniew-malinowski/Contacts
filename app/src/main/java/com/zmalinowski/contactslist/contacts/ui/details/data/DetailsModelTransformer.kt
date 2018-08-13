package com.zmalinowski.contactslist.contacts.ui.details.data

import com.zmalinowski.contactslist.contacts.core.ContactData
import com.zmalinowski.contactslist.contacts.core.ContactsDataSource.PermissionDeniedException
import com.zmalinowski.contactslist.contacts.core.State
import com.zmalinowski.contactslist.contacts.ui.details.data.DetailsModel.*
import com.zmalinowski.contactslist.framework.Transformer

class DetailsModelTransformer(private val contactId: String) : Transformer<State, DetailsModel> {
    override fun invoke(state: State): DetailsModel? = when (state) {
        is State.Initial -> null
        is State.Loading -> Loading
        is State.Loaded -> state.data.firstOrNull { it.id == contactId }?.toDetails()
        is State.Error -> when (state.cause) {
            is PermissionDeniedException -> PermissionDenied
            else -> GeneralError
        }
    }
}

private fun ContactData.toDetails() = Details(
        name = displayName,
        photo = photo
)
