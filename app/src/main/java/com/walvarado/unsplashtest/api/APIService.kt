package com.walvarado.unsplashtest.api

import com.walvarado.unsplashtest.model.Photo
import com.walvarado.unsplashtest.model.User
import com.walvarado.unsplashtest.model.db.PhotoDb
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query
import retrofit2.http.Url

interface APIService {
    @GET
    suspend fun getPhotos(
        @Header("Authorization") accessKey: String,
        @Url url: String,
        @Query("client_id") clientId: String
    ): Response<ArrayList<Photo>>

    @GET
    suspend fun getPhoto(
        @Header("Authorization") accessKey: String,
        @Url url: String,
        @Query("client_id") clientId: String
    ): Response<Photo>

    @GET
    suspend fun getUserByLinkSelf(
        @Header("Authorization") accessKey: String,
        @Url url: String,
        @Query("client_id") clientId: String
    ): Response<User>
}