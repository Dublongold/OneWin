package com.chossoodw1.sporttowin.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import androidx.activity.OnBackPressedCallback
import com.chossoodw1.sporttowin.R
import com.chossoodw1.sporttowin.di.DIType
import com.chossoodw1.sporttowin.di.MainDI
import com.chossoodw1.sporttowin.fragments.DoWhenPop
import com.chossoodw1.sporttowin.nav.MainNaviga
import com.chossoodw1.sporttowin.util.CompletedLevels

class MainPlace : AppCompatActivity() {
    private val di = MainDI()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_place)
        window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        di.addDependency(MainNaviga::class.java, DIType.SINGLE, supportFragmentManager)
        di.addDependency(CompletedLevels::class.java, DIType.SINGLE, ::getSharedPreferences)
        (di.getInstance(MainNaviga::class.java) as MainNaviga).apply {
            onNoCogs = ::finish
            goToLoading()
        }
        supportFragmentManager.addOnBackStackChangedListener {
            val lastFragment = supportFragmentManager.fragments.last()
            if(lastFragment is DoWhenPop) {
                lastFragment.doWhenPop()
            }
        }
        onBackPressedDispatcher.addCallback(object: OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                (di.getInstance(MainNaviga::class.java) as MainNaviga).pop()
            }
        })
    }
}