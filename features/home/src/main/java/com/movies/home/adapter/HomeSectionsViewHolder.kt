package com.movies.home.adapter

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.movies.domain.model.MovieItem
import com.movies.home.databinding.ItemHomeSectionBinding
import com.movies.shared.extensions.isOnline
import com.movies.shared.extensions.toggleVisibility

const val SIZE_COLUMN_TWO = 2

class HomeSectionsViewHolder(
    private val binding: ItemHomeSectionBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(
        item: HomeSection,
        languageIdSelected: String,
        onChangeRecommendedMovies: (String) -> Unit,
        goToMovieDetail: (Int) -> Unit
    ) {
        with(binding) {
            titleSection.text = item.title
            loadingSection.toggleVisibility(item.isLoading)
            if (item.showDataGrid.not()) {
                setMoviesContentList(item.data, goToMovieDetail)
            } else {
                setMoviesContentGrid(
                    item.data,
                    languageIdSelected,
                    onChangeRecommendedMovies,
                    goToMovieDetail
                )
            }
        }
    }

    private fun setMoviesContentList(movies: List<MovieItem>, goToMovieDetail: (Int) -> Unit) {
        binding.moviesContentSection.setContent {
            LazyRow(
                contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.padding(top = 16.dp, bottom = 10.dp)
            ) {
                items(movies) { movie ->
                    CardMovie(
                        image = movie.poster,
                        height = 200.dp
                    ) { goToMovieDetail.invoke(movie.id) }
                }
            }
        }
    }

    private fun setMoviesContentGrid(
        movies: List<MovieItem>,
        languageIdSelected: String,
        onChangeRecommendedMovies: (String) -> Unit,
        goToMovieDetail: (Int) -> Unit
    ) {
        with(binding) {
            moviesContentSection.toggleVisibility(show = root.context.isOnline())
            moviesContentSection.setContent {
                CustomDropDownLanguages(languageIdSelected, onChangeRecommendedMovies)
            }
            moviesGrid.toggleVisibility(true)
            moviesGrid.apply {
                val homeMoviesAdapter = HomeMoviesAdapter(goToMovieDetail)
                layoutManager = GridLayoutManager(context, SIZE_COLUMN_TWO)
                adapter = homeMoviesAdapter
                homeMoviesAdapter.submitList(movies)
            }
        }
    }

}
