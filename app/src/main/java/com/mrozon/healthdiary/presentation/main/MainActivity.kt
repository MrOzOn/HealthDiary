package com.mrozon.healthdiary.presentation.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.mrozon.core.base.BaseActivity
import com.mrozon.healthdiary.R
import com.mrozon.healthdiary.databinding.ActivityMainBinding
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import javax.inject.Inject

class MainActivity : BaseActivity<ActivityMainBinding>() {

    @Inject
    lateinit var viewModel: MainActivityViewModel

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>

    override fun getLayoutId(): Int = R.layout.activity_main


    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initNavigation()
    }

    private fun initNavigation() {
        setSupportActionBar(binding.toolbar)
        val navController = findNavController(R.id.nav_host_fragment)
        appBarConfiguration = AppBarConfiguration(navController.graph, binding.drawerLayout)
        setupActionBarWithNavController(navController, appBarConfiguration)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }
}
