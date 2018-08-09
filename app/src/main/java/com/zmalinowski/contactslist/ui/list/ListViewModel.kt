package com.zmalinowski.contactslist.ui.list

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ListViewModel : ViewModel() {

    val list : MutableLiveData<List<ListItem>> = MutableLiveData()
}
