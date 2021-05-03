package com.walvarado.unsplashtest.dao

import androidx.room.Dao
import androidx.room.Insert
import com.walvarado.unsplashtest.model.db.UserProfileImageDb

@Dao
interface UserProfileImageDao {
    @Insert
    suspend fun insert(userProfileImageDb: UserProfileImageDb)
}