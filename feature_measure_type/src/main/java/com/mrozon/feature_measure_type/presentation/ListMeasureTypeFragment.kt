package com.mrozon.feature_measure_type.presentation

import android.content.Context
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.mrozon.feature_measure_type.R
import com.mrozon.feature_measure_type.databinding.FragmentListMeasureTypeBinding
import com.mrozon.feature_measure_type.di.ListMeasureTypeFragmentComponent
import com.mrozon.utils.base.BaseFragment
import javax.inject.Inject

class ListMeasureTypeFragment: BaseFragment<FragmentListMeasureTypeBinding>() {

    override fun getLayoutId(): Int = R.layout.fragment_list_measure_type

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel by viewModels<ListMeasureTypeFragmentViewModel> { viewModelFactory }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        ListMeasureTypeFragmentComponent.injectFragment(this)
    }

    override fun subscribeUi() {
//        TODO("Not yet implemented")
    }
}