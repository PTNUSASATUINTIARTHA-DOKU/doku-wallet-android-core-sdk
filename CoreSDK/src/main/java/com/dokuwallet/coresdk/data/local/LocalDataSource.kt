package com.dokuwallet.coresdk.data.local

import com.dokuwallet.coresdk.data.local.db.AppDao
import com.dokuwallet.coresdk.data.local.entities.ExamplePostEntity
import kotlinx.coroutines.flow.Flow

internal class LocalDataSource(private val appDao: AppDao) {
    // === EXAMPLE ===
    suspend fun insertExamplePosts(posts: List<ExamplePostEntity>) =
        appDao.insertExamplePosts(posts)

    fun exampleGetPosts(): Flow<List<ExamplePostEntity>> = appDao.exampleGetPosts()

    fun exampleGetPostByID(idPost: Int): Flow<ExamplePostEntity> = appDao.exampleGetPostByID(idPost)
}