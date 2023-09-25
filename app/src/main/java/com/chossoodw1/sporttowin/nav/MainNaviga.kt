package com.chossoodw1.sporttowin.nav

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.chossoodw1.sporttowin.R
import com.chossoodw1.sporttowin.di.MainDI
import com.chossoodw1.sporttowin.fragments.PlayTimeCog
import com.chossoodw1.sporttowin.fragments.PlayTimeResultCog
import com.chossoodw1.sporttowin.fragments.HowToPlayCog
import com.chossoodw1.sporttowin.fragments.LoadingCog
import com.chossoodw1.sporttowin.fragments.MenuCog
import com.chossoodw1.sporttowin.fragments.PrivacyPolicyCog
import com.chossoodw1.sporttowin.fragments.SelectGameCog

class MainNaviga(private val supportCogsManager: FragmentManager, private val di: MainDI) {

    private val cogs = mutableListOf<Fragment>()

    var onNoCogs: (() -> Unit)? = null

    fun goToLoading() {
        val cog = LoadingCog(di)
        goToCog(cog)
    }

    fun goToMenu() {
        val cog = MenuCog(di)
        goToCog(cog)
    }

    fun goToPrivacyPolicy() {
        val cog = PrivacyPolicyCog(di)
        goToCog(cog)
    }

    fun goToHowToPlay() {
        val cog = HowToPlayCog(di)
        goToCog(cog)
    }

    fun goToSelectGame() {
        val cog = SelectGameCog(di)
        goToCog(cog)
    }

    fun goToGame(level: Int) {
        val cog = PlayTimeCog(di, level)
        goToCog(cog)
    }

    fun goToGameResult(level: Int, time: Int, goal: Int) {
        val fragment = PlayTimeResultCog(di, level, time, goal)
        goToCog(fragment, true)
    }

    private fun<T> goToCog(fragment: T, isAdd: Boolean = false) where T: Fragment {
        val transact = supportCogsManager.beginTransaction()

        if(supportCogsManager.fragments.isEmpty()) {
            transact.add(R.id.fragment_container, fragment)
        }
        else {
            if(!isAdd) {
                transact.replace(R.id.fragment_container, fragment)
                cogs.add(fragment)
            }
            else {
                transact.addToBackStack(null)
                transact.add(R.id.fragment_container, fragment)
            }
        }

        transact.commitAllowingStateLoss()
    }

    private fun<T> loadCog(fragment: T) where T: Fragment {
        val transact = supportCogsManager.beginTransaction()
        transact.replace(R.id.fragment_container, fragment)
        transact.commitAllowingStateLoss()
    }

    fun pop() {
        if(supportCogsManager.fragments.size == 1) {
            cogs.removeLastOrNull()
        }
        if(cogs.isNotEmpty()) {
            if(supportCogsManager.fragments.size > 1) {
                supportCogsManager.popBackStackImmediate()
            }
            else {
                val transact = supportCogsManager.beginTransaction()
                transact.replace(R.id.fragment_container, cogs.last())
                transact.commitAllowingStateLoss()
            }
        }
        else {
            onNoCogs?.invoke()
        }
    }

    fun<T> popTo(clazz: Class<T>) where T: Fragment {
        if(cogs.any {it::class.java == clazz}) {
            while(cogs.last()::class.java != clazz && cogs.size > 1) {
                cogs.removeLast()
            }
            while(supportCogsManager.fragments.size > 1 &&
                supportCogsManager.fragments.last()::class.java != clazz) {
                supportCogsManager.popBackStackImmediate()
            }
            loadCog(cogs.last())
        }
    }
}