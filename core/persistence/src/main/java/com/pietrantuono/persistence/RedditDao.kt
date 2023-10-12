package com.pietrantuono.persistence

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.IGNORE
import androidx.room.Query
import androidx.room.Transaction
import com.pietrantuono.persistence.entity.PersistedImageEntity
import com.pietrantuono.persistence.entity.PersistedPostEntity
import com.pietrantuono.persistence.entity.PostWithImagesEntity

@Dao
interface RedditDao {

    @Insert(entity = PersistedPostEntity::class, onConflict = IGNORE)
    suspend fun insert(post: PersistedPostEntity): Long

    @Insert(onConflict = IGNORE)
    suspend fun insert(post: PersistedImageEntity): Long

    @Transaction
    @Query("SELECT * FROM persistedpostentity  ORDER BY created_utc DESC")
    suspend fun getPosts(): List<PostWithImagesEntity>

}
