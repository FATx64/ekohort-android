package com.example.ekohort_android.utils.delegates

import kotlin.reflect.KProperty

fun <T> holdValue(): ValueHolder<T> = ValueHolder()

open class ValueHolder<T> {
    private var value: T? = null
    var hasValue: Boolean = false
        private set

    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        this.value = value
        hasValue = true
    }

    operator fun getValue(thisRef: Any?, property: KProperty<*>): T {
        return this.value!!
    }
}