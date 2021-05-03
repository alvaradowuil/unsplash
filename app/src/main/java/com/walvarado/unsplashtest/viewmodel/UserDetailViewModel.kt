package com.walvarado.unsplashtest.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.walvarado.unsplashtest.BuildConfig
import com.walvarado.unsplashtest.api.APIService
import com.walvarado.unsplashtest.api.ClientHttp
import com.walvarado.unsplashtest.api.RequestError
import com.walvarado.unsplashtest.model.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UserDetailViewModel : ViewModel() {

    val userDetail = MutableLiveData<User>()
    val requestError = MutableLiveData<String>()

    fun getUser(linkSelf: String) {
        try {
            CoroutineScope(Dispatchers.IO).launch {
                val call = ClientHttp.getRetrofit("$linkSelf/").create(APIService::class.java)
                    .getUserByLinkSelf(
                        BuildConfig.ACCESS_KEY,
                        "",
                        BuildConfig.CLIENT_ID
                    )

                if (call.isSuccessful) {
                    val user = call.body()
                    userDetail.postValue(user!!)
                } else {
                    requestError.postValue(RequestError.getByValue(call.code()).toString())
                }
            }
        } catch (e: Throwable) {
            requestError.postValue(RequestError.getByValue(0).toString())
        }

    }
}