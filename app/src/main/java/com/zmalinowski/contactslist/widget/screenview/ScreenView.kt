package com.zmalinowski.contactslist.widget.screenview

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import kotlin.properties.Delegates

/**
 * A widget that allows to control screen contents using an adapter. It's similar to [ViewPager],
 * but there's only one "page" at the time, and API is closer to [RecyclerView].
 *
 * As an improvement, it might implement a view pool and recycling.
 */
class ScreenView @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr), ChangeObserver {

    var adapter: ScreenAdapter<out Any>? by Delegates.observable<ScreenAdapter<out Any>?>(null) { _, _, newValue ->
        newValue?.changeObserver = this
        onChanged()
    }

    override fun onChanged() {
        removeAllViews()
        adapter?.let {
            if (!it.isEmpty()) {
                addView(it.createView(this@ScreenView))
            }
        }
    }
}

interface ChangeObserver {

    fun onChanged()
}

