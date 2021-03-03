package rhett.pezzuti.dailydose.data

// For Retrofit to accept the notification data, there must be a variable
// annotated "data" for the payload to go through.
// The TO defines the TOPIC

data class TrackNotification(
    val data: Track,
    val to: String
)
