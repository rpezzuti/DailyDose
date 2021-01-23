package rhett.pezzuti.dailydose.database

import android.content.Context
import androidx.room.*

@Entity (tableName = "user_preferences")
data class User (

    @PrimaryKey
    val username: String,

    @ColumnInfo
    val dubstep: Int,

    @ColumnInfo
    val melodicDubstep: Int,

    @ColumnInfo
    val loFi: Int,

    @ColumnInfo
    val chillstep: Int,

    @ColumnInfo
    val futureGarage: Int,

    @ColumnInfo
    val pianoAmbient: Int,

    @ColumnInfo
    val experimentalBass: Int,

    @ColumnInfo
    val liquidDnB: Int,

    @ColumnInfo
    val ambientBass: Int,

    @ColumnInfo
    val metalcore: Int,

    @ColumnInfo
    val acousticBallads: Int,

    @ColumnInfo
    val instrumentalRock: Int,

    @ColumnInfo
    val deathMetal: Int,

    @ColumnInfo
    val livePerformances: Int

)



@Dao
interface UserPreferencesDao {

    @Insert
    fun insert(user: User)

    @Update
    fun update(user: User)

}

@Database(entities = [User::class], version = 1, exportSchema = false)
abstract class SavedPreferences : RoomDatabase() {
    abstract val userPreferencesDao : UserPreferencesDao
}

private lateinit var INSTANCE: SavedPreferences

fun initializePreferences(context: Context) : SavedPreferences {
    synchronized(SavedPreferences::class.java) {
        if (!::INSTANCE.isInitialized) {
            INSTANCE = Room.databaseBuilder(context.applicationContext,
                SavedPreferences::class.java,
                "tracks")
                .fallbackToDestructiveMigration()
                .build()
        }
    }
    return INSTANCE
}