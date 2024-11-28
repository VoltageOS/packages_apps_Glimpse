/*
 * SPDX-FileCopyrightText: 2023 The LineageOS Project
 * SPDX-License-Identifier: Apache-2.0
 */

package org.lineageos.glimpse.recyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil3.load
import coil3.request.placeholder
import org.lineageos.glimpse.R
import org.lineageos.glimpse.models.Album

class AlbumThumbnailAdapter(
    private val onItemSelected: (album: Album) -> Unit,
) : ListAdapter<Album, AlbumThumbnailAdapter.AlbumViewHolder>(ALBUM_COMPARATOR) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)

        val view = layoutInflater.inflate(R.layout.album_thumbnail_view, parent, false)

        return AlbumViewHolder(view, onItemSelected)
    }

    override fun onBindViewHolder(holder: AlbumViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object {
        val ALBUM_COMPARATOR = object : DiffUtil.ItemCallback<Album>() {
            override fun areItemsTheSame(oldItem: Album, newItem: Album) = oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Album, newItem: Album) =
                oldItem.id == newItem.id && oldItem.size == newItem.size
        }
    }

    class AlbumViewHolder(
        itemView: View,
        private val onItemSelected: (album: Album) -> Unit,
    ) : RecyclerView.ViewHolder(itemView) {
        // Views
        private val descriptionTextView =
            itemView.findViewById<TextView>(R.id.descriptionTextView)!!
        private val itemsCountTextView = itemView.findViewById<TextView>(R.id.itemsCountTextView)!!
        private val thumbnailImageView = itemView.findViewById<ImageView>(R.id.thumbnailImageView)!!

        fun bind(album: Album) {
            descriptionTextView.text = album.name
            itemsCountTextView.text = itemView.resources.getQuantityString(
                R.plurals.album_thumbnail_items, album.size, album.size
            )

            thumbnailImageView.load(album.thumbnail?.uri) {
                album.thumbnail?.let {
                    memoryCacheKey("thumbnail_${it.id}")
                }
                size(DisplayAwareGridLayoutManager.MAX_THUMBNAIL_SIZE)
                placeholder(R.drawable.thumbnail_placeholder)
            }

            itemView.setOnClickListener {
                onItemSelected(album)
            }
        }
    }
}
