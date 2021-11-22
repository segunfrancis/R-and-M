package com.segunfrancis.randm.ui.home

import android.os.Bundle
import android.view.View
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import coil.ImageLoader
import com.segunfrancis.randm.R
import com.segunfrancis.randm.databinding.FragmentHomeBinding
import com.segunfrancis.randm.util.Result
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : Fragment(R.layout.fragment_home) {

    private lateinit var binding: FragmentHomeBinding
    private val viewModel by viewModel<HomeViewModel>()
    private val imageLoader: ImageLoader by inject()
    private val characterAdapter: CharacterAdapter by lazy {
        CharacterAdapter(imageLoader = imageLoader, onItemClick = { viewModel.toDetail(it) })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHomeBinding.bind(view)

        setupObservers()
    }

    private fun setupObservers() {
        viewModel.uiState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is Result.Loading -> binding.loadingIndicator.root.isVisible = true
                is Result.Error -> renderError(state.errorMessage)
                is Result.Success -> renderContent(state.data)
            }
        }
        viewModel.interaction.observe(viewLifecycleOwner) {
            when (it) {
                is HomeAction.Navigate -> findNavController().navigate(it.destination)
            }
        }
    }

    private fun setupUI() = with(binding) {
        characterRecyclerView.apply {
            adapter = characterAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun renderError(message: String?) = with(binding) {
        loadingIndicator.root.isGone = true
    }

    private fun renderContent(characters: List<Character?>) {
        binding.loadingIndicator.root.isGone = true
        setupUI()
        characterAdapter.submitList(characters)
    }
}
