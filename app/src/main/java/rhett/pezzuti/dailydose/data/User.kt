package rhett.pezzuti.dailydose.data

data class User (
    val username: String,
    val dubstep : Boolean = false,
    val melodicDubstep: Boolean = false,
    val loFi: Boolean = false,
    val chillstep: Boolean = false,
    val futureGarage: Boolean = false,
    val pianoAmbient: Boolean = false,
    val experimentalBass: Boolean = false,
    val liquidDnB: Boolean = false,
    val ambientBass: Boolean = false,
    val metalcore: Boolean = false,
    val acousticBallads: Boolean = false,
    val instrumentalRock: Boolean = false,
    val deathMetal: Boolean = false,
    val livePerformances: Boolean = false
)