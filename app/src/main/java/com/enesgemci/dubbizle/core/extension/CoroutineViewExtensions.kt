package com.enesgemci.dubbizle.core.extension

import android.view.View
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.channels.actor
import kotlinx.coroutines.delay

private const val THROTTLE_TIME = 500L
private const val DEBOUNCE_TIME = 300L

/**
 * Created to get the same behaviour as throttleFirst in RxJava
 *
 * @param action    method body
 */
fun View.onClick(
    throttleTime: Long = THROTTLE_TIME,
    action: suspend (View) -> Unit
) {

    // launch one actor
    val event = GlobalScope.actor<View>(Dispatchers.Main) {
        for (event in channel) {
            action(event)
            delay(throttleTime)
        }
    }

    setOnClickListener {
        event.offer(it)
    }
}
