package com.example.myapplication.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.data.ShoppingItem
import com.example.myapplication.databinding.ImageShoppingListItemBinding
import com.example.myapplication.databinding.TextShoppingListItemBinding

class ItemsListAdapter(
    val list: ArrayList<ShoppingItem>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    class ImageItemViewHolder(val binding: ImageShoppingListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(shoppingItem: ShoppingItem) {
            with(binding)
            {
                titleTextView.text = shoppingItem.title
                amountTextView.text = shoppingItem.amount.toString()
                contentTextView.text = shoppingItem.description
                shoppingItem.image?.let {
                    goodImageView.setImageResource(it)
                }
            }
        }
    }

    class TextItemViewHolder(val binding: TextShoppingListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(shoppingItem: ShoppingItem) {
            with(binding)
            {
                titleTextView.text = shoppingItem.title
                amountTextView.text = shoppingItem.amount.toString()
                contentTextView.text = shoppingItem.description
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == VIEW_TYPE_IMAGE_ITEM) {
            val binding = ImageShoppingListItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            ImageItemViewHolder(binding)
        } else {
            val binding = TextShoppingListItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            TextItemViewHolder(binding)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun getItemViewType(position: Int): Int {
        return list[position].getViewType()
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (list[position].getViewType() == VIEW_TYPE_IMAGE_ITEM) {
            (holder as ImageItemViewHolder).bind(list[position])
        } else {
            (holder as TextItemViewHolder).bind(list[position])
        }
    }

    companion object {
        const val VIEW_TYPE_IMAGE_ITEM = 1
        const val VIEW_TYPE_TEXT_ITEM = 2
    }
}

