package com.pietrantuono.persistence.entity

import androidx.room.Embedded
import androidx.room.Relation

class PostWithImagesEntity(
    @Embedded val post: PersistedPostEntity,
    @Relation(
        parentColumn = "kye",
        entityColumn = "postKey",
    )
    val images: List<PersistedImageEntity>,
)