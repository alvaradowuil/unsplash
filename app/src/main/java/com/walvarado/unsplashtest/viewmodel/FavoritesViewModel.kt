package com.walvarado.unsplashtest.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.walvarado.unsplashtest.UnsplashDb
import com.walvarado.unsplashtest.model.*
import com.walvarado.unsplashtest.model.db.PhotoAndUser
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FavoritesViewModel : ViewModel() {
    val unsplashPhotos = MutableLiveData<List<Photo>>()

    fun getPhotos(context: Context) {
        val tempPhotos = ArrayList<Photo>()
        CoroutineScope(Dispatchers.IO).launch {
            val dao = UnsplashDb.getInstance(context).photoDao

            val photos = dao.getAll()

            photos.forEach { photoAndUser ->
                val photo = convertToPhoto(photoAndUser)
                tempPhotos.add(photo)
            }
            unsplashPhotos.postValue(tempPhotos)
        }
    }

    fun searchPhotos(context: Context, query: String) {
        val tempPhotos = ArrayList<Photo>()
        CoroutineScope(Dispatchers.IO).launch {
            val dao = UnsplashDb.getInstance(context).photoDao

            val photos = dao.searchPhoto("%$query%")

            photos.forEach { photoAndUser ->
                val photo = convertToPhoto(photoAndUser)
                tempPhotos.add(photo)
            }
            unsplashPhotos.postValue(tempPhotos)
        }
    }

    private fun convertToPhoto(photoAndUser: PhotoAndUser): Photo {
        var userLinks: UserLinks? = null
        var userProfileImages: UserProfileImage? = null
        var user: User? = null
        var urls: PhotoUrl? = null
        var exif: Exif? = null

        photoAndUser.user?.let { userDb ->
            userDb.userLinksDb?.let { linksDb ->
                userLinks = linksDb.convertToUserLinks()
            }

            userDb.userProfileImageDb?.let { profileImageDb ->
                userProfileImages = profileImageDb.convertToProfileImage()
            }

            user = userDb.userDb.convertToUser(userLinks!!, userProfileImages!!)
        }

        photoAndUser.photoUrlDb?.let { photoUrlDb ->
            urls = photoUrlDb.convertToPhotoUrl()
        }

        photoAndUser.exif?.let { exifDb ->
            exif = exifDb.convertToExif()
        }

        var photo = photoAndUser.photo!!.convertToPhoto()
        photo.user = user
        photo.urls = urls
        photo.exif = exif

        return photo
    }

    fun deleteFavorite(context: Context, photoId: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val dao = UnsplashDb.getInstance(context).photoDao

            dao.delete(photoId)
        }

        getPhotos(context)
    }
}