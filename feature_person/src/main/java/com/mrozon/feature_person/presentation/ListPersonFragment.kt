package com.mrozon.feature_person.presentation

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.mrozon.feature_person.R
import com.mrozon.feature_person.databinding.FragmentListPersonBinding
import com.mrozon.feature_person.di.ListPersonFragmentComponent
import com.mrozon.utils.base.BaseFragment
import com.mrozon.utils.network.Result
import timber.log.Timber
import javax.inject.Inject

class ListPersonFragment : BaseFragment<FragmentListPersonBinding>() {

    override fun getLayoutId(): Int = R.layout.fragment_list_person

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel by viewModels<ListPersonFragmentViewModel> { viewModelFactory }

    private lateinit var adapter: ListPersonAdapter

    override fun onAttach(context: Context) {
        super.onAttach(context)
        ListPersonFragmentComponent.injectFragment(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = ListPersonAdapter(ListPersonAdapter.ListPersonListener { person ->
            Timber.d("click to ${person.name}")
        })
        binding?.rvPerson?.adapter = adapter
        val manager = LinearLayoutManager(context)
        binding?.rvPerson?.layoutManager = manager
    }

    override fun subscribeUi() {
        viewModel.persons.observe(viewLifecycleOwner, Observer { result ->
            when (result.status) {
                Result.Status.LOADING -> {
                    binding?.srlPerson?.isRefreshing = true
                }
                Result.Status.SUCCESS -> {
                    binding?.srlPerson?.isRefreshing = false
                    adapter.submitList(result.data)
                }
                Result.Status.ERROR -> {
                    binding?.srlPerson?.isRefreshing = false
                    showError(result.message!!) { }
                }
            }
        })
    }

}