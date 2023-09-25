package com.chossoodw1.sporttowin.util

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import com.chossoodw1.sporttowin.predefinded.NUMBER_TO_WORD
import com.chossoodw1.sporttowin.predefinded.WORD_TO_NUMBER

class CompletedLevels(private val getSharedPreferences: (String, Int) -> SharedPreferences) {
    private var needLoad = true
    private var loadedValue = 0
    fun get(): Int {
        val result = if(needLoad) {
            loadedValue = WORD_TO_NUMBER[getSharedPreferences("levels", AppCompatActivity.MODE_PRIVATE)
                .getString("level", "zero")] ?: 0
            loadedValue
        }
        else loadedValue
        return result
    }

    fun set(value: Int) {
        getSharedPreferences("levels", AppCompatActivity.MODE_PRIVATE)
            .edit().putString("level", NUMBER_TO_WORD[value]).apply()
    }
}