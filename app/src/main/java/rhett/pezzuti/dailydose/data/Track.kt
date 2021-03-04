package rhett.pezzuti.dailydose.data

data class Track (
    val url: String,
    val title: String,
    val artist: String,
    val genre: String,
    val image: String,
    val timestamp: Long,
    var favorite: Boolean
)

fun List<Track>.asDatabaseModel(): Array<DatabaseTrack> {
    return map {
        DatabaseTrack(
            url = it.url,
            title = it.title,
            artist = it.artist,
            genre = it.genre,
            image = it.image,
            timestamp = it.timestamp,
            favorite = it.favorite
        )
    }.toTypedArray()
}