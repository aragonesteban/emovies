package com.movies.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.movies.domain.model.MovieItem
import com.movies.home.databinding.ItemHomeMovieBinding
import com.movies.shared.utils.basicDiffUtil

class HomeMoviesAdapter(private val goToMovieDetail: (Int) -> Unit) :
    ListAdapter<MovieItem, HomeMoviesViewHolder>(
        basicDiffUtil { old, new -> old.id == new.id }
    ) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeMoviesViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return HomeMoviesViewHolder(
            binding = ItemHomeMovieBinding.inflate(layoutInflater, parent, false)
        )
    }

    override fun onBindViewHolder(holder: HomeMoviesViewHolder, position: Int) {
        holder.bind(item = getItem(position), goToMovieDetail)
    }

}