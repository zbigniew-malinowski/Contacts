package com.zmalinowski.contactslist.contacts.ui.list.view

import androidx.navigation.fragment.NavHostFragment.findNavController
import com.zmalinowski.contactslist.R
import com.zmalinowski.contactslist.contacts.ui.ContactsBaseFragment
import com.zmalinowski.contactslist.contacts.ui.ContactsViewModel
import com.zmalinowski.contactslist.contacts.ui.PermissionsScreenHolder
import com.zmalinowski.contactslist.contacts.ui.details.view.DetailsFragmentArgs
import com.zmalinowski.contactslist.contacts.ui.list.data.ListModel
import com.zmalinowski.contactslist.contacts.ui.list.data.ListModel.*
import com.zmalinowski.contactslist.widget.screenview.ScreenAdapter
import com.zmalinowski.contactslist.widget.screenview.ScreenHolder.Static
import org.koin.androidx.viewmodel.ext.android.viewModel

class ListFragment : ContactsBaseFragment<ListModel>() {

    override val viewModel: ContactsViewModel<ListModel> by viewModel(name = "list")

    override val singleItemAdapter = ScreenAdapter<ListModel> { parent, item ->
        when (item) {
            is Loading -> Static(parent, R.layout.view_loading)
            is Contacts -> ContactListScreenHolder(parent, this::onContactSelected)
            is Empty -> Static(parent, R.layout.view_list_empty)
            is GeneralError -> Static(parent, R.layout.view_error)
            is PermissionDenied -> PermissionsScreenHolder(parent, permissionHelper)
        }
    }

    private fun onContactSelected(contactId: String) {
        val args = DetailsFragmentArgs.Builder(contactId).build().toBundle()
        findNavController(this).navigate(R.id.action_list_to_details, args)
    }
}