package rhett.pezzuti.dailydose.data

data class Track (
    val timestamp: Long,
    val url: String,
    val title: String,
    val artist: String,
    val genre: String,
    val image: String,
    var favorite: Boolean
)

fun Track.asDatabaseModel(): DatabaseTrack {
    return DatabaseTrack(
        timestamp = this.timestamp,
        url = this.url,
        title = this.title,
        artist = this.artist,
        genre = this.genre,
        image = this.image,
        favorite = this.favorite
    )
}

fun List<Track>.asDatabaseModel(): Array<DatabaseTrack> {
    return map {
        DatabaseTrack(
            timestamp = it.timestamp,
            url = it.url,
            title = it.title,
            artist = it.artist,
            genre = it.genre,
            image = it.image,
            favorite = it.favorite
        )
    }.toTypedArray()
}