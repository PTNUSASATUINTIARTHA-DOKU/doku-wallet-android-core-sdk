package com.dokuwallet.coresdk.data.local.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dokuwallet.coresdk.data.local.entities.ExamplePostEntity
import kotlinx.coroutines.flow.Flow

@Dao
internal interface AppDao {
    // === EXAMPLE ===
    @Insert(onConflict = OnConflictStrategy.REPLACE, entity = ExamplePostEntity::class)
    suspend fun insertExamplePosts(posts: List<ExamplePostEntity>)

    @Query("SELECT * FROM example_post")
    fun exampleGetPosts(): Flow<List<ExamplePostEntity>>

    @Query("SELECT * FROM example_post WHERE id = :idPost")
    fun exampleGetPostByID(idPost: Int): Flow<ExamplePostEntity>
}