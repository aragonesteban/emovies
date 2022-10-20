package com.movies.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavDeepLinkRequest
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.movies.domain.RECOMMENDED_MOVIES_SECTION
import com.movies.domain.TOP_RATED_MOVIES_SECTION
import com.movies.domain.UP_COMING_MOVIES_SECTION
import com.movies.home.adapter.HomeSection
import com.movies.home.adapter.HomeSectionsAdapter
import com.movies.home.databinding.FragmentHomeBinding
import com.movies.shared.extensions.toggleVisibility
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<HomeViewModel>()

    private val homeSectionsList by lazy {
        listOf(
            HomeSection(
                id = UP_COMING_MOVIES_SECTION,
                title = getString(R.string.label_home_movies_up_coming)
            ),
            HomeSection(
                id = TOP_RATED_MOVIES_SECTION,
                title = getString(R.string.label_home_movies_top_rated)
            ),
            HomeSection(
                id = RECOMMENDED_MOVIES_SECTION,
                title = getString(R.string.label_home_movies_recommended),
                showDataGrid = true
            )
        )
    }

    private val homeSectionsAdapter = HomeSectionsAdapter(
        onChangeRecommendedMovies = ::onChangeRecommendedMovies,
        goToMovieDetail = ::goToMovieDetail
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupObservers()
        viewModel.setHomeSectionsList(homeSectionsList)
    }

    private fun setupRecycler(homeSectionsList: List<HomeSection>) {
        with(binding) {
            contentHome.apply {
                layoutManager = LinearLayoutManager(requireContext())
                adapter = homeSectionsAdapter
                homeSectionsAdapter.submitList(homeSectionsList)
            }
            loadingHome.toggleVisibility(false)
        }
    }

    private fun setupObservers() {
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect(::handleViewState)
            }
        }
    }

    private fun handleViewState(uiState: HomeUiState) {
        when (uiState) {
            HomeUiState.Loading -> binding.loadingHome.toggleVisibility(true)
            is HomeUiState.ShowHomeSections -> setupRecycler(uiState.data)
            HomeUiState.Error -> showFeedbackError()
        }
    }

    private fun showFeedbackError() {
        Toast.makeText(
            requireContext(),
            com.movies.shared.R.string.label_generic_error,
            Toast.LENGTH_LONG
        ).show()
    }

    private fun onChangeRecommendedMovies(language: String) {
        viewModel.getRecommendedMovies(language)
    }

    private fun goToMovieDetail(movieId: Int) {
        val request = NavDeepLinkRequest.Builder
            .fromUri("movies-app://com.movies.app/movie_detail_fragment/$movieId".toUri())
            .build()
        findNavController().navigate(request)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}