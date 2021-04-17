package rhett.pezzuti.dailydose.data.source.local

import androidx.lifecycle.LiveData
import androidx.room.*
import rhett.pezzuti.dailydose.data.DatabaseTrack

@Dao
interface TrackDatabaseDao {




    /**
     * Returns LiveData of all tracks marked as 'favorite' in descending order by timestamp.
     *
     * @param favorite Boolean identifier of marked 'favorited' tracks.
     */
    @Query ("SELECT * FROM track_table WHERE is_favorite = :favorite ORDER BY track_timestamp DESC")
    fun observeFavorites(favorite: Boolean): LiveData<List<DatabaseTrack>>

    /**
     * Returns all tracks marked as 'favorite' in descending order by timestamp.
     *
     * @param favorite Boolean identifier of marked 'favorited' tracks.
     */
    @Query ("SELECT * FROM track_table WHERE is_favorite = :favorite ORDER BY track_timestamp DESC")
    fun getFavorites(favorite: Boolean): List<DatabaseTrack>




    /**
     * Returns LiveData of the most recent 5 tracks.
     */
    @Query("SELECT * FROM track_table ORDER BY track_timestamp DESC LIMIT 5")
    fun observeRecent(): LiveData<List<DatabaseTrack>>

    /**
     * Returns the most recent 5 tracks.
     */
    @Query("SELECT * FROM track_table ORDER BY track_timestamp DESC LIMIT 5")
    fun getRecent(): List<DatabaseTrack>





    /**
     * Returns LiveData of all tracks of specified genre.
     *
     * @param genre The desired genre to be queried from the database.
     */
    @Query ("SELECT * FROM track_table WHERE track_genre = :genre")
    fun observeGenre(genre: String): LiveData<List<DatabaseTrack>>

    /**
     * Returns all tracks of specified genre.
     *
     * @param genre The desired genre to be queried from the database.
     */
    @Query ("SELECT * FROM track_table WHERE track_genre = :genre")
    fun getGenre(genre: String): List<DatabaseTrack>








    /**
     * Inserts a single track into the database.
     *
     * @param track Desired track to be inserted.
     */
    @Insert
    fun addTrack(track: DatabaseTrack)

    /**
     * Inserts a list of tracks into the database. OnConflictStrategy is Replace.
     *
     * @param tracks Desired list of tracks to be inserted.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addAllTracks(vararg tracks: DatabaseTrack)







    /**
     * Returns a single track with the specified timestamp.
     *
     * @param trackId Key value of the desired track, identified by the track's timestamp.
     */
    @Query("SELECT * FROM track_table WHERE track_timestamp = :trackId")
    fun getTrack(trackId: Long): DatabaseTrack

    /**
     * Returns a list of all the tracks in the database.
     */
    @Query("SELECT * FROM track_table ORDER BY track_timestamp DESC")
    fun getAllTracks(): List<DatabaseTrack>







    /**
     * Returns a single LiveData track, chosen by the timestamp passed.
     *
     * @param trackId Key value of the desired track, identified by the track's timestamp.
     */
    @Query("SELECT * FROM track_table WHERE track_timestamp = :trackId")
    fun observeTrack(trackId: Long): LiveData<DatabaseTrack>

    /**
     * Returns a LiveData list of all the tracks in the database, organized in descending order by timestamp.
     */
    @Query ("SELECT * FROM track_table ORDER BY track_timestamp DESC")
    fun observeAllTracks() : LiveData<List<DatabaseTrack>>




    /**
     * Update the given track in the database.
     *
     * @param track Track to be updated.
     */
    @Update
    fun updateTrack(track: DatabaseTrack)

    /**
     * Updates each given track in the database.
     *
     * @param tracks Array of tracks to be updated.
     */
    @Update
    fun updateAllTracks(vararg tracks: DatabaseTrack)





    /**
     * Deletes every track in the database. 
     */
    @Query ("DELETE FROM track_table")
    fun deleteAllTotal()
}