package com.walvarado.unsplashtest.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.walvarado.unsplashtest.BuildConfig
import com.walvarado.unsplashtest.UnsplashDb
import com.walvarado.unsplashtest.api.APIService
import com.walvarado.unsplashtest.api.ClientHttp
import com.walvarado.unsplashtest.api.RequestError
import com.walvarado.unsplashtest.model.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PhotoDetailViewModel : ViewModel() {

    val unsplashPhoto = MutableLiveData<Photo>()
    val requestError = MutableLiveData<String>()

    fun getPhotos(context: Context, photoId: String, isOnline: Boolean) {
        if (isOnline) {
            try {
                CoroutineScope(Dispatchers.IO).launch {
                    val call =
                        ClientHttp.getRetrofit(BuildConfig.BASE_URL).create(APIService::class.java)
                            .getPhoto(
                                BuildConfig.ACCESS_KEY,
                                "photos/$photoId/",
                                BuildConfig.CLIENT_ID
                            )

                    if (call.isSuccessful) {
                        val photo = call.body()
                        unsplashPhoto.postValue(photo!!)
                    } else {
                        requestError.postValue(RequestError.getByValue(call.code()).toString())
                    }
                }
            } catch (e: Throwable) {
                requestError.postValue(RequestError.getByValue(0).toString())
            }

        } else {
            CoroutineScope(Dispatchers.IO).launch {
                val dao = UnsplashDb.getInstance(context).photoDao
                val photoDb = dao.getById(photoId)

                var userLinks: UserLinks? = null
                var userProfileImages: UserProfileImage? = null
                var user: User? = null
                var urls: PhotoUrl? = null
                var exif: Exif? = null

                photoDb.user?.let { userDb ->
                    userDb.userLinksDb?.let { linksDb ->
                        userLinks = linksDb.convertToUserLinks()
                    }

                    userDb.userProfileImageDb?.let { profileImageDb ->
                        userProfileImages = profileImageDb.convertToProfileImage()
                    }

                    user = userDb.userDb.convertToUser(userLinks!!, userProfileImages!!)
                }

                photoDb.photoUrlDb?.let { photoUrlDb ->
                    urls = photoUrlDb.convertToPhotoUrl()
                }

                photoDb.exif?.let { exifDb ->
                    exif = exifDb.convertToExif()
                }

                var photo = photoDb.photo!!.convertToPhoto()
                photo.user = user
                photo.urls = urls
                photo.exif = exif

                unsplashPhoto.postValue(photo)
            }

        }
    }
}