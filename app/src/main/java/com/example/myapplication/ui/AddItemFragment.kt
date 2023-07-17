package com.example.myapplication.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.example.MyApp
import com.example.myapplication.data.ShoppingItem
import com.example.myapplication.databinding.FragmentAddItemBinding
import com.example.myapplication.domain.ShoppingListViewModel
import com.example.myapplication.domain.ShoppingListViewModelFactory


class AddItemFragment : Fragment() {

    private var _binding: FragmentAddItemBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ShoppingListViewModel by viewModels {
        ShoppingListViewModelFactory(
            (requireActivity().application as MyApp).shoppingItemRepository
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddItemBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("TAG", "onViewCreated: ${viewModel.toString()}")
        setUpViews()
    }

    private fun setUpViews() {
        with(binding)
        {
            submitButton.setOnClickListener {
                val itemToSave = createItemToSave()
                if (itemToSave != null) {
                    viewModel.addItem(itemToSave)
                }
                Log.d("TAG", viewModel.getAll().toString())
            }
        }
    }

    private fun createItemToSave(): ShoppingItem? {
        with(binding)
        {
            return ShoppingItem(
                id = System.currentTimeMillis(),
                title = titleEditText.text.toString(),
                description = descriptionEditText.text.toString(),
                amount = amountEditText.text.toString().toInt(),
                image = null,
            )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}