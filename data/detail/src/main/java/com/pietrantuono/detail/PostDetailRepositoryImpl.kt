package com.pietrantuono.detail

import com.pietrantuono.persistence.DatabaseClient
import com.pietrantuono.posts.PostDetailRepository
import javax.inject.Inject

class PostDetailRepositoryImpl @Inject constructor(
    private val databaseClient: DatabaseClient,
) : PostDetailRepository {
    override suspend fun getPostDetail(id: String) = databaseClient.getPostById(id)
}