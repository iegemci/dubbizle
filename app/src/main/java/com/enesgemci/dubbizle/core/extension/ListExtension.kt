package com.enesgemci.dubbizle.core.extension

inline val <T> List<T>?.value: ArrayList<T>
    get() = this?.let { ArrayList(this) } ?: ArrayList()

fun <T> List<T>?.isNullOrEmpty(): Boolean {
    return this?.isEmpty() ?: true
}