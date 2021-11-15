package me.igorfedorov.customviewapp

import android.graphics.PorterDuff
import com.hannesdorfmann.adapterdelegates4.AdapterDelegate
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding
import me.igorfedorov.customviewapp.base.Item
import me.igorfedorov.customviewapp.databinding.ItemLineBinding
import me.igorfedorov.customviewapp.databinding.ItemPaletteBinding
import me.igorfedorov.customviewapp.databinding.ItemSizeBinding

fun colorAdapterDelegate(
    onClick: (Int) -> Unit
): AdapterDelegate<List<Item>> =
    adapterDelegateViewBinding<ToolsItem.ColorModel, Item, ItemPaletteBinding>(
        { layoutInflater, parent -> ItemPaletteBinding.inflate(layoutInflater, parent, false) }
    ) {

        itemView.setOnClickListener {
            onClick(adapterPosition)
        }

        bind {
            binding.color.setColorFilter(
                context.resources.getColor(item.color, null),
                PorterDuff.Mode.SRC_IN
            )
        }
    }

fun sizeAdapterDelegate(
    onClick: (Int) -> Unit
): AdapterDelegate<List<Item>> =
    adapterDelegateViewBinding<ToolsItem.SizeModel, Item, ItemSizeBinding>(
        { layoutInflater, parent -> ItemSizeBinding.inflate(layoutInflater, parent, false) }
    ) {

        itemView.setOnClickListener {
            onClick(adapterPosition)
        }

        bind {
            binding.size.text = item.enumSize.name
        }
    }

fun lineAdapterDelegate(
    onClick: (Int) -> Unit
): AdapterDelegate<List<Item>> =
    adapterDelegateViewBinding<ToolsItem.LineModel, Item, ItemLineBinding>(
        { layoutInflater, parent -> ItemLineBinding.inflate(layoutInflater, parent, false) }
    ) {

        itemView.setOnClickListener {
            onClick(adapterPosition)
        }
        bind {
            binding.line.text = item.enumLine.name
        }
    }