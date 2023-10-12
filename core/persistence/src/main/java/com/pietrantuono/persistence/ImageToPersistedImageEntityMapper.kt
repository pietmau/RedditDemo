package com.pietrantuono.persistence

import com.pietrantuono.common.Mapper
import com.pietrantuono.model.reddit.Image
import com.pietrantuono.persistence.entity.PersistedImageEntity
import javax.inject.Inject

class ImageToPersistedImageEntityMapper @Inject constructor() : Mapper<Image, PersistedImageEntity> {

    override fun map(input: Image) = PersistedImageEntity(
        url = input.url,
        width = input.width,
        height = input.height
    )
}
