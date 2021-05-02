package com.walvarado.unsplashtest.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.walvarado.unsplashtest.BuildConfig
import com.walvarado.unsplashtest.api.APIService
import com.walvarado.unsplashtest.api.ClientHttp
import com.walvarado.unsplashtest.model.UnsplashPhoto
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class UnsplashViewModel : ViewModel() {

    private var page: Int = 1

    val unsplashPhotos = MutableLiveData<List<UnsplashPhoto>>()

    fun getPhotos() {
        CoroutineScope(Dispatchers.IO).launch {
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
                // TODO: 4/30/2021 Show error
            }
        }
    }
}