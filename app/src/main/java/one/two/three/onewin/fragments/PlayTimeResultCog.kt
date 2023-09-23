package one.two.three.onewin.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.fragment.app.Fragment
import one.two.three.onewin.R
import one.two.three.onewin.di.MainDI
import one.two.three.onewin.nav.MainNaviga
import one.two.three.onewin.util.defaultInflate

class PlayTimeResultCog(
    private val di: MainDI,
    private val level: Int,
    private val time: Int,
    private val goal: Int): Fragment() {

    private val maxGoal = 100 + level * 50
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.i("Game result", "Level: $level, Time: $time, Goal: $goal")
        return defaultInflate(if(time == 0 && maxGoal > goal || level == 18)
            R.layout.game_failed_result_cog
        else
            R.layout.game_result_cog,
            inflater,
            container)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<TextView>(R.id.game_result_title).text = when {
            goal == maxGoal -> getString(R.string.you_win)
            time == 0 -> getString(R.string.try_again)
            else -> getString(R.string.game_paused)
        }
        val navigation = (di.getInstance(MainNaviga::class.java) as MainNaviga)
        view.findViewById<ImageButton>(R.id.home_button).setOnClickListener {
            navigation.popTo(SelectGameCog::class.java)
        }
        view.findViewById<ImageButton>(R.id.replay_button).setOnClickListener {
            navigation.pop()
        }
        view.findViewById<ImageButton?>(R.id.next_button)?.setOnClickListener {
            if(goal == maxGoal) {
                navigation.pop()
                navigation.goToGame(level + 1)
            }
            else {
                navigation.pop()
            }
        }
        view.findViewById<TextView>(R.id.timer_text).text = getString(R.string.timer_text, time / 60, time % 60)
        view.findViewById<TextView>(R.id.goal_text).text = getString(R.string.goal_text, goal, maxGoal)
    }
}