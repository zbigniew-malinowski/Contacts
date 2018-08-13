package com.zmalinowski.contactslist.contacts.ui.details.view

import android.view.ViewGroup
import com.zmalinowski.contactslist.R
import com.zmalinowski.contactslist.contacts.ui.details.data.DetailsModel
import com.zmalinowski.contactslist.databinding.ViewDetailsBinding
import com.zmalinowski.contactslist.utils.inflateDataBinding
import com.zmalinowski.contactslist.widget.screenview.ScreenHolder

class ContactDetailsScreenHolder(
        parent: ViewGroup,
        private val binding: ViewDetailsBinding = parent.inflateDataBinding(R.layout.view_details)
) : ScreenHolder<DetailsModel> by ScreenHolder.Static(binding.root) {

    override fun bind(model: DetailsModel) {
        with(model as DetailsModel.Details) {
            binding.item = model
        }
    }
}
