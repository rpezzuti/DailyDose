package rhett.pezzuti.dailydose.data.local

import androidx.lifecycle.LiveData
import androidx.room.*
import rhett.pezzuti.dailydose.data.DatabaseTrack

@Dao
interface TrackDatabaseDao {

    @Insert
    fun insert(track: DatabaseTrack)

    // varargs is a way in kotlin to pass an unknown amount of arguments.
    // Is actually just passing an array under the hood
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg tracks: DatabaseTrack)

    @Query("SELECT * FROM track_table WHERE url = :url")
    fun getTrack(url: String): DatabaseTrack

    @Update
    fun update(track: DatabaseTrack)

    @Query ("SELECT * FROM track_table ORDER BY track_timestamp DESC")
    fun getAllTracks() : LiveData<List<DatabaseTrack>>

    @Query ("SELECT * FROM track_table WHERE is_favorite = :favorite")
    fun getFavorites(favorite: Boolean): LiveData<List<DatabaseTrack>>

    @Query ("SELECT * FROM track_table WHERE is_favorite = :favorite")
    fun getFavoritesToSave(favorite: Boolean): List<DatabaseTrack>

    @Query("SELECT * FROM track_table ORDER BY track_timestamp DESC LIMIT 5")
    fun getRecentTracks(): LiveData<List<DatabaseTrack>>

    @Query ("SELECT * FROM track_table WHERE track_genre = :genre")
    fun getAllFromGenre(genre: String): LiveData<List<DatabaseTrack>>

    @Query ("DELETE FROM track_table")
    fun clearAll()
}