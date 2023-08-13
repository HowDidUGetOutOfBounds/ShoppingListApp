package com.example.myapplication.ui

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.MyApp
import com.example.myapplication.adapters.ItemsListAdapter
import com.example.myapplication.data.ShoppingItem
import com.example.myapplication.databinding.FragmentAddItemBinding
import com.example.myapplication.domain.ShoppingListViewModel
import com.example.myapplication.domain.ShoppingListViewModelFactory
import com.example.myapplication.model.ShoppingItemRepository
import com.example.myapplication.model.ShoppingPreferences
import com.example.myapplication.utils.getViewType
import javax.inject.Inject

class AddItemFragment : Fragment() {

    private var _binding: FragmentAddItemBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var shoppingItemRepo: ShoppingItemRepository

    @Inject
    lateinit var prefs: ShoppingPreferences

    private val viewModel: ShoppingListViewModel by activityViewModels {
        ShoppingListViewModelFactory(
            shoppingItemRepo,
            prefs
        )
    }

    lateinit var imagePickerActivityResult: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        (requireActivity().application as MyApp).databaseComponent.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddItemBinding.inflate(inflater, container, false)

        imagePickerActivityResult = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result != null) {
                val imageUri: Uri? = result.data?.data
                if (imageUri != null) {
                    viewModel.setImageUri(imageUri)
                }
            }
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpViews()
        setUpObservers()
    }

    private fun setUpObservers() {
        viewModel.lastImageUriData.observe(viewLifecycleOwner) { imageUri ->
            if (imageUri != null) {
                Glide.with(this)
                    .load(imageUri)
                    .into(binding.takePhotoImageView)
            }
        }
        viewModel.activeItem.observe(viewLifecycleOwner) { item ->
            if (item != null) {

                hideStateButtons()
                item.image?.let {
                    viewModel.setImageUri(it.toUri())
                }
                with(binding) {
                    titleEditText.setText(item.title)
                    amountEditText.setText(item.amount.toString())
                    submitButton.setOnClickListener {
                        viewModel.setItemToSave(createItemToSave())
                        val actualItemState = viewModel.getItemToSave()
                        actualItemState?.let {
                            viewModel.updateItem(
                                item.copy(
                                    title = actualItemState.title,
                                    description = actualItemState.description,
                                    image = actualItemState.image,
                                    amount = actualItemState.amount
                                )
                            )
                        }
                        findNavController().popBackStack()
                    }
                }

                when (item.getViewType()) {
                    ItemsListAdapter.VIEW_TYPE_IMAGE_ITEM -> {
                        setupUiForImageItem(item)
                    }
                    ItemsListAdapter.VIEW_TYPE_TEXT_ITEM -> {
                        setupUiForTextItem(item)
                    }
                }
            }
        }
    }

    private fun setupUiForImageItem(item: ShoppingItem) {
        setImageItemState()
    }

    private fun setupUiForTextItem(item: ShoppingItem) {
        setTextItemState()
        with(binding)
        {
            descriptionEditText.setText(item.description)
        }
    }

    private fun hideStateButtons() {
        with(binding)
        {
            imageItemButton.visibility = View.GONE
            textItemButton.visibility = View.GONE
        }
    }

    private fun setUpViews() {
        with(binding)
        {
            submitButton.setOnClickListener {
                viewModel.setItemToSave(createItemToSave())
                val item = viewModel.getItemToSave()
                if (item != null) {
                    viewModel.addItem(item)
                }
                findNavController().popBackStack()
            }
            cancelButton.setOnClickListener {
                findNavController().popBackStack()
            }
            takePhotoImageView.setOnClickListener {
                getImageFromGallery()
            }
            imageItemButton.setOnClickListener {
                setImageItemState()
            }
            textItemButton.setOnClickListener {
                setTextItemState()
            }
        }

        viewModel.lastImageUriData.value = null
        viewModel.getItemById()
    }

    private fun getImageFromGallery() {
        val photoPickerIntent = Intent(Intent.ACTION_PICK)
        photoPickerIntent.type = "image/*"

        imagePickerActivityResult.launch(photoPickerIntent)
    }

    private fun createItemToSave(): ShoppingItem? {
        with(binding)
        {
            val amount = amountEditText.text.toString().toIntOrNull() ?: 0
            if (amount == 0) {
                return null
            } else {
                return ShoppingItem(
                    id = System.currentTimeMillis(),
                    title = titleEditText.text.toString(),
                    description = descriptionEditText.text.toString(),
                    amount = amount,
                    image = viewModel.lastImageUriData.value?.toString(),
                )
            }
        }
    }

    private fun setImageItemState() {
        with(binding)
        {
            takePhotoImageView.visibility = View.VISIBLE
            takePhotoTextView.visibility = View.VISIBLE
            descriptionEditText.visibility = View.GONE
        }
    }

    private fun setTextItemState() {
        with(binding) {
            takePhotoImageView.visibility = View.GONE
            takePhotoTextView.visibility = View.GONE
            descriptionEditText.visibility = View.VISIBLE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.setActiveId(0)
        _binding = null
    }
}