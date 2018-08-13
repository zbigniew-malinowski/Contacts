package com.zmalinowski.contactslist.ui.details.view

import android.view.ViewGroup
import com.zmalinowski.contactslist.R
import com.zmalinowski.contactslist.databinding.ViewDetailsBinding
import com.zmalinowski.contactslist.ui.details.DetailsModel
import com.zmalinowski.contactslist.utils.inflateDataBinding
import com.zmalinowski.contactslist.widget.singleitemview.ViewHolder

class ContactDetailsViewHolder(
        parent: ViewGroup,
        private val binding: ViewDetailsBinding = parent.inflateDataBinding(R.layout.view_details)
) : ViewHolder<DetailsModel> by ViewHolder.Static(binding.root) {

    override fun bind(model: DetailsModel) {
        with(model as DetailsModel.Details) {
            binding.item = model
        }
    }
}
