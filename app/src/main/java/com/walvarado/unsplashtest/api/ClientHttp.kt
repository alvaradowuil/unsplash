package com.walvarado.unsplashtest.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ClientHttp {
    companion object {
        fun getRetrofit(baseUrl: String): Retrofit {
            return Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
    }
}