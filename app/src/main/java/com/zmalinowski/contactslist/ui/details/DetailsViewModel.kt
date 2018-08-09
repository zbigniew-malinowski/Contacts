package com.zmalinowski.contactslist.ui.details

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class DetailsViewModel : ViewModel() {

    val details: MutableLiveData<Details> = MutableLiveData()
}
