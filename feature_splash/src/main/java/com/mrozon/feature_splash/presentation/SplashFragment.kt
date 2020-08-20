package com.mrozon.feature_splash.presentation


import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.mrozon.core_api.providers.AppWithFacade
import com.mrozon.feature_splash.R
import com.mrozon.feature_splash.databinding.FragmentSplashBinding
import com.mrozon.feature_splash.presentation.di.SplashFragmentComponent
import com.mrozon.utils.base.BaseFragment
import javax.inject.Inject

class SplashFragment : BaseFragment<FragmentSplashBinding>() {

    override fun getLayoutId(): Int = R.layout.fragment_splash

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var viewModel: SplashFragmentViewModel
//    private val viewModel by viewModels<SplashFragmentViewModel> { viewModelFactory }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.blaaa()
    }

//    override fun onAttach(context: Context) {
//        super.onAttach(context)
//        SplashFragmentComponent.create((requireActivity().application as AppWithFacade).getFacade())
//            .inject(this)
////        viewModel = ViewModelProviders.of(this, viewModelFactory).get(HomeViewModel::class.java)
//        viewModel = ViewModelProviders.of(this,viewModelFactory).get(SplashFragmentViewModel::class.java)
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        SplashFragmentComponent.create((requireActivity().application as AppWithFacade).getFacade())
            .inject(this)
//        viewModel = ViewModelProviders.of(this, viewModelFactory).get(HomeViewModel::class.java)
        viewModel = ViewModelProviders.of(this,viewModelFactory).get(SplashFragmentViewModel::class.java)
    }
}