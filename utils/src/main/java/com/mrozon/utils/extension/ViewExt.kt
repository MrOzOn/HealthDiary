package com.mrozon.utils.extension

import android.view.View
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.DatePicker
import android.widget.EditText
import androidx.core.widget.doOnTextChanged
import com.mrozon.utils.R
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import java.util.*

@ExperimentalCoroutinesApi
fun EditText.offer(channel: ConflatedBroadcastChannel<String>) {
    doOnTextChanged { text, _, _, _ ->
        channel.offer(text.toString().trim())
    }
}

fun View.visible(show: Boolean, isGone: Boolean = false) {
    if(show) {
        visibility = View.VISIBLE
    }
    else {
        if (isGone)
            visibility = View.GONE
        else
            visibility = View.INVISIBLE
    }
}

fun DatePicker.getDate(): Date {
    val calendar = Calendar.getInstance()
    calendar.set(year, month, dayOfMonth)
    return calendar.time
}

fun Button.shake() {
    val shake = AnimationUtils.loadAnimation(this.context, R.anim.shake)
    this.startAnimation(shake)
}