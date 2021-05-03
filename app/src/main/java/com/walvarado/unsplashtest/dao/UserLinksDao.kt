package com.walvarado.unsplashtest.dao

import androidx.room.Dao
import androidx.room.Insert
import com.walvarado.unsplashtest.model.db.UserLinksDb

@Dao
interface UserLinksDao {
    @Insert
    suspend fun insert(userLinksDb: UserLinksDb)
}