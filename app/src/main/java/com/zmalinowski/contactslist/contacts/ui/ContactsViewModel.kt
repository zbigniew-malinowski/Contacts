package com.zmalinowski.contactslist.contacts.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.zmalinowski.contactslist.contacts.core.Action
import com.zmalinowski.contactslist.contacts.core.State
import com.zmalinowski.contactslist.framework.Store
import com.zmalinowski.contactslist.framework.Transformer
import com.zmalinowski.contactslist.utils.mapNotNull
import com.zmalinowski.contactslist.utils.toLiveData
import com.zmalinowski.contactslist.utils.toObservable

class ContactsViewModel<T : Any>(
        private val stateStore: Store<Action, State>,
        transformer : Transformer<State, T>
) : ViewModel() {

    init {
        requestData()
    }

    val data : LiveData<T> = stateStore.toObservable()
            .mapNotNull(transformer)
            .toLiveData()

    fun requestData() = stateStore.accept(Action.Load)
}