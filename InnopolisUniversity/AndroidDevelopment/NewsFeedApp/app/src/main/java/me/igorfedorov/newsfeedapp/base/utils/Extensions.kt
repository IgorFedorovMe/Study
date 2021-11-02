package me.igorfedorov.newsfeedapp.base.utils

import android.app.Activity
import android.content.Context
import android.graphics.drawable.Drawable
import android.net.ConnectivityManager
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestBuilder
import com.hannesdorfmann.adapterdelegates4.AbsDelegationAdapter
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.onFailure
import kotlinx.coroutines.channels.trySendBlocking
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import me.igorfedorov.newsfeedapp.R
import me.igorfedorov.newsfeedapp.base.common.Constants.DEFAULT_THROTTLE_DELAY
import timber.log.Timber
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

fun <T : Fragment> T.toastShort(@StringRes message: Int) {
    Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
}

fun <T : Fragment> T.toastLong(@StringRes message: Int) {
    Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
}

fun <T : Fragment> T.toastShort(message: String) {
    Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
}

fun <T : Fragment> T.toastLong(message: String) {
    Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
}

fun ImageView.loadImage(
    src: String?,
    @DrawableRes errorRes: Int = R.drawable.news_feed_variant_outline,
    @DrawableRes placeholderRes: Int = R.drawable.news_feed_variant_outline,
    config: RequestBuilder<Drawable>.() -> Unit = {}
) {
    Glide
        .with(context)
        .load(src)
        .error(errorRes)
        .placeholder(placeholderRes)
        .apply { config(this) }
        .into(this)
}

fun <T> AbsDelegationAdapter<T>.setData(data: T) {
    items = data
    notifyDataSetChanged()
}

fun EditText.textChangeFlow(): Flow<String> {
    return callbackFlow {
        val watcher = object : TextWatcher {

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                trySendBlocking(s?.toString().orEmpty())
                    .onFailure { t -> Timber.d(t) }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun afterTextChanged(s: Editable?) {}
        }
        this@textChangeFlow.addTextChangedListener(watcher)
        awaitClose {
            this@textChangeFlow.removeTextChangedListener(watcher)
        }
    }
}

fun Fragment.hideKeyboard() {
    view?.let { activity?.hideKeyboard(it) }
}

fun Activity.hideKeyboard() {
    hideKeyboard(currentFocus ?: View(this))
}

fun Context.hideKeyboard(view: View) {
    val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
}
