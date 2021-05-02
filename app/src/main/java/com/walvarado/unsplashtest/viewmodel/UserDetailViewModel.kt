package com.walvarado.unsplashtest.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.walvarado.unsplashtest.BuildConfig
import com.walvarado.unsplashtest.api.APIService
import com.walvarado.unsplashtest.api.ClientHttp
import com.walvarado.unsplashtest.model.UnsplashUser
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class UserDetailViewModel: ViewModel() {

    val userDetail = MutableLiveData<UnsplashUser>()

    fun getUser(linkSelf: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val call = ClientHttp.getRetrofit("$linkSelf/").create(APIService::class.java).getUserByLinkSelf(
                BuildConfig.ACCESS_KEY,
                "",
                BuildConfig.CLIENT_ID
            )

            if (call.isSuccessful) {
                val user = call.body()
                userDetail.postValue(user!!)
            } else {
                // TODO: 4/30/2021 Show error
            }
        }
    }
}