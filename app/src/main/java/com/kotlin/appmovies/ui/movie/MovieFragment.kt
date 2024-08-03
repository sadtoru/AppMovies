package com.kotlin.appmovies.ui.movie

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ConcatAdapter
import com.kotlin.appmovies.R
import com.kotlin.appmovies.core.Resource
import com.kotlin.appmovies.data.model.Movie
import com.kotlin.appmovies.data.remote.MovieDataSource
import com.kotlin.appmovies.databinding.FragmentMovieBinding
import com.kotlin.appmovies.presentation.ModelViewModelFactory
import com.kotlin.appmovies.presentation.MovieViewModel
import com.kotlin.appmovies.repository.MovieRepositoryImpl
import com.kotlin.appmovies.repository.RetrofitClient
import com.kotlin.appmovies.ui.movie.adapters.MovieAdapter
import com.kotlin.appmovies.ui.movie.adapters.concat.PopularConcatAdapter
import com.kotlin.appmovies.ui.movie.adapters.concat.TopRatedConcatAdapter
import com.kotlin.appmovies.ui.movie.adapters.concat.UpcomingConcatAdapter


class MovieFragment : Fragment(R.layout.fragment_movie), MovieAdapter.OnMovieClickListener {

    private lateinit var binding: FragmentMovieBinding

    private val viewModel by viewModels<MovieViewModel> {
        ModelViewModelFactory(
            MovieRepositoryImpl(
                MovieDataSource(RetrofitClient.webservice)
            )
        )
    }

    private lateinit var concatAdapter: ConcatAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMovieBinding.bind(view)
        concatAdapter = ConcatAdapter()

        viewModel.fetchMainScreenMovies().observe(viewLifecycleOwner, Observer { result ->
            when (result) {
                is Resource.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }

                is Resource.Success -> {
                    binding.progressBar.visibility = View.GONE
                    concatAdapter.apply {
                        addAdapter(
                            0,
                            UpcomingConcatAdapter(
                                MovieAdapter(
                                    result.data.first.results,
                                    this@MovieFragment
                                )
                            )
                        )
                        addAdapter(
                            1,
                            TopRatedConcatAdapter(
                                MovieAdapter(
                                    result.data.second.results,
                                    this@MovieFragment
                                )
                            )
                        )
                        addAdapter(
                            2,
                            PopularConcatAdapter(
                                MovieAdapter(
                                    result.data.third.results,
                                    this@MovieFragment
                                )
                            )
                        )
                    }
                    binding.rvMovies.adapter = concatAdapter
                }

                is Resource.Failure -> {
                    Log.d("Error", "${result.exception}")
                }

            }
        })


    }

    override fun onMovieClick(movie: Movie) {
        val action = MovieFragmentDirections.actionMovieFragmentToMovieDetailFragment(
            movie.poster_path,
            movie.backdrop_path,
            movie.vote_average.toFloat(),
            movie.vote_count,
            movie.overview,
            movie.title,
            movie.original_language,
            movie.release_date
        )
        findNavController().navigate(action)
    }
}