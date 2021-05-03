package com.walvarado.unsplashtest.view

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import android.view.View.*
import android.widget.ImageView
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.walvarado.unsplashtest.R
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

    private var searchView: SearchView? = null
    private var queryTextListener: SearchView.OnQueryTextListener? = null

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

        setHasOptionsMenu(true)

        return binding.root
    }

    private fun initRecyclerView() {
        binding.rvPhotos.layoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        photosAdapter = PhotosAdapter(context!!, unsplashPhotos, this)
        binding.rvPhotos.adapter = photosAdapter
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
                    viewModel.searchPhotos(activity!!, query!!)
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    Log.d("WUIL", "onQueryTextChange newText: $newText")
                    return true
                }
            }
            searchView!!.setOnQueryTextListener(queryTextListener)

            val closeButton = searchView!!.findViewById<ImageView>(R.id.search_close_btn)
            closeButton.setOnClickListener {
                viewModel!!.getPhotos(context!!)
                searchView!!.onActionViewCollapsed()
            }
        }

        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_search ->                 // Not implemented here
                return false
            else -> {
            }
        }
        searchView!!.setOnQueryTextListener(queryTextListener)
        return super.onOptionsItemSelected(item)
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