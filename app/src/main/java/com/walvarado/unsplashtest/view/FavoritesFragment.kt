package com.walvarado.unsplashtest.view

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.walvarado.unsplashtest.databinding.FavoritesFragmentBinding
import com.walvarado.unsplashtest.model.Photo
import com.walvarado.unsplashtest.viewmodel.FavoritesViewModel

class FavoritesFragment : Fragment(), PhotosAdapter.ItemClickListener {

    companion object {
        fun newInstance() = FavoritesFragment()
    }

    private lateinit var binding: FavoritesFragmentBinding
    private lateinit var viewModel: FavoritesViewModel
    private lateinit var photosAdapter: PhotosAdapter

    private val unsplashPhotos = ArrayList<Photo>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FavoritesFragmentBinding.inflate(layoutInflater, container, false)
        viewModel = ViewModelProvider(this).get(FavoritesViewModel::class.java)

        initRecyclerView()
        viewModel.unsplashPhotos.observe(this, Observer { photos ->
            if (photos.isNotEmpty()) {
                binding.emptyLayout.root.visibility = GONE
                unsplashPhotos.clear()
                unsplashPhotos.addAll(photos)
                photosAdapter.notifyDataSetChanged()
            } else {
                binding.emptyLayout.root.visibility = VISIBLE
            }
        })

        viewModel.getPhotos(context!!)

        return binding.root
    }

    private fun initRecyclerView() {
        binding.rvPhotos.layoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        photosAdapter = PhotosAdapter(context!!, unsplashPhotos, this)
        binding.rvPhotos.adapter = photosAdapter
    }

    override fun itemClick(id: String) {
        val intent = Intent(activity, PhotoDetailActivity::class.java)
        intent.putExtra(PhotoDetailActivity.EXTRA_PHOTO_ID, id)
        startActivity(intent)
    }

    override fun statusFavoriteChange(photo: Photo, position: Int) {
        unsplashPhotos.removeAt(position)
        photosAdapter.notifyDataSetChanged()
        viewModel.deleteFavorite(context!!, photo.id!!)
    }
}