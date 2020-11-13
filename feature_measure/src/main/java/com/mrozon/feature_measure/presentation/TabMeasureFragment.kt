package com.mrozon.feature_measure.presentation

import android.content.Context
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.mrozon.feature_measure.R
import com.mrozon.feature_measure.databinding.FragmentTabMeasureBinding
import com.mrozon.feature_measure.di.TabMeasureFragmentComponent
import com.mrozon.utils.base.BaseFragment
import timber.log.Timber
import javax.inject.Inject

class TabMeasureFragment: BaseFragment<FragmentTabMeasureBinding>() {

    override fun getLayoutId(): Int = R.layout.fragment_tab_measure

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel by viewModels<TabMeasureFragmentViewModel> { viewModelFactory }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        TabMeasureFragmentComponent.injectFragment(this)
        Timber.d("onAttach")
    }

    override fun subscribeUi() {

    }
}