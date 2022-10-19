package com.movies.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.movies.domain.model.MovieItem
import com.movies.home.adapter.HomeSection
import com.movies.home.adapter.HomeSectionsAdapter
import com.movies.home.databinding.FragmentHomeBinding
import com.movies.shared.extensions.toggleVisibility
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

internal const val UP_COMING_MOVIES_SECTION = 0
internal const val TOP_RATED_MOVIES_SECTION = 1
internal const val RECOMMENDED_MOVIES_SECTION = 2

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<HomeViewModel>()

    private lateinit var homeSectionsList: List<HomeSection>

    private val homeSectionsAdapter = HomeSectionsAdapter(
        onChangeRecommendedMovies = ::onChangeRecommendedMovies
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

        setupRecycler()
        setupObservers()
        viewModel.getUpComingMovies()
        viewModel.getTopRatedMovies()
        viewModel.getRecommendedMovies(ES_CO)
    }

    private fun setupRecycler() {
        homeSectionsList = listOf(
            HomeSection(title = getString(R.string.label_home_movies_up_coming)),
            HomeSection(title = getString(R.string.label_home_movies_top_rated)),
            HomeSection(
                title = getString(R.string.label_home_movies_recommended),
                showDataGrid = true
            )
        )
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
            is HomeUiState.ShowRecommendedMovies ->
                setUpMovies(uiState.data, RECOMMENDED_MOVIES_SECTION)
            is HomeUiState.ShowTopRatedMovies ->
                setUpMovies(uiState.data, TOP_RATED_MOVIES_SECTION)
            is HomeUiState.ShowUpcomingMovies ->
                setUpMovies(uiState.data, UP_COMING_MOVIES_SECTION)
            HomeUiState.Error -> showFeedbackError()
        }
    }

    private fun setUpMovies(value: List<MovieItem>, section: Int) {
        binding.loadingHome.toggleVisibility(false)
        homeSectionsList = viewModel.updateHomeSectionsList(homeSectionsList, section, value)
        homeSectionsAdapter.submitList(homeSectionsList)
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

}