package com.chossoodw1.sporttowin.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.allViews
import androidx.fragment.app.Fragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import com.chossoodw1.sporttowin.R
import com.chossoodw1.sporttowin.di.MainDI
import com.chossoodw1.sporttowin.fragments.viewModels.GameLogica
import com.chossoodw1.sporttowin.nav.MainNaviga
import com.chossoodw1.sporttowin.predefinded.gameValues
import com.chossoodw1.sporttowin.util.CompletedLevels
import com.chossoodw1.sporttowin.util.defaultInflate

class PlayTimeCog(
    private val di: MainDI,
    private val level: Int
): Fragment(), DoWhenPop {

    private val viewModel = GameLogica()
    private lateinit var elements: List<ImageButton>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return defaultInflate(R.layout.play_time_cog, inflater, container)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<TextView>(R.id.game_title).text = getString(R.string.level_title, level)
        view.findViewById<ImageButton>(R.id.pause_button).setOnClickListener {
            goToResult()

        }
        viewModel.values = gameValues.shuffled()
        viewModel.maxGoal = level * 50 + 100

        elements = view.allViews.filter {
            it.id != R.id.pause_button && it.id != R.id.replay_button && it is ImageButton
        }.map { it as ImageButton }.toList()
        elements.forEachIndexed() { ind, element ->
            element.setImageResource(R.drawable.elem_01 + viewModel.values[ind])
            element.setOnClickListener {
                if(element.tag == null) {
                    element.foreground =
                        ResourcesCompat.getDrawable(
                            resources,
                            R.drawable.selected_element,
                            null
                        )
                    element.tag = "selected"
                    if(elements.count { it.tag == "selected" } == 2) {
                        val indices = elements.mapIndexed { indexToo, imageButton ->
                            indexToo to imageButton
                        }.filter { it.second.tag == "selected" }.map { it.first }
                        if(viewModel.values[indices[0]] == viewModel.values[indices[1]]) {
                            elements[indices[0]].foreground = null
                            elements[indices[0]].isEnabled = true
                            elements[indices[0]].tag = "found"
                            elements[indices[1]].foreground = null
                            elements[indices[1]].isEnabled = true
                            elements[indices[1]].tag = "found"
                            viewModel.goal.value?.let {
                                viewModel.goal.value = it + 50
                            }
                            if(elements.all { it.tag == "found" }) {
                                CoroutineScope(Dispatchers.Main).launch {
                                    delay(1000)
                                    viewModel.values = gameValues.shuffled()
                                    elements.forEachIndexed() {i, elem ->
                                        elem.foreground = ResourcesCompat.getDrawable(resources,
                                            R.drawable.elem_unknown, null)
                                        elem.isEnabled = true
                                        elem.tag = null
                                        elem.setImageResource(R.drawable.elem_01 + viewModel.values[i])
                                    }
                                }
                            }
                        }
                        else {
                            elements[indices[0]].foreground = ResourcesCompat.getDrawable(resources,
                                R.drawable.invalid_element, null)
                            elements[indices[0]].isEnabled = false
                            elements[indices[0]].tag = null
                            elements[indices[1]].foreground = ResourcesCompat.getDrawable(resources,
                                R.drawable.invalid_element, null)
                            elements[indices[1]].isEnabled = false
                            elements[indices[1]].tag = null
                            CoroutineScope(Dispatchers.Main).launch {
                                delay(1000)
                                if(!isDetached) {
                                    elements[indices[0]].foreground = ResourcesCompat
                                        .getDrawable(resources, R.drawable.elem_unknown, null)
                                    elements[indices[0]].isEnabled = true
                                    elements[indices[1]].foreground = ResourcesCompat
                                        .getDrawable(resources, R.drawable.elem_unknown, null)
                                    elements[indices[1]].isEnabled = true
                                }
                            }
                        }
                    }
                }
            }
        }
        viewModel.seconds.observe(viewLifecycleOwner) {
            view.findViewById<TextView>(R.id.timer_text).text = getString(R.string.timer_text,
                viewModel.minutes.value ?: 0, viewModel.seconds.value ?: 0)
            if(it <= 0 && (viewModel.minutes.value ?: 0) <= 0) {
                goToResult()
            }
        }
        viewModel.goal.observe(viewLifecycleOwner) {
            view.findViewById<TextView>(R.id.goal_text).text = getString(R.string.goal_text,
                viewModel.goal.value ?: 0, viewModel.maxGoal)
            if(it >= viewModel.maxGoal) {
                goToResult()
            }
        }
        view.findViewById<ImageButton>(R.id.replay_button).setOnClickListener {
            startGame()
        }
        startGame()
    }

    private fun goToResult() {
        val completedLevels = (di.getInstance(CompletedLevels::class.java) as CompletedLevels)
        if(level - 1 == completedLevels.get() && level != 30 && viewModel.goal.value == viewModel.maxGoal) {
            completedLevels.set(level)
        }
        viewModel.paused = true
        (di.getInstance(MainNaviga::class.java) as MainNaviga).goToGameResult(level,
            (viewModel.seconds.value ?: 0) + (viewModel.minutes.value ?: 0) * 60,
            viewModel.goal.value ?: 0)
    }

    private fun startGame() {
        for(element in elements) {
            element.tag = null
            element.foreground = ResourcesCompat.getDrawable(
                resources, R.drawable.elem_unknown, null
            )
            element.isEnabled = true
        }
        viewModel.reset()
    }

    override fun doWhenPop() {
        if(viewModel.seconds.value == 0 && viewModel.minutes.value == 0
            || viewModel.goal.value == viewModel.maxGoal) {
            startGame()
            viewModel.reset()
        }
        else {
            viewModel.paused = false
        }
    }
}