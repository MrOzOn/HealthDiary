package com.mrozon.healthdiary.presentation.main

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.viewModels
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.drawerlayout.widget.DrawerLayout.LOCK_MODE_LOCKED_CLOSED
import androidx.drawerlayout.widget.DrawerLayout.LOCK_MODE_UNLOCKED
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.google.android.material.navigation.NavigationView
import com.mrozon.core_api.entity.User
import com.mrozon.core_api.providers.AppWithFacade
import com.mrozon.healthdiary.R
import com.mrozon.healthdiary.databinding.ActivityMainBinding
import com.mrozon.healthdiary.di.main.MainActivityComponent
import com.mrozon.utils.base.BaseActivity
import com.mrozon.utils.network.Result
import timber.log.Timber
import javax.inject.Inject

class MainActivity : BaseActivity<ActivityMainBinding>(){

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel by viewModels<MainActivityViewModel> { viewModelFactory }

    override fun getLayoutId(): Int = R.layout.activity_main

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var navController: NavController
    private lateinit var navView: NavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        initDI()
        super.onCreate(savedInstanceState)
        initNavigation()
        subscribeUi()
    }

    private fun initDI() {
        MainActivityComponent.create((application as AppWithFacade).getFacade())
            .inject(this)
    }

    private fun initNavigation() {
        setSupportActionBar(binding.toolbar)
        drawerLayout = binding.drawerLayout
        navView = binding.navView
        navController = findNavController(R.id.nav_host_fragment)
        appBarConfiguration = AppBarConfiguration(
            setOf(R.id.listPersonFragment),
            binding.drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setNavigationItemSelectedListener {
            val currentDestinationId = navController.currentDestination?.id?:0
            when(it.itemId){
                R.id.show_person -> {
                    if(currentDestinationId!=R.id.listPersonFragment)
                        navController.navigate(R.id.action_global_listPersonFragment)
                }
            }
            drawerLayout.closeDrawer(GravityCompat.START)
            true
        }
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

        viewModel.cleared.observe(this, Observer { cleared ->
            cleared.getContentIfNotHandled()?.let {
                navController.navigate(R.id.action_global_loginFragment)
            }
        })


        viewModel.currentUser.observe(this, Observer { user ->
            showUserProfile(user)
        })
    }

    private fun showUserProfile(user: User?) {
        val headerView = binding.navView.getHeaderView(0)
        val tvUserEmail = headerView.findViewById<TextView>(R.id.tvUserEmail)
        val tvUserName = headerView.findViewById<TextView>(R.id.tvUserName)
        val ivLogout = headerView.findViewById<ImageView>(R.id.ivLogout)

        if (user == null) {
                Timber.d("user is null")
                supportActionBar?.hide()
                drawerLayout.setDrawerLockMode(LOCK_MODE_LOCKED_CLOSED)
                ivLogout.setOnClickListener(null)
            } else {
                Timber.d("user not null")
                supportActionBar?.show()
                drawerLayout.setDrawerLockMode(LOCK_MODE_UNLOCKED)
                tvUserEmail.text = user.email
                tvUserName.text = "${user.firstname}  ${user.lastname}"
                ivLogout.setOnClickListener {
                    if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                        drawerLayout.closeDrawer(GravityCompat.START)
                    }
                    viewModel.logoutUser(user)
                    showUserProfile(null)
                }
            }
    }
}
