package com.sayler666.movies

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.sayler666.movies.databinding.MainActivityBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        with(MainActivityBinding.inflate(layoutInflater)) {
            setContentView(root)

            val navController = findNavController(R.id.nav_host_fragment)
            navView.setupWithNavController(navController)

            val appBarConfiguration = AppBarConfiguration(setOf(R.id.navigation_top_rated, R.id.navigation_favourites))
            toolbar.setupWithNavController(navController, appBarConfiguration)

            navController.addOnDestinationChangedListener { _, destination, _ ->
                when (destination.id) {
                    R.id.navigation_details -> toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24)
                }
            }
            setSupportActionBar(toolbar)
            toolbar.setNavigationOnClickListener {
                if (!navController.popBackStack()) finish()
            }
        }
    }
}