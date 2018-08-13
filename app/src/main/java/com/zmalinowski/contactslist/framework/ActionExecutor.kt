package com.zmalinowski.contactslist.framework

import io.reactivex.Observable

/**
 * Reacts on [Action]s emitting their [Effect] (which may be immediate or asynchronous).
 * Allows to completely define all the logic of async state changes.
 */
interface ActionExecutor<Action, State> {

    /**
     *  Contract:
     *
     *  The returned [Observable] should be finite, cannot emit any error, must emit on
     *  the main thread.
     */
    fun execute(currentState: State, action: Action): Observable<Effect<State>>
}

/**
 * Describes a change that applied to a [State] object produces a new state.
 * It should be a pure function (= without side effects).
 */
typealias Effect<State> = (State) -> State