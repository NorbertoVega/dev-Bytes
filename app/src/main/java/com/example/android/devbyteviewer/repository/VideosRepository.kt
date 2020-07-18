package com.example.android.devbyteviewer.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.example.android.devbyteviewer.database.DatabaseVideo
import com.example.android.devbyteviewer.database.Videosdatabase
import com.example.android.devbyteviewer.database.asDomainModel
import com.example.android.devbyteviewer.domain.Video
import com.example.android.devbyteviewer.network.Network
import com.example.android.devbyteviewer.network.NetworkVideo
import com.example.android.devbyteviewer.network.NetworkVideoContainer
import com.example.android.devbyteviewer.network.asDatabaseModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class VideosRepository(private val database: Videosdatabase) {

    val videos: LiveData<List<Video>> = Transformations.map(database.videoDao.getVideos()){
        it.asDomainModel()
    }

    suspend fun refreshVideos() {
        withContext(Dispatchers.IO) {
            val playlist = Network.devbytes?.getPlaylist()!!.await()
            database.videoDao.insertAll(*playlist.asDatabaseModel() )
        }
    }
}