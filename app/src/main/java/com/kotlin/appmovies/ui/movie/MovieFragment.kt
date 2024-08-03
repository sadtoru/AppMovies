package com.kotlin.appmovies.ui.movie

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.kotlin.appmovies.R
import com.kotlin.appmovies.core.Resource
import com.kotlin.appmovies.data.remote.MovieDataSource
import com.kotlin.appmovies.databinding.FragmentMovieBinding
import com.kotlin.appmovies.presentation.ModelViewModelFactory
import com.kotlin.appmovies.presentation.MovieViewModel
import com.kotlin.appmovies.repository.MovieRepositoryImpl
import com.kotlin.appmovies.repository.RetrofitClient


class MovieFragment : Fragment(R.layout.fragment_movie) {

    private lateinit var binding: FragmentMovieBinding

    private val viewModel by viewModels<MovieViewModel> {
        ModelViewModelFactory(
            MovieRepositoryImpl(
                MovieDataSource(RetrofitClient.webservice)
            )
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMovieBinding.bind(view)

        viewModel.fetchUpcomingMovies().observe(viewLifecycleOwner, Observer { result ->
            when (result) {
                is Resource.Loading -> {
                    Log.d("LiveData", "Loading...")
                }

                is Resource.Success -> {
                    Log.d("LiveData", "${result.data}")
                }

                is Resource.Failure -> {
                    Log.d("Error", "${result.exception}")
                }

            }
        })

    }
}