package com.mrozon.feature_person.presentation

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.view.*
import androidx.activity.addCallback
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout.LARGE
import com.mrozon.core_api.entity.Person
import com.mrozon.core_api.navigation.ListPersonNavigator
import com.mrozon.feature_person.R
import com.mrozon.feature_person.databinding.FragmentListPersonBinding
import com.mrozon.feature_person.di.ListPersonFragmentComponent
import com.mrozon.utils.base.BaseFragment
import com.mrozon.utils.extension.hideKeyboard
import com.mrozon.utils.extension.isActiveNetwork
import com.mrozon.utils.extension.visible
import com.mrozon.utils.network.Result
import timber.log.Timber
import javax.inject.Inject

class ListPersonFragment : BaseFragment<FragmentListPersonBinding>() {

    companion object {
        const val DELAY_EXIT_CONFIRM = 2000L
    }

    private var doubleBackToExitPressedOnce = false

    override fun getLayoutId(): Int = R.layout.fragment_list_person

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var navigator: ListPersonNavigator

    private val viewModel by viewModels<ListPersonFragmentViewModel> { viewModelFactory }

    private lateinit var adapter: ListPersonAdapter

    override fun onAttach(context: Context) {
        super.onAttach(context)
        ListPersonFragmentComponent.injectFragment(this)
        Timber.d("onAttach")
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
        adapter = ListPersonAdapter(object : ListPersonAdapter.ListPersonClickListener {
            override fun onClick(person: Person) {
                Timber.d("click to ${person.name}")
                navigator.navigateToMeasureForPerson(findNavController(), person.id)
            }

            override fun onLongClick(person: Person) {
                Timber.d("long click to ${person.name}")
                navigator.navigateToEditPerson(findNavController(),getString(R.string.edit_person),person.id)
            }
        })
        binding?.rvPerson?.adapter = adapter
        val manager = LinearLayoutManager(context)
        binding?.rvPerson?.layoutManager = manager

        binding?.fabAddPerson?.setOnClickListener {
            navigator.navigateToEditPerson(findNavController(),getString(R.string.add_person),0)
        }

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.list_person_menu, menu)
        return super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        hideKeyboard()
        when(item.itemId){
            R.id.refreshPersonNetwork -> {
                viewModel.refreshPersons()
            }
        }
        return false
    }

    override fun subscribeUi() {
        viewModel.persons.observe(viewLifecycleOwner, Observer { event ->
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requireActivity().onBackPressedDispatcher.addCallback(this) {
            finishIfConfirm()
        }
    }

    private fun finishIfConfirm() {
        if (doubleBackToExitPressedOnce)
            requireActivity().finish()
        doubleBackToExitPressedOnce = true
        show(getString(R.string.confirm_exit))
        Handler().postDelayed({
            doubleBackToExitPressedOnce = false
        }, DELAY_EXIT_CONFIRM)
    }
}