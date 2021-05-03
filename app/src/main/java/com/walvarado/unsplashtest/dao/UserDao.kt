package com.walvarado.unsplashtest.dao

import androidx.room.Dao
import androidx.room.Insert
import com.walvarado.unsplashtest.model.db.UserDb

@Dao
interface UserDao {
    @Insert
    suspend fun insert(userDb: UserDb)
}