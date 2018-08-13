package com.zmalinowski.contactslist.app

import android.Manifest
import android.app.Application
import androidx.fragment.app.Fragment
import com.github.ajalt.timberkt.Timber
import com.zmalinowski.contactslist.BuildConfig
import com.zmalinowski.contactslist.contacts.core.ContactData
import com.zmalinowski.contactslist.contacts.core.ContactsActionExecutor
import com.zmalinowski.contactslist.contacts.core.ContactsDataSource
import com.zmalinowski.contactslist.contacts.core.State
import com.zmalinowski.contactslist.contacts.data.ContactTransformer
import com.zmalinowski.contactslist.contacts.data.ContactsDataSourceImpl
import com.zmalinowski.contactslist.contacts.data.DataProvider
import com.zmalinowski.contactslist.contacts.data.validation.DataAccessValidator
import com.zmalinowski.contactslist.contacts.data.validation.ValidatingContactsDataSource
import com.zmalinowski.contactslist.contacts.ui.ContactsViewModel
import com.zmalinowski.contactslist.contacts.ui.details.data.DetailsModelTransformer
import com.zmalinowski.contactslist.contacts.ui.list.data.ListModelTransformer
import com.zmalinowski.contactslist.framework.Store
import com.zmalinowski.contactslist.framework.Transformer
import com.zmalinowski.contactslist.utils.PermissionHelper
import ir.mirrajabi.rxcontacts.Contact
import ir.mirrajabi.rxcontacts.RxContacts
import org.koin.android.ext.android.startKoin
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.ext.koin.viewModel
import org.koin.core.parameter.parametersOf
import org.koin.dsl.module.module

class ContactsApp : Application() {

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) Timber.plant(Timber.DebugTree())
        startKoin(this, listOf(contactsModule))
    }
}

private val contactsModule = module {
    viewModel("list") { ContactsViewModel(get(), get<ListModelTransformer>()) }
    viewModel("details") { (contactId: String) ->
        ContactsViewModel(
                get(),
                get<DetailsModelTransformer>(parameters = { parametersOf(contactId) }))
    }
    single { ListModelTransformer }
    factory { (contactId: String) -> DetailsModelTransformer(contactId) }
    single { Store.create(State.Initial, get<ContactsActionExecutor>()) }
    single { ContactsActionExecutor(get()) }
    single {
        ValidatingContactsDataSource(
                wrapped = ContactsDataSourceImpl<Contact>(get(), get()),
                validator = get()
        ) as ContactsDataSource
    }
    single { ContactTransformer as Transformer<Contact, ContactData> }
    single {
        DataAccessValidator.PermissionValidator(
                context = androidContext(),
                permission = Manifest.permission.READ_CONTACTS
        ) as DataAccessValidator
    }
    single {
        { RxContacts.fetch(androidContext()) } as DataProvider<Contact>
    }

    factory { (fragment: Fragment, onPermissionGranted: () -> Unit) ->
        PermissionHelper(
                fragment = fragment,
                permissionList = *arrayOf(Manifest.permission.READ_CONTACTS),
                onPermissionsGranted = onPermissionGranted
        )
    }
}