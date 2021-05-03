package com.walvarado.unsplashtest.view

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.walvarado.unsplashtest.Utils
import com.walvarado.unsplashtest.databinding.ActivityPhotoDetailBinding
import com.walvarado.unsplashtest.model.Photo
import com.walvarado.unsplashtest.viewmodel.PhotoDetailViewModel

class PhotoDetailActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_PHOTO_ID = "photoId"
    }

    private lateinit var binding: ActivityPhotoDetailBinding
    private lateinit var viewModel: PhotoDetailViewModel
    private var linkSelf: String? = null

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPhotoDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val photoId = intent.extras!!.get(EXTRA_PHOTO_ID).toString()

        viewModel = ViewModelProvider(this).get(PhotoDetailViewModel::class.java)

        setObservers()

        val isOnline = Utils.isOnline(this)
        viewModel.getPhotos(this, photoId, isOnline)

        binding.ivUser.setOnClickListener(View.OnClickListener {
            if (linkSelf != null && isOnline) {
                val intent = Intent(this, UserDetailActivity::class.java)
                intent.putExtra(UserDetailActivity.EXTRA_LINK_SELF, linkSelf)
                startActivity(intent)
            }
        })
    }

    private fun setObservers() {
        viewModel.unsplashPhoto.observe(this, Observer { photo ->
            showDetail(photo)
        })

        viewModel.requestError.observe(this, Observer { requestError ->
            Toast.makeText(this, requestError, Toast.LENGTH_LONG).show()
        })
    }

    private fun showDetail(photo: Photo) {
        Utils.buildGradle(this, photo.urls!!.full.toString(), binding.ivPhoto)
        binding.tvLikes.text = photo.likes.toString()
        binding.tvDownloads.text = photo.downloads.toString()
        binding.tvDescription.text = photo.description

        with(photo.user) {
            Utils.buildGradle(
                applicationContext,
                this!!.profileImage!!.medium.toString(),
                binding.ivUser
            )
            binding.tvUsername.text = this.username

            with(this.links) {
                linkSelf = this!!.self
            }
        }

        if (photo.exif != null) {
            with(photo.exif) {
                binding.tvCameraBrand.text = this?.make ?: ""
                binding.tvCameraModel.text = this?.model ?: ""
                binding.tvCameraConfig.text =
                    "${this?.aperture ?: ""} - ${this?.exposureTime?.take(4) ?: ""} - ${this?.focalLength?.take(
                        4
                    ) ?: ""}"
            }
        }
    }
}