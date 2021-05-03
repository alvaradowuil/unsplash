package com.walvarado.unsplashtest.view

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.walvarado.unsplashtest.Utils
import com.walvarado.unsplashtest.databinding.UnsplashFragmentBinding
import com.walvarado.unsplashtest.model.Photo
import com.walvarado.unsplashtest.model.db.PhotoDb
import com.walvarado.unsplashtest.viewmodel.UnsplashViewModel

class UnsplashFragment : Fragment(), PhotosAdapter.ItemClickListener {
    companion object {
        fun newInstance() = UnsplashFragment()
    }

    private lateinit var binding: UnsplashFragmentBinding
    private lateinit var viewModel: UnsplashViewModel
    private lateinit var photosAdapter: PhotosAdapter
    private val unsplashPhotos = ArrayList<Photo>()
    private var layoutManager: LinearLayoutManager? = null
    private val lastVisibleItemPosition: Int
        get() = layoutManager!!.findLastVisibleItemPosition()

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = UnsplashFragmentBinding.inflate(layoutInflater, container, false)
        viewModel = ViewModelProvider(this).get(UnsplashViewModel::class.java)

        initRecyclerView()

        if (Utils.isOnline(activity!!)){
            binding.connectionError.root.visibility = GONE
            viewModel.getPhotos()
        } else {
            binding.connectionError.root.visibility = VISIBLE
        }

        viewModel.unsplashPhotos.observe(this, Observer { photos ->
            unsplashPhotos.addAll(photos)
            photosAdapter.notifyDataSetChanged()
        })

        viewModel.showProgress.observe(this, Observer { showProgress ->
            binding.progress.visibility = if (showProgress) VISIBLE else GONE
        })

        return binding.root
    }

    private fun initRecyclerView() {
        layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        binding.rvPhotos.layoutManager = layoutManager
        photosAdapter = PhotosAdapter(context!!, unsplashPhotos, this)
        binding.rvPhotos.adapter = photosAdapter

        setRecyclerViewScrollListener()
    }

    private fun setRecyclerViewScrollListener() {
        binding.rvPhotos.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                val totalItemCount = recyclerView.layoutManager!!.itemCount
                if (totalItemCount == lastVisibleItemPosition + 1) {
                    viewModel.getPhotos()
                }
            }
        })
    }

    override fun itemClick(id: String) {
        val intent = Intent(activity, PhotoDetailActivity::class.java)
        intent.putExtra(PhotoDetailActivity.EXTRA_PHOTO_ID, id)
        startActivity(intent)
    }

    override fun statusFavoriteChange(photo: Photo, position: Int) {
        viewModel.savePhoto(activity!!.applicationContext, photo)
        Toast.makeText(activity!!, "Guardando favorito", Toast.LENGTH_LONG).show()
    }
}