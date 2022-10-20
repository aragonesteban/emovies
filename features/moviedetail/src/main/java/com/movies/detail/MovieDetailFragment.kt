package com.movies.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import coil.load
import com.movies.detail.databinding.FragmentMovieDetailBinding
import com.movies.domain.model.MovieDetail
import com.movies.shared.extensions.toggleVisibility
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class MovieDetailFragment : Fragment() {

    private val viewModel by viewModels<MovieDetailViewModel>()

    companion object {
        const val MOVIE_ID_KEY = "movieId"
    }

    private var _binding: FragmentMovieDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMovieDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupToolbar()
        setupObservers()

        val movieId = arguments?.getInt(MOVIE_ID_KEY) ?: 0
        viewModel.getMovieDetail(movieId)
    }

    private fun setupToolbar() {
        (activity as? AppCompatActivity)?.apply {
            setSupportActionBar(binding.toolbarMovieDetail)
            supportActionBar?.setDisplayShowTitleEnabled(false)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            binding.toolbarMovieDetail.setNavigationOnClickListener {
                findNavController().popBackStack()
            }
        }
    }

    private fun setupObservers() {
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect(::handleViewState)
            }
        }
    }

    private fun handleViewState(viewState: MovieDetailUiState) {
        when (viewState) {
            MovieDetailUiState.Loading -> binding.loadingMovieDetail.toggleVisibility(true)
            is MovieDetailUiState.ShowMovieDetail -> setMovieDetail(viewState.data)
            MovieDetailUiState.Error -> showFeedbackError()
        }
    }

    private fun setMovieDetail(data: MovieDetail) {
        with(binding) {
            loadingMovieDetail.toggleVisibility(false)
            movieDetailPoster.load(data.posterPath) {
                crossfade(true)
            }
            movieDetailTitle.text = data.title
            movieDetailYear.toggleVisibility(true)
            movieDetailYear.text = data.releaseDate.split("-").first()
            movieDetailLanguage.toggleVisibility(true)
            movieDetailLanguage.text = data.originalLanguage
            movieDetailRating.toggleVisibility(true)
            movieDetailRating.text = data.voteAverage.toString()
            movieDetailGenres.text = data.genres
            movieDetailOverview.text = data.overview
            btnWatchTrailer.toggleVisibility(value = data.videoYoutubeKey.isNotEmpty())
            btnWatchTrailer.setOnClickListener {
                openMovieTrailer(data.videoYoutubeKey)
            }
        }
    }

    private fun openMovieTrailer(videoYoutubeKey: String) {
        val movieTrailerBottomSheet = MovieTrailerBottomSheet.newInstance(videoYoutubeKey)
        movieTrailerBottomSheet.show(parentFragmentManager, movieTrailerBottomSheet.tag)
    }

    private fun showFeedbackError() {
        Toast.makeText(
            requireContext(),
            com.movies.shared.R.string.label_generic_error,
            Toast.LENGTH_LONG
        ).show()
    }

}