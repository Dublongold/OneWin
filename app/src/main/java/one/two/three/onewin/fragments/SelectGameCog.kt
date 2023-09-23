package one.two.three.onewin.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import one.two.three.onewin.R
import one.two.three.onewin.di.MainDI
import one.two.three.onewin.nav.MainNaviga
import one.two.three.onewin.util.CompletedLevels
import one.two.three.onewin.util.defaultInflate
import one.two.three.onewin.util.findAppCompatButton

class SelectGameCog(private val di: MainDI): Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return defaultInflate(R.layout.select_level_cog, inflater, container)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<ImageButton>(R.id.back_button).setOnClickListener {
            (di.getInstance(MainNaviga::class.java) as MainNaviga).pop()
        }
        val completed = (di.getInstance(CompletedLevels::class.java) as CompletedLevels).get()
        Log.i("Completed", "$completed")

        findAppCompatButton(R.id.start_level_1_button)?.apply {
            setOnClickListener {
                (di.getInstance(MainNaviga::class.java) as MainNaviga).goToGame(1)
            }
        }

        findAppCompatButton(R.id.start_level_2_button)?.apply {
            setOnClickListener {
                (di.getInstance(MainNaviga::class.java) as MainNaviga).goToGame(2)
            }
            isEnabled = 1 <= completed
        }

        findAppCompatButton(R.id.start_level_3_button)?.apply {
            setOnClickListener {
                (di.getInstance(MainNaviga::class.java) as MainNaviga).goToGame(3)
            }
            isEnabled = 2 <= completed
        }

        findAppCompatButton(R.id.start_level_4_button)?.apply {
            setOnClickListener {
                (di.getInstance(MainNaviga::class.java) as MainNaviga).goToGame(4)
            }
            isEnabled = 3 <= completed
        }

        findAppCompatButton(R.id.start_level_5_button)?.apply {
            setOnClickListener {
                (di.getInstance(MainNaviga::class.java) as MainNaviga).goToGame(5)
            }
            isEnabled = 4 <= completed
        }

        findAppCompatButton(R.id.start_level_6_button)?.apply {
            setOnClickListener {
                (di.getInstance(MainNaviga::class.java) as MainNaviga).goToGame(6)
            }
            isEnabled = 5 <= completed
        }

        findAppCompatButton(R.id.start_level_7_button)?.apply {
            setOnClickListener {
                (di.getInstance(MainNaviga::class.java) as MainNaviga).goToGame(7)
            }
            isEnabled = 6 <= completed
        }

        findAppCompatButton(R.id.start_level_8_button)?.apply {
            setOnClickListener {
                (di.getInstance(MainNaviga::class.java) as MainNaviga).goToGame(8)
            }
            isEnabled = 7 <= completed
        }

        findAppCompatButton(R.id.start_level_9_button)?.apply {
            setOnClickListener {
                (di.getInstance(MainNaviga::class.java) as MainNaviga).goToGame(9)
            }
            isEnabled = 8 <= completed
        }

        findAppCompatButton(R.id.start_level_10_button)?.apply {
            setOnClickListener {
                (di.getInstance(MainNaviga::class.java) as MainNaviga).goToGame(10)
            }
            isEnabled = 9 <= completed
        }

        findAppCompatButton(R.id.start_level_11_button)?.apply {
            setOnClickListener {
                (di.getInstance(MainNaviga::class.java) as MainNaviga).goToGame(11)
            }
            isEnabled = 10 <= completed
        }

        findAppCompatButton(R.id.start_level_12_button)?.apply {
            setOnClickListener {
                (di.getInstance(MainNaviga::class.java) as MainNaviga).goToGame(12)
            }
            isEnabled = 11 <= completed
        }

        findAppCompatButton(R.id.start_level_13_button)?.apply {
            setOnClickListener {
                (di.getInstance(MainNaviga::class.java) as MainNaviga).goToGame(13)
            }
            isEnabled = 12 <= completed
        }

        findAppCompatButton(R.id.start_level_14_button)?.apply {
            setOnClickListener {
                (di.getInstance(MainNaviga::class.java) as MainNaviga).goToGame(14)
            }
            isEnabled = 13 <= completed
        }

        findAppCompatButton(R.id.start_level_15_button)?.apply {
            setOnClickListener {
                (di.getInstance(MainNaviga::class.java) as MainNaviga).goToGame(15)
            }
            isEnabled = 14 <= completed
        }

        findAppCompatButton(R.id.start_level_15_button)?.apply {
            setOnClickListener {
                (di.getInstance(MainNaviga::class.java) as MainNaviga).goToGame(15)
            }
            isEnabled = 14 <= completed
        }

        findAppCompatButton(R.id.start_level_16_button)?.apply {
            setOnClickListener {
                (di.getInstance(MainNaviga::class.java) as MainNaviga).goToGame(16)
            }
            isEnabled = 15 <= completed
        }

        findAppCompatButton(R.id.start_level_17_button)?.apply {
            setOnClickListener {
                (di.getInstance(MainNaviga::class.java) as MainNaviga).goToGame(17)
            }
            isEnabled = 16 <= completed
        }

        findAppCompatButton(R.id.start_level_18_button)?.apply {
            setOnClickListener {
                (di.getInstance(MainNaviga::class.java) as MainNaviga).goToGame(18)
            }
            isEnabled = 17 <= completed
        }
    }
}