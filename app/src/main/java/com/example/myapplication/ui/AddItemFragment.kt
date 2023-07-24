package com.example.myapplication.ui

import android.R
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.MyApp
import com.example.myapplication.data.ShoppingItem
import com.example.myapplication.databinding.FragmentAddItemBinding
import com.example.myapplication.domain.ShoppingListViewModel
import com.example.myapplication.domain.ShoppingListViewModelFactory
import com.example.myapplication.model.ShoppingItemRepository
import javax.inject.Inject


private const val GALLERY_REQUEST = 202

class AddItemFragment : Fragment() {

    private var _binding: FragmentAddItemBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var shoppingItemRepo: ShoppingItemRepository

    private val viewModel: ShoppingListViewModel by activityViewModels {
        ShoppingListViewModelFactory(
            shoppingItemRepo
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
        viewModel.lastImageUriData.observe(viewLifecycleOwner) { imageUri ->
            if (imageUri != null) {
                Glide.with(this)
                    .load(imageUri)
                    .into(binding.takePhotoImageView)
            }
        }
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
                    image = viewModel.lastImageUriData.value ?: null,
                )
            }
        }
    }

    fun setImageItemState() {
        with(binding)
        {
            takePhotoImageView.visibility = View.VISIBLE
            takePhotoTextView.visibility = View.VISIBLE
            descriptionEditText.visibility = View.GONE
        }
    }

    fun setTextItemState() {
        with(binding) {
            takePhotoImageView.visibility = View.GONE
            takePhotoTextView.visibility = View.GONE
            descriptionEditText.visibility = View.VISIBLE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}