package com.zmalinowski.contactslist.contacts.ui.details.view

import com.zmalinowski.contactslist.R
import com.zmalinowski.contactslist.contacts.ui.ContactsBaseFragment
import com.zmalinowski.contactslist.contacts.ui.ContactsViewModel
import com.zmalinowski.contactslist.contacts.ui.PermissionsScreenHolder
import com.zmalinowski.contactslist.contacts.ui.details.data.DetailsModel
import com.zmalinowski.contactslist.contacts.ui.details.data.DetailsModel.*
import com.zmalinowski.contactslist.widget.screenview.ScreenAdapter
import com.zmalinowski.contactslist.widget.screenview.ScreenHolder
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class DetailsFragment : ContactsBaseFragment<DetailsModel>() {

    private val contactId get() = DetailsFragmentArgs.fromBundle(arguments).contactId
    override val viewModel: ContactsViewModel<DetailsModel> by viewModel(name = "details") { parametersOf(contactId) }

    override val singleItemAdapter = ScreenAdapter<DetailsModel> { parent, item ->
        when (item) {
            is Loading -> ScreenHolder.Static(parent, R.layout.view_loading)
            is Details -> ContactDetailsScreenHolder(parent)
            is GeneralError -> ScreenHolder.Static(parent, R.layout.view_error)
            is PermissionDenied -> PermissionsScreenHolder(parent, permissionHelper)
        }
    }
}
