package com.pietrantuono.persistence

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.IGNORE
import androidx.room.Query
import com.pietrantuono.persistence.entity.PersistedPostEntity

@Dao
interface RedditDao {

    @Insert(entity = PersistedPostEntity::class, onConflict = IGNORE)
    suspend fun insert(post: PersistedPostEntity): Long

    @Query("SELECT * FROM persistedpostentity  ORDER BY created_utc DESC")
    suspend fun getPosts(): List<PersistedPostEntity>

    @Query("SELECT * FROM persistedpostentity  WHERE id = :id")
    suspend fun getPostById(id: String): PersistedPostEntity?
}
