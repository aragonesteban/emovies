package com.movies.home.adapter

import androidx.compose.ui.unit.dp
import androidx.recyclerview.widget.RecyclerView
import com.movies.domain.model.MovieItem
import com.movies.home.databinding.ItemHomeMovieBinding

class HomeMoviesViewHolder(
    private val binding: ItemHomeMovieBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: MovieItem, goToMovieDetail: (Int) -> Unit) {
        binding.composeMoviePoster.setContent {
            CardMovie(
                image = item.poster,
                height = 205.dp,
                isInGrid = true
            ) { goToMovieDetail.invoke(item.id) }
        }
    }

}