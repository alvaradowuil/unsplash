package com.walvarado.unsplashtest.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.walvarado.unsplashtest.BuildConfig
import com.walvarado.unsplashtest.UnsplashDb
import com.walvarado.unsplashtest.api.APIService
import com.walvarado.unsplashtest.api.ClientHttp
import com.walvarado.unsplashtest.api.RequestError
import com.walvarado.unsplashtest.model.Photo
import com.walvarado.unsplashtest.model.db.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UnsplashViewModel : ViewModel() {

    private var page: Int = 1

    val unsplashPhotos = MutableLiveData<List<Photo>>()
    val showProgress = MutableLiveData<Boolean>()
    val requestError = MutableLiveData<String>()

    fun getPhotos() {
        showProgress.postValue(true)
        CoroutineScope(Dispatchers.IO).launch {
            try{
                val call = ClientHttp.getRetrofit(BuildConfig.BASE_URL).create(APIService::class.java).getPhotos(
                    BuildConfig.ACCESS_KEY,
                    "photos/?page=$page",
                    BuildConfig.CLIENT_ID
                )

                if (call.isSuccessful) {
                    val photos = call.body()
                    page += 1
                    unsplashPhotos.postValue(ArrayList())
                    unsplashPhotos.postValue(photos!!)
                } else {
                    requestError.postValue(RequestError.getByValue(call.code()).toString())
                }
                showProgress.postValue(false)
            } catch (e: Throwable) {
                requestError.postValue(RequestError.getByValue(0).toString())
                showProgress.postValue(false)
            }
        }
    }

    fun searchPhotos(query: String) {
        showProgress.postValue(true)
        CoroutineScope(Dispatchers.IO).launch {
            try{
                val call = ClientHttp.getRetrofit(BuildConfig.BASE_URL).create(APIService::class.java).searchPhoto(
                    BuildConfig.ACCESS_KEY,
                    "search/photos/?page=$page",
                    BuildConfig.CLIENT_ID,
                    query
                )

                if (call.isSuccessful) {
                    val searchPhotoResponse = call.body()
                    page += 1
                    unsplashPhotos.postValue(ArrayList())
                    unsplashPhotos.postValue(searchPhotoResponse!!.results)
                } else {
                    requestError.postValue(RequestError.getByValue(call.code()).toString())
                }
                showProgress.postValue(false)
            } catch (e: Throwable) {
                requestError.postValue(RequestError.getByValue(0).toString())
                showProgress.postValue(false)
            }
        }
    }

    fun savePhoto(context: Context, photo: Photo) {
        var exifDb: ExifDb? = null
        var userDb: UserDb? = null
        var photoUrlDb: PhotoUrlDb? = null
        var userLinksDb: UserLinksDb? = null
        var profileImageDb: UserProfileImageDb? = null
        var photoDb: PhotoDb? = null

        photo?.let {
            photoDb = it.convertToPhotoDb()
        }

        photo.urls?.let { urls ->
            photoUrlDb = urls.convertToPhotoUrlDb(photo.id!!)
        }

        photo.exif?.let { exif ->
            exifDb = exif.convertToExifDb(photo.id!!)
        }

        photo.user?.let { user ->
            userDb = user.convertToUserDb(photo.id!!)
            user.links?.let {  links ->
                userLinksDb = links.convertToUserLinksDb(user.id!!)
            }
            user.profileImage?.let {  profileImage ->
                profileImageDb = profileImage.convertToUserProfileImageDb(user.id!!)
            }
        }

        CoroutineScope(Dispatchers.IO).launch {
            photoDb?.let { photo ->
                val photoDao = UnsplashDb.getInstance(context).photoDao
                photoDao.insert(photo)

                exifDb?.let { exif ->
                    val exifDao = UnsplashDb.getInstance(context).exifDao
                    exifDao.insert(exif)
                }

                photoUrlDb?.let { photoUrl ->
                    val photoUrlDao = UnsplashDb.getInstance(context).photoUrlDao
                    photoUrlDao.insert(photoUrl)
                }

                userDb?.let { user ->
                    val userDao = UnsplashDb.getInstance(context).userDao
                    userDao.insert(user)

                    userLinksDb?.let { userLink ->
                        val userLinksDao = UnsplashDb.getInstance(context).userLinksDao
                        userLinksDao.insert(userLink)
                    }

                    profileImageDb?.let { profileImage ->
                        val userProfileImageDao = UnsplashDb.getInstance(context).userProfileImageDao
                        userProfileImageDao.insert(profileImage)
                    }
                }
            }
        }
    }

}