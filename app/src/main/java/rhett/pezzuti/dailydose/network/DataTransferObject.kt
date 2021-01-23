package rhett.pezzuti.dailydose.network

data class FirebaseTrack(
    val artist: String,
    val genre: String,
    val image: String,
    val timestamp: Long,
    val title: String,
    val url: String
)
