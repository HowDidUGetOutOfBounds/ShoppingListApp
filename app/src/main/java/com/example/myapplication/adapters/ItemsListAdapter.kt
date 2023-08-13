package com.example.myapplication.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myapplication.data.ShoppingItem
import com.example.myapplication.databinding.ImageShoppingListItemBinding
import com.example.myapplication.databinding.TextShoppingListItemBinding
import com.example.myapplication.utils.getViewType

class ItemsListAdapter(
    val context: Context,
    val increaseItemAmountInStorage: (ShoppingItem) -> Unit,
    val decreaseItemAmountInStorage: (ShoppingItem) -> Unit,
    val canButtonClick: (ShoppingItem) -> Unit,
    val onUpdateItemClick: (ShoppingItem) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    inner class ImageItemViewHolder(private val binding: ImageShoppingListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(shoppingItem: ShoppingItem) {
            with(binding)
            {
                titleTextView.text = shoppingItem.title
                amountTextView.text = shoppingItem.amount.toString()
                contentTextView.text = shoppingItem.description
                increaseButton.setOnClickListener {
                    increaseItemAmountInStorage(
                        shoppingItem.copy(
                            amount = shoppingItem.amount + 1
                        )
                    )
                }
                decreaseButton.setOnClickListener {
                    decreaseItemAmountInStorage(
                        shoppingItem.copy(
                            amount = shoppingItem.amount - 1
                        )
                    )
                }
                deleteAllImageView.setOnClickListener {
                    canButtonClick(shoppingItem)
                }
                shoppingItem.image?.let { imageUri ->
                    Glide.with(context)
                        .load(imageUri.toUri())
                        .into(goodImageView)
                }
                root.setOnClickListener {
                    onUpdateItemClick(shoppingItem)
                }
            }
        }
    }

    inner class TextItemViewHolder(val binding: TextShoppingListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(shoppingItem: ShoppingItem) {
            with(binding)
            {
                titleTextView.text = shoppingItem.title
                amountTextView.text = shoppingItem.amount.toString()
                contentTextView.text = shoppingItem.description
                increaseButton.setOnClickListener {
                    increaseItemAmountInStorage(
                        shoppingItem.copy(
                            amount = shoppingItem.amount + 1
                        )
                    )
                }
                decreaseButton.setOnClickListener {
                    decreaseItemAmountInStorage(
                        shoppingItem.copy(
                            amount = shoppingItem.amount - 1
                        )
                    )
                }
                deleteAllImageView.setOnClickListener {
                    canButtonClick(shoppingItem)
                }
                root.setOnClickListener {
                    onUpdateItemClick(shoppingItem)
                }
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
        return differ.currentList.size
    }

    override fun getItemViewType(position: Int): Int {
        return differ.currentList[position].getViewType()
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (differ.currentList[position].getViewType() == VIEW_TYPE_IMAGE_ITEM) {
            (holder as ImageItemViewHolder).bind(differ.currentList[position])
        } else {
            (holder as TextItemViewHolder).bind(differ.currentList[position])
        }
    }

    private val differCallback = object : DiffUtil.ItemCallback<ShoppingItem>(){
        override fun areItemsTheSame(oldItem: ShoppingItem, newItem: ShoppingItem): Boolean {
            return  oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ShoppingItem, newItem: ShoppingItem): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this,differCallback)

    companion object {
        const val VIEW_TYPE_IMAGE_ITEM = 1
        const val VIEW_TYPE_TEXT_ITEM = 2
    }
}

