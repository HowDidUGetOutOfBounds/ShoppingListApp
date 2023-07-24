package com.example.myapplication.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.MyApp
import com.example.myapplication.R
import com.example.myapplication.adapters.ItemsListAdapter
import com.example.myapplication.data.ShoppingItem
import com.example.myapplication.databinding.MainScreenFragmentBinding
import com.example.myapplication.domain.ShoppingListViewModel
import com.example.myapplication.domain.ShoppingListViewModelFactory
import com.example.myapplication.model.ShoppingItemRepository
import com.example.myapplication.utils.Utils
import javax.inject.Inject

class MainScreenFragment : Fragment() {

    private var _binding: MainScreenFragmentBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var shoppingItemRepo: ShoppingItemRepository

    private val viewModel: ShoppingListViewModel by activityViewModels {
        ShoppingListViewModelFactory(
            shoppingItemRepo
        )
    }

    var mainRecyclerViewAdapter: ItemsListAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        (requireActivity().application as MyApp).databaseComponent.inject(this)
        super.onCreate(savedInstanceState)
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = MainScreenFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("TAG", "onViewCreated: ${viewModel.toString()}")
        setupViews()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupViews() {
        mainRecyclerViewAdapter = ItemsListAdapter(
            context = requireContext(),
            list = viewModel.getAll(),
            increaseItemAmountInStorage = { item ->
                viewModel.updateItem(item)
            },
            decreaseItemAmountInStorage = { item ->
                viewModel.updateItem(item)
            }
        )

        binding.recyclerView.adapter = mainRecyclerViewAdapter
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())

        binding.addItemButton.setOnClickListener {
            findNavController().navigate(R.id.action_mainScreenFragment_to_addItemFragment)
        }

        viewModel.databaseData.observe(viewLifecycleOwner){ dataSet ->
            mainRecyclerViewAdapter?.setNewDataset(dataSet)
        }
    }
}