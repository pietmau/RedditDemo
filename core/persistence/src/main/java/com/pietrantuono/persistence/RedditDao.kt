package com.pietrantuono.persistence

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.IGNORE
import androidx.room.Query
import com.pietrantuono.persistence.entity.PersistedPostEntity

@Dao
interface RedditDao {

    @Insert(entity = PersistedPostEntity::class, onConflict = IGNORE)
    fun insertAll(post: List<PersistedPostEntity>)

    @Query("SELECT * FROM persistedpostentity  ORDER BY created_utc DESC LIMIT :limit")
    fun getPosts(limit: Int): List<PersistedPostEntity>

    @Query("SELECT * FROM persistedpostentity  WHERE id = :id")
    fun getPostById(id: String): PersistedPostEntity?
}
