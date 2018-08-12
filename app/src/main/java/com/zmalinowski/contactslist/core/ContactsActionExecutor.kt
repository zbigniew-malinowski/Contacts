package com.zmalinowski.contactslist.core

import com.github.ajalt.timberkt.e
import com.zmalinowski.contactslist.framework.ActionExecutor
import com.zmalinowski.contactslist.framework.Effect
import io.reactivex.Observable

class ContactsActionExecutor(private val dataSource: ContactsDataSource) : ActionExecutor<Action, State> {

    override fun execute(currentState: State, action: Action): Observable<Effect<State>> = when (action) {
        Action.Load -> if (currentState.shouldLoad()) load() else doNothing()
    }

    private fun load(): Observable<Effect<State>> =
            dataSource.getAllContacts()
                    .toObservable()
                    .map { onDataLoaded(it) }
                    .startWith(onLoadingStarted())
                    .doOnError { e(it) { "error loading data" } }
                    .onErrorReturn { error -> onLoadingFailed(error) }

    private fun State.shouldLoad() = this is State.Initial || this is State.Error

    private fun doNothing(): Observable<Effect<State>> = Observable.empty()

    private fun onLoadingFailed(cause: Throwable): Effect<State> = { State.Error(cause) }

    private fun onDataLoaded(data: List<ContactData>): Effect<State> = { State.Loaded(data) }

    private fun onLoadingStarted(): Effect<State> = { State.Loading }

}