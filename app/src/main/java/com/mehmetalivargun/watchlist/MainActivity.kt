package com.mehmetalivargun.watchlist

import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.mehmetalivargun.watchlist.databinding.ActivityMainBinding
import com.mehmetalivargun.watchlist.infra.BaseFragment
import com.mehmetalivargun.watchlist.ui.MovieList
import com.mehmetalivargun.watchlist.ui.MovieListDirections
import com.mehmetalivargun.watchlist.ui.WatchList
import com.mehmetalivargun.watchlist.ui.WatchListDirections
import com.trendyol.medusalib.navigator.MultipleStackNavigator
import com.trendyol.medusalib.navigator.Navigator
import com.trendyol.medusalib.navigator.NavigatorConfiguration
import com.trendyol.medusalib.navigator.transaction.NavigatorTransaction
import dagger.hilt.android.AndroidEntryPoint


private lateinit var binding: ActivityMainBinding

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), Navigator.NavigatorListener {
    lateinit var navigation: BottomNavigationView





    lateinit var toggle:ActionBarDrawerToggle
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)




        toggle=ActionBarDrawerToggle(this, binding.drawerLayout,R.string.open,R.string.close)
        binding.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        val navController = navHostFragment.navController
        binding.navView.setNavigationItemSelectedListener {
            when(it.itemId){
                R.id.watchListNav->{
                    Log.e("Visible",getVisibleFragment().toString())
                    if(getVisibleFragment() !is WatchList){

                        val action =
                            MovieListDirections.actionMovieListToWatchList()
                        findNavController(this,R.id.fragmentContainerView).navigate(action)
                        Log.e("TAG",getVisibleFragment().toString())
                        if (getVisibleFragment() is MovieList) {
                            Log.e("TAG",getVisibleFragment().toString())
                        }


                    }
                    binding.drawerLayout.closeDrawer(Gravity.LEFT)


                }
                R.id.movieListNav->{
                    Log.e("Visible",getVisibleFragment().toString())
                    if(getVisibleFragment() !is MovieList){

                        val action =
                            WatchListDirections.actionWatchListToMovieList()
                        findNavController(this,R.id.fragmentContainerView).navigate(action)

                    }
                    else{

                    }
                    binding.drawerLayout.closeDrawer(GravityCompat.START)
                }

         
            }
            true
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(toggle.onOptionsItemSelected(item)){
            return true
        }
        return super.onOptionsItemSelected(item)
    }


    private fun getVisibleFragment(): Fragment? {
        val fragmentManager = this@MainActivity.supportFragmentManager
        val fragments = fragmentManager.fragments
        for (fragment in fragments) {
            if (fragment != null && fragment.isVisible){
                return fragment
            }
        }
        return null
    }

    override fun onBackPressed() {
        if(binding.drawerLayout.isDrawerOpen(GravityCompat.START)){
            binding.drawerLayout.closeDrawer(GravityCompat.START)
        }
        else{
            super.onBackPressed()
        }

    }

    override fun onTabChanged(tabIndex: Int) {
        when (tabIndex) {
            0 -> navigation.selectedItemId = R.id.watchListNav
            1 -> navigation.selectedItemId = R.id.movieListNav
        }
    }
}