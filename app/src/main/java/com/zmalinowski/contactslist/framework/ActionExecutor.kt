package com.zmalinowski.contactslist.framework

import io.reactivex.Observable

interface ActionExecutor<Action, State> {

    fun execute(currentState: State, action: Action): Observable<Effect<State>>
}

