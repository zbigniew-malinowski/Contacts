package com.zmalinowski.contactslist.ui.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.zmalinowski.contactslist.core.Action
import com.zmalinowski.contactslist.core.State
import com.zmalinowski.contactslist.framework.Store
import com.zmalinowski.contactslist.utils.mapNotNull
import com.zmalinowski.contactslist.utils.toLiveData
import com.zmalinowski.contactslist.utils.toObservable
import io.reactivex.Observable
import io.reactivex.ObservableSource

class ListViewModel(
        private val stateStore: Store<Action, State>,
        transformer : ListModelTransformer
) : ViewModel() {

    init {
        requestData()
    }

    val list : LiveData<ListModel> = stateStore.toObservable()
            .mapNotNull(transformer)
            .toLiveData()

    fun requestData() = stateStore.accept(Action.Load)
}
