package com.pietrantuono.persistence.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity()
data class PersistedPostEntity(
    @PrimaryKey(autoGenerate = true)
    val key: Long = 0,
    @ColumnInfo(name = "id")
    val id: String,
    @ColumnInfo(name = "score")
    val score: Int? = null,
    @ColumnInfo(name = "kind")
    val kind: String? = null,
    @ColumnInfo(name = "subreddit")
    val subreddit: String? = null,
    @ColumnInfo(name = "thumbnail")
    val thumbnail: String? = null,
    @ColumnInfo(name = "title")
    val title: String? = null,
    @ColumnInfo(name = "ups")
    val ups: Int? = null,
    @ColumnInfo(name = "created")
    val created: Long? = null,
    @ColumnInfo(name = "subreddit_id")
    val subredditId: String? = null,
    @ColumnInfo(name = "author")
    val author: String? = null,
    @ColumnInfo(name = "num_comments")
    val numComments: Int? = null,
    @ColumnInfo(name = "permalink")
    val permalink: String? = null,
    @ColumnInfo(name = "url")
    val url: String? = null,
    @ColumnInfo(name = "created_utc")
    val createdUtc: Long? = null,
)