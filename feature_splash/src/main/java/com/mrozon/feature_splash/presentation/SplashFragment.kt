package com.mrozon.feature_splash.presentation


import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.mrozon.feature_splash.R
import com.mrozon.feature_splash.databinding.FragmentSplashBinding
import com.mrozon.feature_splash.di.SplashFragmentComponent
import com.mrozon.utils.base.BaseFragment
import javax.inject.Inject


class SplashFragment : BaseFragment<FragmentSplashBinding>() {

    override fun getLayoutId(): Int = R.layout.fragment_splash

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel by viewModels<SplashFragmentViewModel> { viewModelFactory }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        SplashFragmentComponent.injectFragment(this)
    }

    override fun subscribeUi() {
        viewModel.currentUser.observe(viewLifecycleOwner, Observer { it ->
            if(it==null){
                //auth user
            }
            else
            {
                //show profiles
            }
        })
    }


}
