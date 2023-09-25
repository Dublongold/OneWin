package com.chossoodw1.sporttowin.di

import android.content.SharedPreferences
import androidx.fragment.app.FragmentManager
import com.chossoodw1.sporttowin.nav.MainNaviga
import com.chossoodw1.sporttowin.util.CompletedLevels

class MainDI {
    private val instances = mutableMapOf<Class<*>, Any?>()

    fun getInstance(clazz: Class<*>, argument: Any? = null): Any? {
        return if(instances.keys.contains(clazz)) {
            instances[clazz]
        }
        else {
            when(clazz) {
                MainNaviga::class.java -> MainNaviga(argument as FragmentManager, this)
                else -> null
            }
        }
    }

    fun<T> addDependency(clazz: Class<T>, type: DIType, argument: Any? = null) {
        if(type == DIType.SINGLE) {
            instances[clazz] = when(clazz) {
                MainNaviga::class.java -> MainNaviga(argument as FragmentManager, this)
                CompletedLevels::class.java -> CompletedLevels(argument as (String, Int) -> SharedPreferences)
                else -> null
            }
        }
    }
}

enum class DIType {
    SINGLE,
    DEFAULT
}