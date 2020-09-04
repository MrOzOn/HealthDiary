package com.mrozon.feature_person.presentation

import android.content.Context
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.mrozon.feature_person.R
import com.mrozon.feature_person.databinding.FragmentListPersonBinding
import com.mrozon.feature_person.di.ListPersonFragmentComponent
import com.mrozon.utils.base.BaseFragment
import javax.inject.Inject

class ListPersonFragment : BaseFragment<FragmentListPersonBinding>() {

    override fun getLayoutId(): Int = R.layout.fragment_list_person

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel by viewModels<ListPersonFragmentViewModel> { viewModelFactory }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        ListPersonFragmentComponent.injectFragment(this)
    }

    override fun subscribeUi() {

    }

}