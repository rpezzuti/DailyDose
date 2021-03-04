package rhett.pezzuti.dailydose.utils

fun String.toDisplayGenre(): String {
    return when (this) {
        "dubstep" -> "Dubstep"
        "melodic-dubstep" -> "Melodic Dubstep"
        "lo-fi" -> "Lo-Fi"
        "chillstep" -> "Chillstep"
        "garage" -> "Garage"
        "piano-ambient" -> "Piano / Ambient"
        "experimental-bass" -> "Experimental Bass"
        "liquid-dnb" -> "Liquid DnB"
        "ambient-bass" -> "Ambient Bass"

        "metalcore" -> "Metalcore"
        "acoustic-ballads" -> "Acoustic / Ballads"
        "instrumental-rock" -> "Instrumental Rock"
        "death-metal" -> "Death Metal"
        "live-performances" -> "Live Performance"
        else -> ""
    }
}