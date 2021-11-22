package com.segunfrancis.randm.ui.detail

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.HORIZONTAL
import coil.ImageLoader
import com.segunfrancis.randm.R
import com.segunfrancis.randm.databinding.FragmentDetailBinding
import com.segunfrancis.randm.util.Result
import com.segunfrancis.randm.util.loadImage
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import timber.log.Timber

class DetailFragment : Fragment(R.layout.fragment_detail) {

    private lateinit var binding: FragmentDetailBinding
    private val args by navArgs<DetailFragmentArgs>()
    private val viewModel by viewModel<DetailViewModel> { parametersOf(args.id) }
    private val episodeAdapter: DetailAdapter by lazy { DetailAdapter() }
    private val imageLoader: ImageLoader by inject()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentDetailBinding.bind(view)

        setupObservers()
    }

    private fun setupObservers() {
        viewModel.uiState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is Result.Loading -> { Timber.d("Loading...") }
                is Result.Success -> {
                    renderContent(state.data)
                }
                is Result.Error -> { Timber.e(state.errorMessage) }
            }
        }
    }

    private fun renderContent(detail: CharacterDetail?) = with(binding) {
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
