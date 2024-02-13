package com.example.ekohort_android.utils.delegates

import kotlin.reflect.KProperty

class WriteOnce<T> {
    private var holder = holdValue<T>()
    private var value by holder

    operator fun getValue(thisRef: Any?, property: KProperty<*>): T {
        if (!holder.hasValue) {
            throw IllegalStateException("Property must be initialized before use")
        }
        return value
    }

    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        if (holder.hasValue) {
            throw RuntimeException("Write-once property already has a value")
        }
        this.value = value
    }

}