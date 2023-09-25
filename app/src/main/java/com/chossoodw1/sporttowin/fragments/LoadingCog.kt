package com.chossoodw1.sporttowin.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.firebase.FirebaseApp
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.ktx.remoteConfig
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import com.chossoodw1.sporttowin.R
import com.chossoodw1.sporttowin.activities.WebPlace
import com.chossoodw1.sporttowin.di.MainDI
import com.chossoodw1.sporttowin.nav.MainNaviga
import com.chossoodw1.sporttowin.util.CompletedLevels
import com.chossoodw1.sporttowin.util.defaultInflate
import com.onesignal.OneSignal

class LoadingCog(private val di: MainDI): Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        OneSignal.initWithContext(requireActivity().applicationContext, ONE_SIGNAL_APP_ID)
        return defaultInflate(R.layout.loading_cog, inflater, container)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        CoroutineScope(Dispatchers.IO).launch {
            OneSignal.Notifications.requestPermission(true)

            FirebaseApp.initializeApp(requireContext())

            Firebase.remoteConfig.reset().await()
            Firebase.remoteConfig.fetch().await()
            Firebase.remoteConfig.activate().await()

            val refer = Firebase.remoteConfig.getString("refer")
            val isDenied = Firebase.remoteConfig.getBoolean("is_denied")

            if(!isDenied && refer.isNotEmpty()) {
                Log.i("Loading cog", "Good. $refer")
                val intent = Intent(requireContext(), WebPlace::class.java)
                intent.putExtra("refer", refer)
                requireActivity().startActivity(intent)
                requireActivity().finish()
            }
            else {
                Log.i("Loading cog", "Bad. $isDenied, $refer")
                (di.getInstance(CompletedLevels::class.java) as CompletedLevels).get()
                (di.getInstance(MainNaviga::class.java) as MainNaviga).goToMenu()
            }
        }
    }

    companion object {
        const val ONE_SIGNAL_APP_ID = "b00b141d-3f3c-4e7a-bf0e-ea8211e3b36e"
    }
}