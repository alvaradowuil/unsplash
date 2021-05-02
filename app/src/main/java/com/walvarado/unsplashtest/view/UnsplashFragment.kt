package com.walvarado.unsplashtest.view

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.walvarado.unsplashtest.databinding.UnsplashFragmentBinding
import com.walvarado.unsplashtest.model.UnsplashPhoto
import com.walvarado.unsplashtest.viewmodel.UnsplashViewModel

class UnsplashFragment : Fragment(), PhotosAdapter.ItemClickListener {
    companion object {
        fun newInstance() = UnsplashFragment()
    }

    private lateinit var binding: UnsplashFragmentBinding
    private lateinit var viewModel: UnsplashViewModel
    private lateinit var photosAdapter: PhotosAdapter
    private val unsplashPhotos = ArrayList<UnsplashPhoto>()
    private var layoutManager: LinearLayoutManager? = null
    private val lastVisibleItemPosition: Int
        get() = layoutManager!!.findLastVisibleItemPosition()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = UnsplashFragmentBinding.inflate(layoutInflater, container, false)
        viewModel = ViewModelProvider(this).get(UnsplashViewModel::class.java)

        initRecyclerView()
        viewModel.getPhotos()
        return binding.root
    }

    private fun initRecyclerView() {
        layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        binding.rvPhotos.layoutManager = layoutManager
        photosAdapter = PhotosAdapter(context!!, unsplashPhotos, this)
        binding.rvPhotos.adapter = photosAdapter

        viewModel.unsplashPhotos.observe(this, Observer { photos ->
            unsplashPhotos.addAll(photos)
            photosAdapter.notifyDataSetChanged()
        })

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

    override fun setFav(photo: UnsplashPhoto) {
        TODO("Not yet implemented")
    }
}