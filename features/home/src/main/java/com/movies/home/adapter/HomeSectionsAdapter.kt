package com.movies.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.movies.domain.model.MovieItem
import com.movies.home.ES_CO
import com.movies.home.databinding.ItemHomeSectionBinding
import com.movies.shared.utils.basicDiffUtil

class HomeSectionsAdapter(
    private val onChangeRecommendedMovies: (String) -> Unit
) : ListAdapter<HomeSection, HomeSectionsViewHolder>(
    basicDiffUtil { old, new -> old.title == new.title }
) {

    private var languageIdSelected = ES_CO

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeSectionsViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return HomeSectionsViewHolder(
            binding = ItemHomeSectionBinding.inflate(layoutInflater, parent, false)
        )
    }

    override fun onBindViewHolder(holder: HomeSectionsViewHolder, position: Int) {
        holder.bind(item = getItem(position), languageIdSelected) { language ->
            languageIdSelected = language
            onChangeRecommendedMovies.invoke(language)
        }
    }

}

data class HomeSection(
    val isLoading: Boolean = true,
    val title: String,
    val data: List<MovieItem> = listOf(),
    val showDataGrid: Boolean = false
)