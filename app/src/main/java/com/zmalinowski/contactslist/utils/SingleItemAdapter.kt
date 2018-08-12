package com.zmalinowski.contactslist.utils

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlin.properties.Delegates

class SingleItemAdapter<T>(private val viewFactory: (ViewGroup, T) -> ViewHolder<T>) : RecyclerView.Adapter<SingleItemAdapter.ViewHolder<T>>() {

    var item: T? by Delegates.observable<T?>(null) { _, old, new ->
        when {
            old == new -> Unit
            old == null -> notifyItemInserted(0)
            new == null -> notifyItemRemoved(0)
            else -> notifyItemChanged(0)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder<T> =
            viewFactory.invoke(parent, item!!)

    override fun getItemCount() = if (item != null) 1 else 0

    override fun onBindViewHolder(holder: ViewHolder<T>, position: Int) {
        holder.bind(item!!)
    }

    open class ViewHolder<in T>(view: View) : RecyclerView.ViewHolder(view) {

        constructor(parent: ViewGroup, layoutId: Int) :this(parent.inflate(layoutId))

        open fun bind(model: T) {
//            NOOP
        }
    }

}

