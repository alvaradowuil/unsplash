package com.walvarado.unsplashtest.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.walvarado.unsplashtest.model.db.PhotoAndUser
import com.walvarado.unsplashtest.model.db.PhotoDb

@Dao
interface PhotoDao {
    @Query("SELECT * FROM photo")
    suspend fun getAll(): List<PhotoAndUser>

    @Query("SELECT * FROM photo WHERE id = :photoId")
    suspend fun getById(photoId: String): PhotoAndUser

    @Query("SELECT * FROM photo WHERE description LIKE :query")
    suspend fun searchPhoto(query: String): List<PhotoAndUser>

    @Insert
    suspend fun insert(photoDb: PhotoDb)

    @Query("DELETE FROM photo WHERE id = :photoId")
    suspend fun delete(photoId: String)
}