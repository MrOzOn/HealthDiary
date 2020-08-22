package com.mrozon.feature_auth.presentation

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.mrozon.feature_auth.R
import com.mrozon.feature_auth.databinding.FragmentLoginBinding
import com.mrozon.feature_auth.di.LoginFragmentComponent
import com.mrozon.utils.base.BaseFragment
import javax.inject.Inject

class LoginFragment : BaseFragment<FragmentLoginBinding>() {

    override fun getLayoutId(): Int = R.layout.fragment_login

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel by viewModels<LoginFragmentViewModel> { viewModelFactory }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        LoginFragmentComponent.injectFragment(this)
    }

    override fun subscribeUi() {
        TODO("Not yet implemented")
    }

}