package com.example.tmdbclient.presentation.artist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.tmdbclient.R
import com.example.tmdbclient.data.model.artist.Artist
import com.example.tmdbclient.databinding.ListItemBinding

class ArtistAdapter() : RecyclerView.Adapter<ArtistViewHolder>() {

    private val artistList = ArrayList<Artist>()

    fun setList(artists: List<Artist>) {
        artistList.clear()
        artistList.addAll(artists)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArtistViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: ListItemBinding = DataBindingUtil.inflate(
            layoutInflater,
            R.layout.list_item,
            parent,
            false
        )

        return ArtistViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return artistList.size
    }

    override fun onBindViewHolder(holder: ArtistViewHolder, position: Int) {
        holder.bind(artistList[position])
    }

}

class ArtistViewHolder(val binding: ListItemBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(artist: Artist) {
        binding.titleTextView.text = artist.name
        binding.descriptionTextView.visibility = View.GONE

        val profileImageUrl = "https://image.tmdb.org/t/p/w500" + artist.profilePath

        Glide.with(binding.imageView.context)
            .load(profileImageUrl)
            .into(binding.imageView)
    }

}