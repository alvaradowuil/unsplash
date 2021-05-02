package com.walvarado.unsplashtest.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBar
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.walvarado.unsplashtest.R
import com.walvarado.unsplashtest.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {
    private lateinit var binding: ActivityMainBinding
    private lateinit var toolbar: ActionBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        toolbar = supportActionBar!!

        binding.navigationView.setOnNavigationItemSelectedListener(this)
        openFragment(UnsplashFragment.newInstance())
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.unsplash_option -> {
                toolbar.title = "Unsplash"
                openFragment(UnsplashFragment.newInstance())
                return true
            }
            R.id.favorites_option -> {
                toolbar.title = "Favorites"
                openFragment(FavoritesFragment.newInstance())
                return true
            }
        }
        return false
    }

    private fun openFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }
}