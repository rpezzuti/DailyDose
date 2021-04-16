package rhett.pezzuti.dailydose.data.source.local

import androidx.lifecycle.LiveData
import androidx.room.*
import rhett.pezzuti.dailydose.data.DatabaseTrack

@Dao
interface TrackDatabaseDao {




    /**
     *
     */
    @Query ("SELECT * FROM track_table WHERE is_favorite = :favorite ORDER BY track_timestamp DESC")
    fun observeFavorites(favorite: Boolean): LiveData<List<DatabaseTrack>>

    @Query ("SELECT * FROM track_table WHERE is_favorite = :favorite ORDER BY track_timestamp DESC")
    fun getFavorites(favorite: Boolean): List<DatabaseTrack>




    /**
     *
     */
    @Query("SELECT * FROM track_table ORDER BY track_timestamp DESC LIMIT 5")
    fun observeRecent(): LiveData<List<DatabaseTrack>>

    @Query("SELECT * FROM track_table ORDER BY track_timestamp DESC LIMIT 5")
    fun getRecent(): List<DatabaseTrack>





    /**
     *
     */
    @Query ("SELECT * FROM track_table WHERE track_genre = :genre")
    fun observeGenre(genre: String): LiveData<List<DatabaseTrack>>

    @Query ("SELECT * FROM track_table WHERE track_genre = :genre")
    fun getGenre(genre: String): List<DatabaseTrack>








    /**
     *
     */
    @Insert
    fun addTrack(track: DatabaseTrack)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addAllTracks(vararg tracks: DatabaseTrack)







    /**
     *
     */
    @Query("SELECT * FROM track_table WHERE track_timestamp = :trackId")
    fun getTrack(trackId: Long): DatabaseTrack

    @Query("SELECT * FROM track_table ORDER BY track_timestamp DESC")
    fun getAllTracks(): List<DatabaseTrack>







    /**
     *
     */
    @Query("SELECT * FROM track_table WHERE track_timestamp = :trackId")
    fun observeTrack(trackId: Long): LiveData<DatabaseTrack>

    @Query ("SELECT * FROM track_table ORDER BY track_timestamp DESC")
    fun observeAllTracks() : LiveData<List<DatabaseTrack>>




    /**
     *
     */
    @Update
    fun updateTrack(track: DatabaseTrack)

    @Update
    fun updateAllTracks(vararg tracks: DatabaseTrack)





    /**
     *
     */
    @Query ("DELETE FROM track_table")
    fun deleteAllTotal()
}