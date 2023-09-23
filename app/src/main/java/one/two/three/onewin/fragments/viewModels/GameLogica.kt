package one.two.three.onewin.fragments.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class GameLogica: ViewModel() {
    val seconds = MutableLiveData(0)
    val minutes = MutableLiveData(2)
    val goal = MutableLiveData(0)
    var paused = false
    private var timerON = false
    lateinit var values: List<Int>
    var maxGoal: Int = -1

    fun reset() {
        minutes.value = 2
        seconds.value = 0
        goal.value = 0
        paused = false
        startTimer()
    }

    private fun startTimer() {
        if(!timerON) {
            timerON = true
            CoroutineScope(Dispatchers.Main).launch {
                val check = { seconds.value != 0 || minutes.value != 0 }
                while (check()) {
                    delay(250)
                    while (paused && check()) {
                        delay(50)
                    }
                    delay(250)
                    while (paused && check()) {
                        delay(50)
                    }
                    delay(250)
                    while (paused && check()) {
                        delay(50)
                    }
                    delay(250)
                    while (paused && check()) {
                        delay(50)
                    }
                    if (seconds.value == 0) {
                        if (minutes.value != 0) {
                            minutes.value?.let {
                                minutes.value = it - 1
                            }
                            seconds.value = 59
                        }
                    } else {
                        seconds.value?.let {
                            seconds.value = it - 1
                        }
                    }
                }
                timerON = false
            }
        }
    }
}