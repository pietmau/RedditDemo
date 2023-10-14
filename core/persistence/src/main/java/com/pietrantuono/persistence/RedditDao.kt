package com.pietrantuono.persistence

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.IGNORE
import androidx.room.Query
import com.pietrantuono.persistence.entity.PersistedPostEntity

@Dao
interface RedditDao {

    @Insert(entity = PersistedPostEntity::class, onConflict = IGNORE)
    suspend fun insertAll(post: List<PersistedPostEntity>)

    @Query("SELECT * FROM persistedpostentity  ORDER BY created_utc DESC LIMIT :limit")
    suspend fun getPosts(limit: Int): List<PersistedPostEntity>

    @Query("SELECT * FROM persistedpostentity  WHERE id = :id")
    suspend fun getPostById(id: String): PersistedPostEntity?
}
