package rhett.pezzuti.dailydose.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Update


@Dao
interface DatabaseDao {

    @Insert
    fun addTrack(track: DatabaseTrack)

    @Update ()
    fun updateTrack(track: DatabaseTrack)

}