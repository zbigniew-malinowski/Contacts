package com.zmalinowski.contactslist.ui.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.zmalinowski.contactslist.core.Action
import com.zmalinowski.contactslist.core.State
import com.zmalinowski.contactslist.framework.Store
import com.zmalinowski.contactslist.ui.list.ListModel
import com.zmalinowski.contactslist.ui.list.ListModelTransformer
import com.zmalinowski.contactslist.utils.mapNotNull
import com.zmalinowski.contactslist.utils.toLiveData
import com.zmalinowski.contactslist.utils.toObservable

class DetailsViewModel(
        private val stateStore: Store<Action, State>,
        transformer : ListModelTransformer
) : ViewModel() {

    init {
        requestData()
    }

    val details : LiveData<ListModel> = stateStore.toObservable()
            .mapNotNull(transformer)
            .toLiveData()

    fun requestData() = stateStore.accept(Action.Load)
}
