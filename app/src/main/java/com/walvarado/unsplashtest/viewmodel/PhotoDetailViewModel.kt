package com.walvarado.unsplashtest.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.walvarado.unsplashtest.api.APIService
import com.walvarado.unsplashtest.BuildConfig
import com.walvarado.unsplashtest.api.ClientHttp
import com.walvarado.unsplashtest.model.UnsplashPhoto
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PhotoDetailViewModel : ViewModel() {

    val unsplashPhoto = MutableLiveData<UnsplashPhoto>()

    fun getPhotos(photoId: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val call = ClientHttp.getRetrofit(BuildConfig.BASE_URL).create(APIService::class.java).getPhoto(
                BuildConfig.ACCESS_KEY,
                "photos/$photoId/",
                BuildConfig.CLIENT_ID
            )

            if (call.isSuccessful) {
                val photo = call.body()
                unsplashPhoto.postValue(photo!!)
            } else {
                // TODO: 4/30/2021 Show error
            }
        }
    }
}