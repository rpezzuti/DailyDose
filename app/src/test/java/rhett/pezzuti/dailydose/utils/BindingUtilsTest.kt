package rhett.pezzuti.dailydose.utils

import org.junit.Test
import org.junit.Assert.*

class BindingUtilsTest {

    // Was giving me a Command line too long error, changed the shorten command line
    // From Java (default w/e) to JAR and it worked. yay!
    @Test
    fun getFabImageResource_false_returnsFavoriteBorder(){
        // Given
        val isFavorite: Boolean = false

        // When


        // Then
        assertEquals(false, isFavorite)
    }


}