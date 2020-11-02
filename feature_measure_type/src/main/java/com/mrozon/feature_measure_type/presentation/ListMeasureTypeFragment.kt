package com.mrozon.feature_measure_type.presentation

import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.mrozon.core_api.entity.MeasureType
import com.mrozon.feature_measure_type.R
import com.mrozon.feature_measure_type.databinding.FragmentListMeasureTypeBinding
import com.mrozon.feature_measure_type.di.ListMeasureTypeFragmentComponent
import com.mrozon.utils.base.BaseFragment
import com.mrozon.utils.extension.hideKeyboard
import com.mrozon.utils.extension.visible
import com.mrozon.utils.network.Result
import javax.inject.Inject

class ListMeasureTypeFragment: BaseFragment<FragmentListMeasureTypeBinding>() {

    override fun getLayoutId(): Int = R.layout.fragment_list_measure_type

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel by viewModels<ListMeasureTypeFragmentViewModel> { viewModelFactory }

    private lateinit var adapter: MeasureTypeAdapter

    override fun onAttach(context: Context) {
        super.onAttach(context)
        ListMeasureTypeFragmentComponent.injectFragment(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = MeasureTypeAdapter(object: MeasureTypeAdapter.TypeMeasureClickListener {
            override fun onClick(measureType: MeasureType) {
//                TODO("Not yet implemented")
                show("will be soon))")
            }
        })
        binding?.rvMeasureTypes?.adapter = adapter
        val manager = LinearLayoutManager(context)
        binding?.rvMeasureTypes?.layoutManager = manager
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.list_measure_type_menu, menu)
        return super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        hideKeyboard()
        when(item.itemId){
            R.id.refreshMeasureTypes -> {
                viewModel.refreshMeasureTypes()
            }
        }
        return false
    }

    override fun subscribeUi() {
        viewModel.measure_types.observe(viewLifecycleOwner, Observer { event ->
            event.peekContent().let { result ->
                when (result.status) {
                    Result.Status.LOADING -> {
                        binding?.progressBar?.visible(true)
                    }
                    Result.Status.SUCCESS -> {
                        binding?.progressBar?.visible(false)
                        adapter.submitList(result.data)
                    }
                    Result.Status.ERROR -> {
                        binding?.progressBar?.visible(false)
                        showError(result.message!!)
                    }
                }
            }
        })
    }
}