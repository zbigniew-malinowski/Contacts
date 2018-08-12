package com.zmalinowski.contactslist.framework

import com.github.ajalt.timberkt.d
import io.reactivex.ObservableSource
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Consumer
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.subjects.BehaviorSubject


interface Store<Action, State> : Consumer<Action>, ObservableSource<State>, Disposable {

    private class DefaultStore<Action, State>(
            private val initialState: State,
            private val executor: ActionExecutor<Action, State>,
            private val subject: BehaviorSubject<State> = BehaviorSubject.createDefault(initialState),
            private val disposable: CompositeDisposable = CompositeDisposable()
    ) : Store<Action, State>, ObservableSource<State> by subject, Disposable by disposable {

        private val currentSate: State get() = subject.value!!

        override fun accept(action: Action) {
            d { "accepting action: $action" }
            disposable += executor.execute(currentSate, action)
                    .subscribe { applyEffect(it) }
        }

        private fun applyEffect(effect: Effect<State>) {
            d { "applying effect: $effect" }
            subject.onNext(effect.invoke(currentSate))
            d { "new state is: $currentSate" }
        }
    }

    companion object {

        fun <Action, State> create(
                initialState: State,
                executor: ActionExecutor<Action, State>
        ): Store<Action, State> =
                DefaultStore(initialState, executor)
    }
}

