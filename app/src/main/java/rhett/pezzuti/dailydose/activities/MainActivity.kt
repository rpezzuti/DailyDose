package rhett.pezzuti.dailydose.activities

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.*
import com.google.android.material.navigation.NavigationView
import rhett.pezzuti.dailydose.R

class MainActivity : AppCompatActivity() {


    private lateinit var drawerLayout: DrawerLayout
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {

        // Must be called before onCreate to reset the theme back to non-launcher
        setTheme(R.style.Theme_DailyDose)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupNavigationDrawer()
        setSupportActionBar(findViewById(R.id.main_toolbar))

        val navController: NavController = findNavController(R.id.myMainNavHostFragment)

        appBarConfiguration =
                AppBarConfiguration.Builder(R.id.homeFragment)
                    .setOpenableLayout(drawerLayout)
                    .build()

        setupActionBarWithNavController(navController, appBarConfiguration)

        // Code for the menu navigation
        findViewById<NavigationView>(R.id.main_nav_view)
            .setupWithNavController(navController)


       /* // setupActionBarWithNavController(navController, appBarConfiguration)
        // NavigationUI.setupWithNavController(findViewById(R.id.main_drawerLayout), navController)
        NavigationUI.setupActionBarWithNavController(this, navController, drawerLayout)*/

    }

    // Code for the sandwich Icon.
    override fun onSupportNavigateUp(): Boolean {
       /* val navController = this.findNavController(R.id.myMainNavHostFragment)
        return navController.navigateUp() || super.onSupportNavigateUp()*/


        return findNavController(R.id.myMainNavHostFragment).navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }

    private fun setupNavigationDrawer() {
        drawerLayout = (findViewById<DrawerLayout>(R.id.main_drawerLayout))
            .apply {
                setStatusBarBackground(R.color.design_default_color_primary_dark)
            }
    }
}