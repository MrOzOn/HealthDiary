package com.mrozon.feature_splash.presentation


import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.mrozon.core_api.navigation.SplashNavigator
import com.mrozon.feature_splash.R
import com.mrozon.feature_splash.databinding.FragmentSplashBinding
import com.mrozon.feature_splash.di.SplashFragmentComponent
import com.mrozon.utils.base.BaseFragment
import javax.inject.Inject


class SplashFragment : BaseFragment<FragmentSplashBinding>() {

    override fun getLayoutId(): Int = R.layout.fragment_splash

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var navigator: SplashNavigator

    private val viewModel by viewModels<SplashFragmentViewModel> { viewModelFactory }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        SplashFragmentComponent.injectFragment(this)
    }

    override fun subscribeUi() {
        viewModel.currentUser.observe(viewLifecycleOwner, Observer { it ->
            if(it==null){
                //auth user
                navigator.navigateToAuth(findNavController())
            }
            else
            {
                navigator.navigateToListPerson(findNavController())
            }
        })
    }


}
