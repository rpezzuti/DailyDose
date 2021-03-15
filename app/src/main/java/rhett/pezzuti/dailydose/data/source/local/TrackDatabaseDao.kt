package rhett.pezzuti.dailydose.data.source.local

import androidx.lifecycle.LiveData
import androidx.room.*
import rhett.pezzuti.dailydose.data.DatabaseTrack

@Dao
interface TrackDatabaseDao {

    /** All Tracks **/
    @Query ("SELECT * FROM track_table ORDER BY track_timestamp DESC")
    fun observeAllTracks() : LiveData<List<DatabaseTrack>>

    @Query("SELECT * FROM track_table ORDER BY track_timestamp DESC")
    fun getAllTracks(): List<DatabaseTrack>



    /** Recent Tracks **/
    @Query("SELECT * FROM track_table ORDER BY track_timestamp DESC LIMIT 5")
    fun observeRecent(): LiveData<List<DatabaseTrack>>

    @Query("SELECT * FROM track_table ORDER BY track_timestamp DESC LIMIT 5")
    fun getRecent(): List<DatabaseTrack>





    /** Favorite Tracks **/
    @Query ("SELECT * FROM track_table WHERE is_favorite = :favorite ORDER BY track_timestamp DESC")
    fun observeFavorites(favorite: Boolean): LiveData<List<DatabaseTrack>>

    @Query ("SELECT * FROM track_table WHERE is_favorite = :favorite ORDER BY track_timestamp DESC")
    fun getFavorites(favorite: Boolean): List<DatabaseTrack>




    /** Tracks by Genre **/
    @Query ("SELECT * FROM track_table WHERE track_genre = :genre")
    fun observeGenre(genre: String): LiveData<List<DatabaseTrack>>

    @Query ("SELECT * FROM track_table WHERE track_genre = :genre")
    fun getGenre(genre: String): List<DatabaseTrack>





    /** Single Tracks **/
    @Query("SELECT * FROM track_table WHERE track_timestamp = :trackId")
    fun observeTrack(trackId: Long): LiveData<DatabaseTrack>

    @Query("SELECT * FROM track_table WHERE track_timestamp = :trackId")
    fun getTrack(trackId: Long): DatabaseTrack



    @Insert
    fun insert(track: DatabaseTrack)

    // varargs is a way in kotlin to pass an unknown amount of arguments.
    // Is actually just passing an array under the hood
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg tracks: DatabaseTrack)


    @Update
    fun update(track: DatabaseTrack)


    @Query ("SELECT * FROM track_table WHERE is_favorite = :favorite")
    fun getFavoritesToSave(favorite: Boolean): List<DatabaseTrack>


    @Query ("SELECT * FROM track_table WHERE track_genre = :genre")
    fun getAllFromGenre(genre: String): LiveData<List<DatabaseTrack>>


    /** Clear All **/
    @Query ("DELETE FROM track_table")
    fun clearAll()
}