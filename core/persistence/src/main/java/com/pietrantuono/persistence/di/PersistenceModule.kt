package com.pietrantuono.persistence.di

import android.content.Context
import androidx.room.Room
import com.pietrantuono.persistence.DatabaseClient
import com.pietrantuono.persistence.DatabaseClientImpl
import com.pietrantuono.persistence.ImageToPersistedImageEntityMapper
import com.pietrantuono.persistence.PostToPersistedPostEntityMapper
import com.pietrantuono.persistence.PostWithImagesEntityToPostMapper
import com.pietrantuono.persistence.RedditDatabase
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext

@Module
@InstallIn(ViewModelComponent::class)
interface PersistenceModule {

    companion object {
        private const val DATABASE_NAME = "database-reddit"

        @Provides
        fun bindDatabaseClient(@ApplicationContext applicationContext: Context): DatabaseClient {
            val database = Room.databaseBuilder(
                context = applicationContext,
                klass = RedditDatabase::class.java,
                name = DATABASE_NAME
            ).build()
            return DatabaseClientImpl(
                redditDatabase = database,
                redditDao = database.redditDao(),
                postToPersistedPostEntityMapper = PostToPersistedPostEntityMapper(),
                imageToPersistedImageEntityMapper = ImageToPersistedImageEntityMapper(),
                postWithImagesEntityToPostMapper = PostWithImagesEntityToPostMapper()
            )
        }
    }
}
