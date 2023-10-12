package com.pietrantuono.persistence.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    foreignKeys = [ForeignKey(
        entity = PersistedPostEntity::class,
        parentColumns = ["key"],
        childColumns = ["postKey"],
        onDelete = ForeignKey.CASCADE
    )],
    indices = [Index(value = ["postKey"])]
)
data class PersistedImageEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "url")
    val url: String? = null,
    @ColumnInfo(name = "width")
    val width: Int? = null,
    @ColumnInfo(name = "height")
    val height: Int? = null,
    @ColumnInfo(name = "postKey")
    val postKey: Long? = null,
)