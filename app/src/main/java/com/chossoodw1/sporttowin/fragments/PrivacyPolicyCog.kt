package com.chossoodw1.sporttowin.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import com.chossoodw1.sporttowin.R
import com.chossoodw1.sporttowin.di.MainDI
import com.chossoodw1.sporttowin.nav.MainNaviga
import com.chossoodw1.sporttowin.util.defaultInflate

class PrivacyPolicyCog(private val di: MainDI): Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return defaultInflate(R.layout.privacy_policy_cog, inflater, container)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<ImageButton>(R.id.back_button).setOnClickListener {
            (di.getInstance(MainNaviga::class.java) as MainNaviga).pop()
        }
    }
}