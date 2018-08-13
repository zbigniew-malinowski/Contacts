package com.zmalinowski.contactslist.contacts.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.zmalinowski.contactslist.R
import com.zmalinowski.contactslist.utils.PermissionHelper
import com.zmalinowski.contactslist.utils.observe
import com.zmalinowski.contactslist.widget.screenview.ScreenAdapter
import com.zmalinowski.contactslist.widget.screenview.ScreenView
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

/**
 * A fragment corresponds to a screen in the app. It's a composition root and it's responsibility is wiring
 * the screen's UI and the underlying business logic together - it shouldn't contain any logic on it's
 * own.
 */
abstract class ContactsBaseFragment<T : Any> : Fragment() {

    abstract val viewModel: ContactsViewModel<T>
    abstract val singleItemAdapter: ScreenAdapter<T>
    protected val permissionHelper: PermissionHelper by inject { parametersOf(this, viewModel::requestData) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_view_host, container, false)

        with(view as ScreenView) {
            adapter = singleItemAdapter
        }
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        observe(viewModel.data) {
            singleItemAdapter.item = it
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        permissionHelper.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        permissionHelper.onActivityResult(requestCode, resultCode, data)
    }
}