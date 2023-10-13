package com.pietrantuono.persistence

import androidx.room.Database
import androidx.room.RoomDatabase
import com.pietrantuono.persistence.entity.PersistedPostEntity

@Database(entities = [PersistedPostEntity::class], version = 1, exportSchema = false)
abstract class RedditDatabase : RoomDatabase() {
    abstract fun redditDao(): RedditDao
}