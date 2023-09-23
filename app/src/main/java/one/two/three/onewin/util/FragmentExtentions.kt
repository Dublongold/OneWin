package one.two.three.onewin.util

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatButton
import androidx.fragment.app.Fragment


fun defaultInflate(id: Int, inflater: LayoutInflater, container: ViewGroup?): View? {
    return inflater.inflate(id, container, false)
}

fun Fragment.findAppCompatButton(id: Int): AppCompatButton? {
    return view?.findViewById(id)
}