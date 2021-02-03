package rhett.pezzuti.dailydose.database

import androidx.lifecycle.LiveData
import androidx.room.*

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
    fun favorite(track: DatabaseTrack)

    @Query ("SELECT * FROM track_table ORDER BY track_timestamp DESC")
    fun getAllTracks() : LiveData<List<DatabaseTrack>>

    @Query ("SELECT * FROM track_table WHERE is_favorite = :favorite")
    fun getFavorites(favorite: Boolean): LiveData<List<DatabaseTrack>>

    @Query("SELECT * FROM track_table ORDER BY track_timestamp DESC LIMIT 5")
    fun getRecentTracks(): LiveData<List<DatabaseTrack>>

    @Query ("SELECT * FROM track_table WHERE track_genre = :genre")
    fun getAllFromGenre(genre: String): LiveData<List<DatabaseTrack>>

    @Query ("DELETE FROM track_table")
    fun clearAll()
}

// Only gonna be one user.
@Dao
interface UserPreferencesDao {

    @Insert
    fun insert(user: User)

    @Update
    fun update(user: User)

    @Query("SELECT * FROM user_preferences")
    fun getCurrentUser(): LiveData<User?>

    @Query("SELECT * FROM user_preferences")
    fun isUserInitialized(): User?

    @Query("DELETE FROM user_preferences")
    fun clear()

}