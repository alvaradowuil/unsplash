package com.walvarado.unsplashtest.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.walvarado.unsplashtest.Utils
import com.walvarado.unsplashtest.databinding.ActivityUserDetailBinding
import com.walvarado.unsplashtest.model.UnsplashUser
import com.walvarado.unsplashtest.viewmodel.UserDetailViewModel

class UserDetailActivity : AppCompatActivity() {
    companion object {
        const val EXTRA_LINK_SELF = "linkSelf"
    }

    private lateinit var binding: ActivityUserDetailBinding
    private lateinit var viewModel: UserDetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this).get(UserDetailViewModel::class.java)

        binding = ActivityUserDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val linkSelf = intent.extras!!.get(EXTRA_LINK_SELF).toString()
        viewModel.getUser(linkSelf)
        viewModel.userDetail.observe(this, Observer { user ->
            showDetail(user)
        })
    }

    private fun showDetail(user: UnsplashUser) {
        Utils.buildGradle(applicationContext, user.profileImage!!.large.toString(), binding.ivPhoto)
        binding.tvUsername.text = user.username
        binding.tvName.text = user.name
        binding.tvCollection.text = user.totalCollections.toString()
        binding.tvLikes.text = user.totalLikes.toString()
        binding.tvPhotos.text = user.totalPhotos.toString()
        binding.tvBiography.text = user.bio
    }
}