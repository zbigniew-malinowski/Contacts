package com.zmalinowski.contactslist.widget.singleitemview

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import kotlin.properties.Delegates

class SingleItemView @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr), Observer {

    var adapter: SingleItemAdapter<out Any>? by Delegates.observable<SingleItemAdapter<out Any>?>(null) { _, _, newValue ->
        newValue?.observer = this
        onChanged()
    }

    override fun onChanged() {
        removeAllViews()
        adapter?.let {
            if (!it.isEmpty()) {
                addView(it.createView(this@SingleItemView))
            }
        }
    }
}

interface Observer {

    fun onChanged()
}

