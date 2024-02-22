package com.dokuwallet.coresdk.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.dokuwallet.coresdk.data.local.entities.ExamplePostEntity

@Database(
    entities = [
        ExamplePostEntity::class, // === EXAMPLE === // TODO EXT: Don't Forget to add new Entity
    ],
    version = 1,
    exportSchema = false
)
internal abstract class AppDatabase : RoomDatabase() {
    abstract fun appDao(): AppDao
}