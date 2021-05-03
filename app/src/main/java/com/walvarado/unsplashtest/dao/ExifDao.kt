package com.walvarado.unsplashtest.dao

import androidx.room.Dao
import androidx.room.Insert
import com.walvarado.unsplashtest.model.db.ExifDb

@Dao
interface ExifDao {
    @Insert
    suspend fun insert(exifDb: ExifDb)
}