package com.forextimetestapp.ui.main

import android.os.Bundle
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import com.forextimetestapp.R
import com.forextimetestapp.ui.base.BaseActivity
import com.forextimetestapp.widget.disableShiftMode
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

    private lateinit var navHost: NavHostFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initNavigation()
    }

    private fun initNavigation() {
        navHost = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        disableShiftMode(bottom_navigation_main)
        bottom_navigation_main.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.list -> {
                    Navigation.findNavController(nav_host_fragment.view!!)
                        .navigate(R.id.listFragment)
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.favorite -> {
                    Navigation.findNavController(nav_host_fragment.view!!)
                        .navigate(R.id.favouriteFragment)
                    return@setOnNavigationItemSelectedListener true
                }
                else -> false
            }
        }
        bottom_navigation_main.selectedItemId = R.id.list
    }
}