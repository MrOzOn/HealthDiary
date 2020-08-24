package com.mrozon.feature_auth.presentation

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import androidx.core.widget.addTextChangedListener
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.mrozon.feature_auth.R
import com.mrozon.feature_auth.databinding.FragmentLoginBinding
import com.mrozon.feature_auth.di.LoginFragmentComponent
import com.mrozon.utils.base.BaseFragment
import com.mrozon.utils.extension.hideKeyboard
import com.mrozon.utils.extension.offer
import com.mrozon.utils.extension.visible
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import timber.log.Timber
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

    @ExperimentalCoroutinesApi
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.etUserName?.offer(viewModel.userNameChannel)
        binding?.etUserPassword?.offer(viewModel.userPasswordChannel)

        btnLogin.setOnClickListener {
            hideKeyboard()
            viewModel.loginUser(etUserName.text.toString().trim(),etUserPassword.text.toString().trim())
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

        viewModel.progress.observe(viewLifecycleOwner, Observer { progress ->
            binding?.progressBar?.visible(progress)
            binding?.btnLogin?.isEnabled = !progress && viewModel.enableLogin.value?:false
            binding?.btnRegistration?.isEnabled = !progress
        })
    }

}