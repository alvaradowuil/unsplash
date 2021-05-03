package com.walvarado.unsplashtest.view

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.*
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.ImageView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.walvarado.unsplashtest.R
import com.walvarado.unsplashtest.Utils
import com.walvarado.unsplashtest.databinding.UnsplashFragmentBinding
import com.walvarado.unsplashtest.model.Photo
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

    private var searchView: SearchView? = null
    private var queryTextListener: SearchView.OnQueryTextListener? = null

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = UnsplashFragmentBinding.inflate(layoutInflater, container, false)
        viewModel = ViewModelProvider(this).get(UnsplashViewModel::class.java)

        initRecyclerView()

        if (Utils.isOnline(activity!!)) {
            binding.connectionError.root.visibility = GONE
            viewModel.getPhotos()
        } else {
            binding.connectionError.root.visibility = VISIBLE
        }

        setObservers()

        setHasOptionsMenu(true)

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_search, menu);

        val searchItem = menu.findItem(R.id.action_search)
        val searchManager =
            activity!!.getSystemService(Context.SEARCH_SERVICE) as SearchManager

        if (searchItem != null) {
            searchView = searchItem.actionView as SearchView
        }

        if (searchView != null) {
            searchView!!.setSearchableInfo(searchManager.getSearchableInfo(activity!!.componentName))
            queryTextListener = object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    unsplashPhotos.clear()
                    photosAdapter.notifyDataSetChanged()
                    viewModel.searchPhotos(query!!)
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    return true
                }
            }
            searchView!!.setOnQueryTextListener(queryTextListener)

            val closeButton = searchView!!.findViewById<ImageView>(R.id.search_close_btn)
            closeButton.setOnClickListener {
                viewModel!!.getPhotos()
                searchView!!.onActionViewCollapsed()
            }
        }

        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_search ->
                return false
            else -> {
            }
        }
        searchView!!.setOnQueryTextListener(queryTextListener)
        return super.onOptionsItemSelected(item)
    }

    private fun setObservers() {
        viewModel.unsplashPhotos.observe(this, Observer { photos ->
            unsplashPhotos.addAll(photos)
            photosAdapter.notifyDataSetChanged()
        })

        viewModel.showProgress.observe(this, Observer { showProgress ->
            binding.progress.visibility = if (showProgress) VISIBLE else GONE
        })

        viewModel.requestError.observe(this, Observer { requestError ->
            Toast.makeText(activity!!, requestError, Toast.LENGTH_LONG).show()
        })
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