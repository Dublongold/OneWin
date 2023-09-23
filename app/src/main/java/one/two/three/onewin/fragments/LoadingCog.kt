package one.two.three.onewin.fragments

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
import one.two.three.onewin.R
import one.two.three.onewin.activities.WebPlace
import one.two.three.onewin.di.MainDI
import one.two.three.onewin.nav.MainNaviga
import one.two.three.onewin.util.CompletedLevels
import one.two.three.onewin.util.defaultInflate

class LoadingCog(private val di: MainDI): Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return defaultInflate(R.layout.loading_cog, inflater, container)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        CoroutineScope(Dispatchers.IO).launch {
            /*
            OneSignal.initWithContext(requireActivity().applicationContext, ONE_SIGNAL_APP_ID)
            OneSignal.Notification.requestPermission(true)
            */
            FirebaseApp.initializeApp(requireContext())

            Firebase.remoteConfig.reset().await()
            Firebase.remoteConfig.fetch().await()
            Firebase.remoteConfig.activate().await()

            val url = Firebase.remoteConfig.getString("link")
            val allow = Firebase.remoteConfig.getBoolean("allow")

            if(allow && url.isNotEmpty()) {
                Log.i("Loading cog", "Good. $url")
                val intent = Intent(requireContext(), WebPlace::class.java)
                intent.putExtra("url", url)
                requireActivity().startActivity(intent)
                requireActivity().finish()
            }
            else {
                Log.i("Loading cog", "Bad. $allow, $url")
                (di.getInstance(CompletedLevels::class.java) as CompletedLevels).get()
                (di.getInstance(MainNaviga::class.java) as MainNaviga).goToMenu()
            }
        }
    }
}