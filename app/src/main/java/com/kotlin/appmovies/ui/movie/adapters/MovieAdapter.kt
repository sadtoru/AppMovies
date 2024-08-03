package com.kotlin.appmovies.ui.movie.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.kotlin.appmovies.core.BaseViewHolder
import com.kotlin.appmovies.data.model.Movie
import com.kotlin.appmovies.databinding.MovieItemBinding

class MovieAdapter(
    private val movieList: List<Movie>,
    private val itemClickListener: OnMovieClickListener
) : RecyclerView.Adapter<BaseViewHolder<*>>() {

    interface OnMovieClickListener {
        fun onMovieClick(movie: Movie)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        val itemBinding =
            MovieItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        // cargar el layout
        val holder = MoviesViewHolder(itemBinding, parent.context)

        //obtener la posicion de cada click en la lista
        itemBinding.root.setOnClickListener {
            val position = holder.adapterPosition.takeIf {
                it != DiffUtil.DiffResult.NO_POSITION
            }
                ?: return@setOnClickListener //si es nulo no hace nada
            itemClickListener.onMovieClick(movieList[position])//devuelve la posición del item clickeado
        }
        return holder
    }

    override fun getItemCount(): Int = movieList.size

    //cada uno de los datos se dibuja en pantalla
    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        when (holder) {
            is MoviesViewHolder -> holder.bind(movieList[position])
        }
    }

    private inner class MoviesViewHolder(val binding: MovieItemBinding, val context: Context) :
        BaseViewHolder<Movie>(binding.root) {
        override fun bind(item: Movie) {
            Glide.with(context)
                .load("https://image.tmdb.org/t/p/w500/${item.poster_path}")//cargar imagen
                .centerCrop() // redimiensionar imagen
                .into(binding.imgMovie) //carga imagen en el id imgMovie
        }

    }
}