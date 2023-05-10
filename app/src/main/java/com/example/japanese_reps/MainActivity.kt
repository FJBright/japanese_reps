package com.example.japanese_reps

import MyDatabaseHelper
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.japanese_reps.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView


class MainActivity : AppCompatActivity() {
    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var dbHelper: MyDatabaseHelper

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Handles the initial setup and directs the user to the activity_main.xml
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Navigation controller used by the interchangeable fragment in activity_main.xml
        navController = findNavController(R.id.nav_host_fragment_activity_main)

        // Navigation bar adapted from a nav dropdown selection
        val bottomNavigationView: BottomNavigationView = binding.navView
        bottomNavigationView.setupWithNavController(navController)

        // If the navigation is to an individual flashcard fragment the bottom nav bar is hidden
        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id == R.id.individualFlashcard) {
                bottomNavigationView.visibility = View.GONE
            } else {
                bottomNavigationView.visibility = View.VISIBLE
            }
        }

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        // For the navbar I think?
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_lists, R.id.navigation_settings
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)

        dbHelper = MyDatabaseHelper(this)
        // Access the database through dbHelper instance
        val database = dbHelper.writableDatabase
        // Perform database operations as needed
        val dataList = dbHelper.getAllTableValues()
        // Do something with the retrieved data
        for (data in dataList) {
            // Access individual values: data.id, data.english, data.romanji, etc.
            println("ID: ${data.id}, English: ${data.english}")
        }
        // Remember to close the database when finished
        dbHelper.close()
    }

    // For the purposes of navigating backwards in the top left
    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration)
    }
}