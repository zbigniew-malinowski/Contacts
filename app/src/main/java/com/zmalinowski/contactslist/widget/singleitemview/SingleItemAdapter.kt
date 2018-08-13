package com.zmalinowski.contactslist.widget.singleitemview

import android.view.View
import android.view.ViewGroup
import com.zmalinowski.contactslist.utils.inflate
import kotlin.properties.Delegates

class SingleItemAdapter<T : Any>(private val viewFactory: (ViewGroup, T) -> ViewHolder<T>) {

    var item: T? by Delegates.observable<T?>(null) { _, old, new ->
        if (old != new)
            observer?.onChanged()
    }
    var observer: Observer? = null

    fun createView(parent: ViewGroup): View {
        requireNotNull(item)
        val viewHolder = viewFactory.invoke(parent, item!!)
        viewHolder.bind(item!!)
        return viewHolder.itemView
    }

    fun isEmpty(): Boolean = item == null
}

interface ViewHolder<in T> {

    val itemView: View

    fun bind(model: T)

    class Static<in T>(override val itemView: View) : ViewHolder<T>{

        constructor(parent : ViewGroup, layoutId : Int):this(parent.inflate(layoutId))

        override fun bind(model: T) {
//            NOOP
        }

    }
}