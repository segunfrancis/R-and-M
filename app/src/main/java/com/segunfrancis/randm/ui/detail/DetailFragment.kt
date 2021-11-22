package com.segunfrancis.randm.ui.detail

import android.os.Bundle
import android.view.View
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.HORIZONTAL
import coil.ImageLoader
import com.segunfrancis.randm.R
import com.segunfrancis.randm.databinding.FragmentDetailBinding
import com.segunfrancis.randm.ui.home.HomeAction
import com.segunfrancis.randm.util.Result
import com.segunfrancis.randm.util.loadImage
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class DetailFragment : Fragment(R.layout.fragment_detail) {

    private lateinit var binding: FragmentDetailBinding
    private val args by navArgs<DetailFragmentArgs>()
    private val viewModel by viewModel<DetailViewModel> { parametersOf(args.id) }
    private val episodeAdapter: EpisodeAdapter by lazy {
        EpisodeAdapter(onEpisodeClick = {
            viewModel.toEpisodeDetail(it)
        })
    }
    private val imageLoader: ImageLoader by inject()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentDetailBinding.bind(view)

        setupObservers()
    }

    private fun setupObservers() {
        viewModel.uiState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is Result.Loading -> {
                    binding.loadingIndicator.root.isVisible = true
                }
                is Result.Success -> renderContent(state.data)
                is Result.Error -> renderError(state.errorMessage)
            }
        }
        viewModel.interaction.observe(viewLifecycleOwner) {
            when (it) {
                is HomeAction.Navigate -> findNavController().navigate(it.destination)
            }
        }
    }

    private fun renderError(message: String?) = with(binding) {
        loadingIndicator.root.isGone = true
    }

    private fun renderContent(detail: CharacterDetail?) = with(binding) {
        loadingIndicator.root.isGone = true
        episodeRecyclerView.apply {
            adapter = episodeAdapter
            layoutManager = LinearLayoutManager(requireContext(), HORIZONTAL, false)
        }
        episodeAdapter.submitList(detail?.episodes)

        nameValue.text = detail?.name
        createdValue.text = detail?.created
        statusValue.text = detail?.status
        genderValue.text = detail?.gender
        speciesValue.text = detail?.species
        characterImage.loadImage(detail?.image, imageLoader)
    }
}
