package one.two.three.onewin.util

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import one.two.three.onewin.predefinded.NUMBER_TO_WORD
import one.two.three.onewin.predefinded.WORD_TO_NUMBER

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