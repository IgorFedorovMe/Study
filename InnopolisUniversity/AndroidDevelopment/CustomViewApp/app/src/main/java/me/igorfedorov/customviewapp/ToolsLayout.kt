package me.igorfedorov.customviewapp

import android.content.Context
import android.util.AttributeSet
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter
import me.igorfedorov.customviewapp.base.utils.setAdapterAndCleanupOnDetachFromWindow
import me.igorfedorov.customviewapp.base.utils.setData

class ToolsLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : CardView(context, attrs, defStyleAttr) {

    private var onClick: (Int) -> Unit = {}

    private val adapterDelegate = ListDelegationAdapter(
        colorAdapterDelegate {
            onClick(it)
        },
        sizeAdapterDelegate {
            onClick(it)
        },
        lineAdapterDelegate {
            onClick(it)
        }
    )

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        findViewById<RecyclerView>(R.id.toolsRecyclerView).apply {
            setAdapterAndCleanupOnDetachFromWindow(adapterDelegate)
            layoutManager = LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
        }
    }

    fun render(listToolsItem: List<ToolsItem>) {
        adapterDelegate.setData(listToolsItem)
    }

    fun setOnClickListener(onClick: (Int) -> Unit) {
        this.onClick = onClick
    }
}