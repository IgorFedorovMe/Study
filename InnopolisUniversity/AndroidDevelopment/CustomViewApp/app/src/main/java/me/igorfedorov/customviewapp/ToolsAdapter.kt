package me.igorfedorov.customviewapp

import android.graphics.PorterDuff
import com.hannesdorfmann.adapterdelegates4.AdapterDelegate
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding
import me.igorfedorov.customviewapp.base.Item
import me.igorfedorov.customviewapp.databinding.ItemPaletteBinding

fun colorAdapterDelegate(
    onClick: (Int) -> Unit
): AdapterDelegate<List<Item>> =
    adapterDelegateViewBinding<ToolsItem.ColorModel, Item, ItemPaletteBinding>(
        { layoutInflater, parent -> ItemPaletteBinding.inflate(layoutInflater, parent, false) }
    ) {
        bind { list ->

            binding.color.setColorFilter(
                context.resources.getColor(item.color, null),
                PorterDuff.Mode.SRC_IN
            )
            itemView.setOnClickListener { onClick(adapterPosition) }
        }
    }