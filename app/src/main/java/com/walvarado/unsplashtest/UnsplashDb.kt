package com.walvarado.unsplashtest

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.walvarado.unsplashtest.dao.*
import com.walvarado.unsplashtest.model.db.*

@Database(
    entities = [
        PhotoDb::class,
        ExifDb::class,
        PhotoUrlDb::class,
        UserDb::class,
        UserLinksDb::class,
        UserProfileImageDb::class
    ],
    version = 1
)
abstract class UnsplashDb : RoomDatabase() {
    abstract val photoDao: PhotoDao
    abstract val userDao: UserDao
    abstract val exifDao: ExifDao
    abstract val photoUrlDao: PhotoUrlDao
    abstract val userLinksDao: UserLinksDao
    abstract val userProfileImageDao: UserProfileImageDao

    companion object {
        @Volatile
        private var INSTANCE: UnsplashDb? = null

        fun getInstance(context: Context): UnsplashDb {
            synchronized(this) {
                return INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    UnsplashDb::class.java,
                    "unsplashdb"
                ).build()
            }
        }
    }
}