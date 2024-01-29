package com.example.cherubook.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.onNavDestinationSelected
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.cherubook.R
import com.example.cherubook.databinding.ActivityMainBinding
import com.example.cherubook.ui.auth.AuthActivity
import com.example.cherubook.utility.ExceptionHelpers
import com.example.cherubook.utility.IViewModelState
import com.example.cherubook.utility.LifecycleListener
import com.example.cherubook.utility.LoadingState
import com.example.cherubook.utility.MainApplication
import com.example.cherubook.utility.SharedPreferenceHelpers

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private lateinit var authActivityIntent: Intent

    companion object {
        private lateinit var loadingView: View
        fun setLoadingStatus(viewModel: IViewModelState, viewLifecycleOwner: LifecycleOwner) {
            viewModel.loadingState.observe(viewLifecycleOwner) {
                when (it) {
                    LoadingState.Loading -> loadingView.visibility = View.VISIBLE
                    LoadingState.Loaded -> loadingView.visibility = View.GONE
                }
            }

        }

        fun setErrorStatus(viewModel: IViewModelState, viewLifecycleOwner: LifecycleOwner) {
            viewModel.errorState.observe(viewLifecycleOwner) {
                ExceptionHelpers.showErrorMessageByToast(it)
            }
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        loadingView=binding.content.fullPageLoadingView.root
        authActivityIntent=Intent(this@MainActivity, AuthActivity::class.java)

        val navView: BottomNavigationView = binding.content.navView

        navController = findNavController(R.id.nav_host_fragment_activity_main)
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.homeFragmentNav,R.id.profileFragmentNav,R.id.searchFragmentNav
            ),
            fallbackOnNavigateUpListener = ::onSupportNavigateUp
        )
        val callbacks = LifecycleListener()
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
        supportFragmentManager.registerFragmentLifecycleCallbacks(callbacks, true)
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)

        val searchMenuItem=menu.findItem(R.id.action_search)
        val searchView= searchMenuItem.actionView as SearchView
        searchView.queryHint="Search for a book"

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                val bundle=Bundle()
                bundle.putString("query",query)
                val builder= NavOptions.Builder()
                navController.currentDestination?.id?.let {
                    builder.setPopUpTo(it, inclusive = true, saveState = true)
                }
                navController.navigate(R.id.searchFragmentNav,bundle,builder.build())
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }
        })
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_info -> {
                Toast.makeText(MainApplication.applicationContext(), "Melek Kama", Toast.LENGTH_LONG)
                    .show()
                true
            }
            R.id.logout->{
                SharedPreferenceHelpers.clearAuth()
                startActivity(authActivityIntent)
                finish()
                return true
            }
            else-> return item.onNavDestinationSelected(navController) || super.onOptionsItemSelected(item)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}