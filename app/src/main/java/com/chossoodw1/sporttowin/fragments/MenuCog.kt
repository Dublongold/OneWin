package com.chossoodw1.sporttowin.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.chossoodw1.sporttowin.R
import com.chossoodw1.sporttowin.di.MainDI
import com.chossoodw1.sporttowin.nav.MainNaviga
import com.chossoodw1.sporttowin.util.defaultInflate
import com.chossoodw1.sporttowin.util.findAppCompatButton

class MenuCog(private val di: MainDI): Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return defaultInflate(R.layout.menu_cog, inflater, container)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        findAppCompatButton(R.id.privacy_policy_button)?.setOnClickListener {
            (di.getInstance(MainNaviga::class.java) as MainNaviga).goToPrivacyPolicy()
        }
        findAppCompatButton(R.id.how_to_play_button)?.setOnClickListener {
            (di.getInstance(MainNaviga::class.java) as MainNaviga).goToHowToPlay()
        }
        findAppCompatButton(R.id.play_button)?.setOnClickListener {
            (di.getInstance(MainNaviga::class.java) as MainNaviga).goToSelectGame()
        }
    }
}