package com.kotlin.appmovies.data.remote

import com.kotlin.appmovies.application.AppConstants
import com.kotlin.appmovies.data.model.MovieList
import com.kotlin.appmovies.repository.WebService

class MovieDataSource(private val webService: WebService) {

    suspend fun getUpcomingMovies(): MovieList = webService.getUpcomingMovies(AppConstants.API_KEY)

    suspend fun getTopRatedMovies(): MovieList = webService.getTopRatedMovies(AppConstants.API_KEY)

    suspend fun getPopularMovies(): MovieList = webService.getPopularMovies(AppConstants.API_KEY)
}