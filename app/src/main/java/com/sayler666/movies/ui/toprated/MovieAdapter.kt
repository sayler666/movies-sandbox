package com.sayler666.movies.ui.toprated

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.sayler666.movies.databinding.MovieGridItemBinding
import com.sayler666.movies.db.MovieDb
import com.sayler666.movies.ui.toprated.MovieAdapter.MovieViewHolder.Companion.from

class MovieAdapter(private val onClick: (Int) -> Unit) : ListAdapter<MovieDb, MovieAdapter.MovieViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        return from(parent, onClick)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val flower = getItem(position)
        holder.bind(flower)
    }

    companion object {
        val DIFF_CALLBACK: DiffUtil.ItemCallback<MovieDb> =
            object : DiffUtil.ItemCallback<MovieDb>() {
                override fun areItemsTheSame(oldItem: MovieDb, newItem: MovieDb) =
                    oldItem == newItem

                override fun areContentsTheSame(oldItem: MovieDb, newItem: MovieDb) =
                    oldItem.id == newItem.id
            }
    }

    class MovieViewHolder(private val binding: MovieGridItemBinding, val onClick: (Int) -> Unit) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(movie: MovieDb) {
            binding.run {
                title.text = movie.title
                binding.detailItem.setOnClickListener { view -> onClick.invoke(movie.id) }
                poster.load(movie.imgUrl)
            }
        }

        companion object {
            fun from(parent: ViewGroup, onClick: (Int) -> Unit): MovieViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = MovieGridItemBinding.inflate(layoutInflater, parent, false)
                return MovieViewHolder(binding, onClick)
            }
        }
    }
}

