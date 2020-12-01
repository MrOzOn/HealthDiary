package com.mrozon.feature_measure.presentation

import android.content.Context
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mrozon.core_api.entity.Measure
import com.mrozon.feature_measure.R
import com.mrozon.feature_measure.databinding.FragmentListMeasureBinding
import com.mrozon.feature_measure.di.TabMeasureFragmentComponent
import com.mrozon.utils.base.BaseFragment
import com.mrozon.utils.extension.hideKeyboard
import com.mrozon.utils.extension.setTitleActionBar
import com.mrozon.utils.extension.visible
import com.mrozon.utils.network.Result
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

    private var adapter: ListMeasureAdapter? = null

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
        val manager = LinearLayoutManager(context)
        binding?.rvMeasure?.apply {
            layoutManager = manager
            addItemDecoration(DividerItemDecoration(requireContext(),DividerItemDecoration.VERTICAL))
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.list_measure_menu, menu)
        return super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        hideKeyboard()
        when(item.itemId){
            R.id.refreshMeasuresNetwork -> {
                viewModel.refreshMeasuresNetwork()
            }
        }
        return false
    }

    override fun onResume() {
        super.onResume()
        arguments?.let {
            val personId = requireArguments().getLong(ARG_PERSON_ID, -1)
            val measureTypeId = requireArguments().getLong(ARG_MEASURE_TYPE_ID, -1)
            viewModel.initialLoadData(personId, measureTypeId)
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
        viewModel.initialData.observe(viewLifecycleOwner, Observer { event ->
            event.peekContent().let { result ->
                when (result.status) {
                    Result.Status.LOADING -> {
                        binding?.progressBar?.visible(true)
                    }
                    Result.Status.SUCCESS -> {
                        binding?.progressBar?.visible(false)
                        val person = result.data?.first
                        setTitleActionBar(person?.name?:"")
                        val measureType = result.data?.second
                        val measures = result.data?.third
                        Timber.d("measures contains ${measures?.size} items")
                        adapter = ListMeasureAdapter(measureType!!, object:
                            ListMeasureAdapter.ListMeasureClickListener {
                            override fun onClick(measure: Measure) {

                            }

                            override fun onLongClick(measure: Measure) {

                            }
                        })
                        binding?.rvMeasure?.adapter = adapter
                        adapter?.submitList(measures)
                    }
                    Result.Status.ERROR -> {
                        binding?.progressBar?.visible(false)
                        showError(result.message!!)
                    }
                }
            }
        })

        viewModel.measures.observe(viewLifecycleOwner, Observer { event ->
            event.peekContent().let { result ->
                when (result.status) {
                    Result.Status.LOADING -> {
                        binding?.progressBar?.visible(true)
                    }
                    Result.Status.SUCCESS -> {
                        binding?.progressBar?.visible(false)
                        adapter?.submitList(result.data)
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