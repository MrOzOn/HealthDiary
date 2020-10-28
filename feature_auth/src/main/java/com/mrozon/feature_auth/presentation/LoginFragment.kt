package com.mrozon.feature_auth.presentation

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.mrozon.core_api.navigation.LoginNavigator
import com.mrozon.feature_auth.R
import com.mrozon.feature_auth.databinding.FragmentLoginBinding
import com.mrozon.feature_auth.di.LoginFragmentComponent
import com.mrozon.utils.base.BaseFragment
import com.mrozon.utils.extension.hideKeyboard
import com.mrozon.utils.extension.offer
import com.mrozon.utils.extension.shake
import com.mrozon.utils.extension.visible
import com.mrozon.utils.network.Result
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import javax.inject.Inject

class LoginFragment : BaseFragment<FragmentLoginBinding>() {

    override fun getLayoutId(): Int = R.layout.fragment_login

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var navigator: LoginNavigator

    private val viewModel by viewModels<LoginFragmentViewModel> { viewModelFactory }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        LoginFragmentComponent.injectFragment(this)
    }

    @ExperimentalCoroutinesApi
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.etUserName?.offer(viewModel.userNameChannel)
        binding?.etUserName?.setText(arguments?.getString("userName"))

        binding?.etUserPassword?.offer(viewModel.userPasswordChannel)

        binding?.btnLogin?.setOnClickListener {
            hideKeyboard()
            viewModel.loginUser()
        }

        binding?.btnRegistration?.setOnClickListener {
            hideKeyboard()
            navigator.navigateToRegisterUser(findNavController())
        }

    }


    @ExperimentalCoroutinesApi
    @FlowPreview
    override fun subscribeUi() {
        viewModel.enableLogin.observe(viewLifecycleOwner, Observer { validate ->
            if(validate!=null) {
                binding?.btnLogin?.isEnabled = validate
            }
        })

        viewModel.loggedUser.observe(viewLifecycleOwner, Observer { event ->
            event.getContentIfNotHandled().let { result ->
                if(result!=null){
                    when (result.status) {
                        Result.Status.LOADING -> {
                            binding?.progressBar?.visible(true)
                        }
                        Result.Status.SUCCESS -> {
                            binding?.progressBar?.visible(false)
                            navigator.navigateToListPerson(findNavController())
                        }
                        Result.Status.ERROR -> {
                            binding?.progressBar?.visible(false)
                            binding?.btnLogin?.shake()
                            showError(result.message!!)
                        }
                    }
                }
            }

        })

    }

}