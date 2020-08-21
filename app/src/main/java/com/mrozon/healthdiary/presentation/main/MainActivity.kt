package com.mrozon.healthdiary.presentation.main

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.mrozon.core_api.providers.AppWithFacade
import com.mrozon.feature_splash.di.SplashFragmentComponent
import com.mrozon.feature_splash.presentation.SplashFragmentViewModel
import com.mrozon.healthdiary.R
import com.mrozon.healthdiary.databinding.ActivityMainBinding
import com.mrozon.healthdiary.di.main.DaggerMainActivityComponent
import com.mrozon.healthdiary.di.main.MainActivityComponent
import com.mrozon.utils.base.BaseActivity
import timber.log.Timber
import javax.inject.Inject

class MainActivity : BaseActivity<ActivityMainBinding>() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel by viewModels<MainActivityViewModel> { viewModelFactory }

    override fun getLayoutId(): Int = R.layout.activity_main

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var navController: NavController


    override fun onCreate(savedInstanceState: Bundle?) {
        initDI()
        super.onCreate(savedInstanceState)
        initNavigation()
    }

    private fun initDI() {
//        val component = SplashFragmentComponent.create(
//            (fragment.activity?.application
//                    as AppWithFacade).getFacade()
//        )
//        component.inject(fragment)
//        return component
//        DaggerMainActivityComponent.builder()
//            .databaseProvider((application as AppWithFacade).getFacade().provideDatabase())
//            .build()
//            .inject(this)
        MainActivityComponent.create((application as AppWithFacade).getFacade())
            .inject(this)
    }

    private fun initNavigation() {
        setSupportActionBar(binding.toolbar)
        drawerLayout = binding.drawerLayout
        navController = findNavController(R.id.nav_host_fragment)
        appBarConfiguration = AppBarConfiguration(navController.graph, binding.drawerLayout)
        setupActionBarWithNavController(navController, appBarConfiguration)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }

    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun subscribeUi() {
        viewModel.currentUser.observe(this, Observer {
            if(it==null){
                Timber.d("user is null")
            }
            else
            {
                Timber.d("user not null")
            }
        })
    }
}
