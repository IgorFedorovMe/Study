package me.igorfedorov.newsfeedapp.base.utils

import android.content.Context
import android.net.ConnectivityManager
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import me.igorfedorov.newsfeedapp.common.Constants.DEFAULT_THROTTLE_DELAY
import java.text.SimpleDateFormat
import java.util.*

val Context.connectivityManager: ConnectivityManager
    get() =
        this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

fun RecyclerView.setAdapterAndCleanupOnDetachFromWindow(recyclerViewAdapter: RecyclerView.Adapter<*>) {
    adapter = recyclerViewAdapter
    addOnAttachStateChangeListener(object : View.OnAttachStateChangeListener {
        override fun onViewDetachedFromWindow(v: View?) {
            adapter = null
            removeOnAttachStateChangeListener(this)
        }

        override fun onViewAttachedToWindow(v: View?) {
        }
    })
}

fun View.setThrottledClickListener(delay: Long = DEFAULT_THROTTLE_DELAY, onClick: (View) -> Unit) {
    setOnClickListener {
        throttle(delay) {
            onClick(it)
        }
    }
}

private var lastClickTimestamp = 0L
fun View.throttle(delay: Long = DEFAULT_THROTTLE_DELAY, action: () -> Unit): Boolean {
    val currentTimestamp = System.currentTimeMillis()
    val delta = currentTimestamp - lastClickTimestamp
    if (delta !in 0L..delay) {
        lastClickTimestamp = currentTimestamp
        action()
        return true
    }
    return false
}

fun String.dateFromISO8601(
    locale: Locale = Locale.US,
    formatFrom: SimpleDateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", locale),
    formatTo: SimpleDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm", locale)
): String {
    return formatTo.format(formatFrom.parse(this) ?: "")
}