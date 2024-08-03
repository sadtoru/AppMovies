package com.kotlin.appmovies.ui.movie.adapters.concat

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kotlin.appmovies.core.BaseConcatHolder
import com.kotlin.appmovies.databinding.PopularMoviesRowBinding
import com.kotlin.appmovies.ui.movie.adapters.MovieAdapter

class PopularConcatAdapter(private val moviesAdapter: MovieAdapter) :
    RecyclerView.Adapter<BaseConcatHolder<*>>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseConcatHolder<*> {
        val itemBinding =
            PopularMoviesRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ConcatViewHolder(itemBinding)
    }

    override fun getItemCount(): Int = 1

    override fun onBindViewHolder(holder: BaseConcatHolder<*>, position: Int) {
        when (holder) {
            is ConcatViewHolder -> holder.bind(moviesAdapter)
        }
    }

    private inner class ConcatViewHolder(val binding: PopularMoviesRowBinding) :
        BaseConcatHolder<MovieAdapter>(binding.root) {
        override fun bind(adapter: MovieAdapter) {
            binding.rvPopularMovies.adapter = adapter
        }

    }
}