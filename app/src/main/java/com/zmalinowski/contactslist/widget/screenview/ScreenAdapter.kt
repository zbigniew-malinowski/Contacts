package com.zmalinowski.contactslist.widget.screenview

import android.view.View
import android.view.ViewGroup
import com.zmalinowski.contactslist.utils.inflate
import kotlin.properties.Delegates

/**
 * An adapter used by [ScreenView], allowing to switch screen contents between different
 * layouts. The [screenConfig] allows to bind particular data model classes with corresponding layouts.
 */
class ScreenAdapter<T : Any>(private val screenConfig: (ViewGroup, T) -> ScreenHolder<T>) {

    var item: T? by Delegates.observable<T?>(null) { _, old, new ->
        if (old != new)
            changeObserver?.onChanged()
    }
    var changeObserver: ChangeObserver? = null

    fun createView(parent: ViewGroup): View {
        requireNotNull(item)
        val viewHolder = screenConfig.invoke(parent, item!!)
        viewHolder.bind(item!!)
        return viewHolder.itemView
    }

    fun isEmpty(): Boolean = item == null
}

/**
 * Holds a view representing particular screen layout, and is able to populate it with corresponding
 * data model.
 */
interface ScreenHolder<in T> {

    val itemView: View

    fun bind(model: T)

    /**
     * Basic implementation, holding only a static view (which doesn't require any external data).
     */
    class Static<in T>(override val itemView: View) : ScreenHolder<T>{

        constructor(parent : ViewGroup, layoutId : Int):this(parent.inflate(layoutId))

        override fun bind(model: T) {
//            NOOP
        }

    }
}