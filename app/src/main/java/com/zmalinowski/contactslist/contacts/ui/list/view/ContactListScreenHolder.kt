package com.zmalinowski.contactslist.contacts.ui.list.view

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.zmalinowski.contactslist.R
import com.zmalinowski.contactslist.contacts.ui.list.data.ListItem
import com.zmalinowski.contactslist.contacts.ui.list.data.ListModel
import com.zmalinowski.contactslist.databinding.ViewListItemBinding
import com.zmalinowski.contactslist.utils.inflateDataBinding
import com.zmalinowski.contactslist.widget.screenview.ScreenHolder

class ContactListScreenHolder(parent: ViewGroup, onContactSelected : (String) -> Unit) : ScreenHolder<ListModel> by ScreenHolder.Static(parent, R.layout.view_list) {

    private val contactsAdapter = ContactsAdapter(onContactSelected)

    init {
        with(itemView as RecyclerView) {
            adapter = contactsAdapter
        }
    }

    override fun bind(model: ListModel) {
        with(model as ListModel.Contacts) {
            contactsAdapter.submitList(model.list)
        }
    }
}

class ContactsAdapter(private val onContactSelected: (String) -> Unit) : ListAdapter<ListItem, ListItemViewHolder>(DIFF_UTIL) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListItemViewHolder {
        return ListItemViewHolder(parent.inflateDataBinding(R.layout.view_list_item))
    }

    override fun onBindViewHolder(holder: ListItemViewHolder, position: Int) {
        val item = getItem(position)
        holder.binding.item = item
        holder.itemView.setOnClickListener { onContactSelected(item.contactId) }
    }

    companion object {
        val DIFF_UTIL = object : DiffUtil.ItemCallback<ListItem>() {
            override fun areItemsTheSame(oldItem: ListItem, newItem: ListItem): Boolean = oldItem.contactId == newItem.contactId

            override fun areContentsTheSame(oldItem: ListItem, newItem: ListItem): Boolean = oldItem == newItem

        }
    }
}

class ListItemViewHolder(val binding: ViewListItemBinding) : RecyclerView.ViewHolder(binding.root)