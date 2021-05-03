package com.walvarado.unsplashtest.view

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.walvarado.unsplashtest.Utils
import com.walvarado.unsplashtest.databinding.ItemListImageBinding
import com.walvarado.unsplashtest.model.Photo

class PhotosAdapter(
    private val context: Context,
    private val photos: ArrayList<Photo>,
    private val itemClickListener: ItemClickListener
) : RecyclerView.Adapter<PhotosAdapter.PhotosViewHolder>() {

    inner class PhotosViewHolder(val itemListImageBinding: ItemListImageBinding) :
        RecyclerView.ViewHolder(itemListImageBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotosViewHolder {
        val itemListImageBinding = ItemListImageBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

        return PhotosViewHolder(itemListImageBinding)
    }

    override fun getItemCount(): Int {
        return photos.size
    }

    override fun onBindViewHolder(holder: PhotosViewHolder, position: Int) {
        with(holder) {
            with(photos[position]) {
                itemListImageBinding.tvUsername.text = user!!.username
                itemListImageBinding.tvLikes.text = user!!.totalLikes.toString()

                Utils.buildGradle(context, urls!!.full.toString(), itemListImageBinding.ivItem)
                Utils.buildGradle(
                    context,
                    user!!.profileImage!!.medium.toString(),
                    itemListImageBinding.ivProfile
                )

                itemListImageBinding.root.setOnClickListener {
                    itemClickListener.itemClick(id!!)
                }

                itemListImageBinding.ivFavorite.setOnClickListener {
                    itemClickListener.statusFavoriteChange(this, position)
                }
            }
        }
    }

    interface ItemClickListener {
        fun itemClick(id: String)
        fun statusFavoriteChange(photo: Photo, position: Int)
    }
}


