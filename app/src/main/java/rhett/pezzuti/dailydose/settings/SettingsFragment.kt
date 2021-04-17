package rhett.pezzuti.dailydose.settings

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import rhett.pezzuti.dailydose.R


// Need to have a unique implementation for this to come up.
class SettingsFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preferences, rootKey)


    }

}