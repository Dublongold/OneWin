package one.two.three.onewin.util

import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity

fun AppCompatActivity.addOnBackPressedCallback(enabled: Boolean, callback: () -> Unit) {
    onBackPressedDispatcher.addCallback(object: OnBackPressedCallback(enabled) {
        override fun handleOnBackPressed() {
            callback()
        }
    })
}