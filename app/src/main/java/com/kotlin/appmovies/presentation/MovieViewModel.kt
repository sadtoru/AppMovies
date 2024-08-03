package com.kotlin.appmovies.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import com.kotlin.appmovies.core.Resource
import com.kotlin.appmovies.repository.MovieRepository
import kotlinx.coroutines.Dispatchers

class MovieViewModel(private val repo: MovieRepository) : ViewModel() {

    fun fetchMainScreenMovies() = liveData(Dispatchers.IO) {
        emit(Resource.Loading())

        try {
            emit(
                Resource.Success(
                    Triple(
                        repo.getUpcomingMovies(),
                        repo.getPopularMovies(),
                        repo.getTopRatedMovies()
                    )
                )
            )
        } catch (e: Exception) {
            emit(Resource.Failure(e))
        }
    }

}

class ModelViewModelFactory(private val repo: MovieRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(MovieRepository::class.java).newInstance(repo)
    }
}