package one.two.three.onewin.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import one.two.three.onewin.R
import one.two.three.onewin.di.MainDI
import one.two.three.onewin.nav.MainNaviga
import one.two.three.onewin.util.defaultInflate
import one.two.three.onewin.util.findAppCompatButton

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