package com.example.android.devbyteviewer.database

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface VideoDao {

    @Query("select * from databasevideo")
    fun getVideos(): LiveData<List<DatabaseVideo>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg videos: DatabaseVideo)
}



@Database(entities = [DatabaseVideo::class], version = 1)
abstract class Videosdatabase: RoomDatabase() {
    abstract val videoDao: VideoDao
}

private lateinit var INSTANCE: Videosdatabase

fun getDatabase(context: Context): Videosdatabase {
    synchronized(Videosdatabase::class.java) {
        if (!::INSTANCE.isInitialized)
            INSTANCE = Room.databaseBuilder(context.applicationContext, Videosdatabase::class.java, "videos").build()
    }
    return INSTANCE
}