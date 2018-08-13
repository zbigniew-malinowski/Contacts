package com.zmalinowski.contactslist.framework

/**
 * Represents mapper from [T] to [R]. Allows to return null, which may allow filtering
 * with [mapNotNull] or similar operators.
 *
 * Adds additional context, which makes constructor parameters more readable and also
 * encourages to encapsulate mapping logic into a reusable class.
 */
typealias Transformer<T, R> = (T) -> R?