package com.mrozon.feature_measure.presentation

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.mrozon.feature_measure.R
import com.mrozon.feature_measure.databinding.FragmentListMeasureBinding
import com.mrozon.feature_measure.di.TabMeasureFragmentComponent
import com.mrozon.utils.base.BaseFragment
import timber.log.Timber
import javax.inject.Inject

class ListMeasureFragment : BaseFragment<FragmentListMeasureBinding>() {

    companion object {
        private const val ARG_PERSON_ID = "person_id"
        private const val ARG_MEASURE_TYPE_ID = "measure_type_id"

        fun getInstance(personId: Long, measureTypeId: Long): ListMeasureFragment {
            val fragment = ListMeasureFragment()
            fragment.arguments = Bundle().apply {
                putLong(ARG_PERSON_ID, personId)
                putLong(ARG_MEASURE_TYPE_ID, measureTypeId)
            }
            return fragment
        }
    }

    override fun getLayoutId(): Int = R.layout.fragment_list_measure

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel by viewModels<ListMeasureFragmentViewModel> { viewModelFactory }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        TabMeasureFragmentComponent.injectFragment(this)
        Timber.d("onAttach")
    }

    override fun subscribeUi() {

    }

}