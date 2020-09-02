package com.mrozon.feature_auth.presentation

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.mrozon.core_api.navigation.LoginNavigator
import com.mrozon.core_api.navigation.RegistrationNavigator
import com.mrozon.feature_auth.R
import com.mrozon.feature_auth.databinding.FragmentRegistrationBinding
import com.mrozon.feature_auth.di.LoginFragmentComponent
import com.mrozon.feature_auth.di.RegistrationFragmentComponent
import com.mrozon.utils.base.BaseFragment
import com.mrozon.utils.extension.hideKeyboard
import com.mrozon.utils.extension.offer
import com.mrozon.utils.extension.visible
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import timber.log.Timber
import javax.inject.Inject

class RegistrationFragment: BaseFragment<FragmentRegistrationBinding>() {

    override fun getLayoutId(): Int = R.layout.fragment_registration

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var navigator: RegistrationNavigator

    private val viewModel by viewModels<RegistrationFragmentViewModel> { viewModelFactory }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        RegistrationFragmentComponent.injectFragment(this)
    }

    @ExperimentalCoroutinesApi
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.etUserName?.offer(viewModel.emailChannel)
        binding?.etUserPassword?.offer(viewModel.passwordChannel)
        binding?.etUserPasswordAgain?.offer(viewModel.passwordConfirmChannel)
        binding?.etFirstName?.offer(viewModel.firstNameChannel)
        binding?.etLastName?.offer(viewModel.lastNameChannel)

        binding?.btnRegistration?.setOnClickListener {
            hideKeyboard()
            viewModel.registerUser()
        }

        binding?.btnRegistrationCancel?.setOnClickListener {
            hideKeyboard()
            navigator.navigateToLoginUser(findNavController())
        }
    }

    @ExperimentalCoroutinesApi
    @FlowPreview
    override fun subscribeUi() {

        viewModel.progress.observe(viewLifecycleOwner, Observer { progress ->
            binding?.progressBar?.visible(progress)
            binding?.btnRegistrationCancel?.isEnabled = !progress
            Timber.d("progress=${progress} and valid=${viewModel.validateData.value==ValidateDataError.OK}")
            binding?.btnRegistration?.isEnabled = (!progress) and (viewModel.validateData.value==ValidateDataError.OK)
        })

        viewModel.error.observe(viewLifecycleOwner, Observer {error ->
            if(error!=null)
                showError(error) {}
        })

        viewModel.registered.observe(viewLifecycleOwner, Observer { registeredUser ->
            if(registeredUser!=null) {
                showInfo(getString(R.string.userRegistered)) {
                    navigator.navigateToLoginUser(findNavController(), registeredUser)
                }
            }
        })

        viewModel.validateData.observe(viewLifecycleOwner, Observer {
            binding?.btnRegistration?.isEnabled =  false
            binding?.etUserPassword?.error = null
            binding?.etUserPasswordAgain?.error = null
            binding?.etUserName?.error = null
            binding?.etFirstName?.error = null
            binding?.etLastName?.error = null
            when (it) {
                ValidateDataError.OK -> {
                    binding?.btnRegistration?.isEnabled = true and !(viewModel.progress.value!!)
                }
                ValidateDataError.INCORRECT_EMAIL -> {
                    binding?.etUserName?.error = getString(R.string.uncorrect_email)
                }
                ValidateDataError.PASSWORD_EMPTY -> {
                    binding?.etUserPassword?.error = getString(R.string.empty_field)
                }
                ValidateDataError.PASSWORD_AGAIN_EMPTY -> {
                    binding?.etUserPasswordAgain?.error = getString(R.string.empty_field)
                }
                ValidateDataError.PASSWORD_NOT_EQUAL -> {
                    binding?.etUserPassword?.error = getString(R.string.equals_psw)
                }
                ValidateDataError.FIRST_NAME_EMPTY -> {
                    binding?.etFirstName?.error = getString(R.string.empty_field)
                }
                ValidateDataError.LAST_NAME_EMPTY -> {
                    binding?.etLastName?.error = getString(R.string.empty_field)
                }
                else -> {}
            }
        })
    }

}