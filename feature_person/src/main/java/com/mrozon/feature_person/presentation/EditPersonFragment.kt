package com.mrozon.feature_person.presentation

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.view.*
import android.widget.EditText
import androidx.core.app.ActivityCompat.invalidateOptionsMenu
import androidx.core.view.get
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.mrozon.core_api.entity.Gender
import com.mrozon.core_api.navigation.EditPersonNavigator
import com.mrozon.core_api.navigation.ListPersonNavigator
import com.mrozon.feature_person.R
import com.mrozon.feature_person.databinding.FragmentEditPersonBinding
import com.mrozon.feature_person.di.EditPersonFragmentComponent
import com.mrozon.utils.base.BaseFragment
import com.mrozon.utils.extension.*
import com.mrozon.utils.network.Result
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import timber.log.Timber
import java.util.*
import javax.inject.Inject


class EditPersonFragment : BaseFragment<FragmentEditPersonBinding>() {

    override fun getLayoutId(): Int = R.layout.fragment_edit_person

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var navigator: EditPersonNavigator

    private val viewModel by viewModels<EditPersonFragmentViewModel> { viewModelFactory }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        EditPersonFragmentComponent.injectFragment(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    @ExperimentalCoroutinesApi
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.radioGroup?.setOnCheckedChangeListener { _, id ->
            if(id==R.id.rbMale)
                viewModel.setMaleGender(true)
            else
                viewModel.setMaleGender(false)
        }

        binding?.etPersonName?.offer(viewModel.personNameChannel)

        binding?.pickerBoD?.init(2020,1,1)
        { _, year, month, day ->
            val date = "$year-${month+1}-$day"
//            Timber.d("string = $date date=${date.toSimpleDate().toDateString()}")
            viewModel.personDobChannel.offer(date.toSimpleDate())
        }
        val calendar = Calendar.getInstance()
        calendar.time = Date()
        binding?.pickerBoD?.updateDate(calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH))

        val id = arguments?.getLong("id",0)?:0
        if(id>0){
            viewModel.initValue(id)
        }
    }

    @ExperimentalCoroutinesApi
    @FlowPreview
    override fun subscribeUi() {
        viewModel.male.observe(viewLifecycleOwner, Observer { isMale ->
            if (isMale)
                binding?.ivGenger?.setImageResource(R.drawable.ic_male_avatar)
            else
                binding?.ivGenger?.setImageResource(R.drawable.ic_female_avatar)
        })

        viewModel.enableAdding.observe(viewLifecycleOwner, Observer { enabled ->
            invalidateOptionsMenu(activity)
        })

        viewModel.person.observe(viewLifecycleOwner, Observer { result ->
            if(result!=null){
                when (result.status) {
                    Result.Status.LOADING -> {
                        binding?.progressBar?.visible(true)
                    }
                    Result.Status.SUCCESS -> {
                        binding?.progressBar?.visible(false)
                        navigator.navigateToListPerson(findNavController())
                    }
                    Result.Status.ERROR -> {
                        binding?.progressBar?.visible(false)
                        showError(result.message!!)
                    }
                }
            }
        })

        viewModel.initPerson.observe(viewLifecycleOwner, Observer { result ->
            if(result!=null){
                when (result.status) {
                    Result.Status.LOADING -> {
                        binding?.progressBar?.visible(true)
                    }
                    Result.Status.SUCCESS -> {
                        arguments?.remove("id")
                        binding?.progressBar?.visible(false)
                        binding?.etPersonName?.setText(result.data?.name)
                        if (result.data?.gender==Gender.FEMALE)
                            binding?.radioGroup?.check(R.id.rbFemale)
                        else
                            binding?.radioGroup?.check(R.id.rbMale)
                        val calendar = Calendar.getInstance()
                        calendar.time = result.data?.born?:Date()
                        binding?.pickerBoD?.updateDate(calendar.get(Calendar.YEAR),
                            calendar.get(Calendar.MONTH),
                            calendar.get(Calendar.DAY_OF_MONTH))
                        viewModel.initDone()
                        arguments?.putLong("current_id",result.data?.id?:-1)
                        invalidateOptionsMenu(activity)
                    }
                    Result.Status.ERROR -> {
                        binding?.progressBar?.visible(false)
                        showError(result.message!!)
                    }
                }
            }
        })

        viewModel.deletedPerson.observe(viewLifecycleOwner, Observer { result ->
            if(result!=null){
                when (result.status) {
                    Result.Status.LOADING -> {
                        binding?.progressBar?.visible(true)
                    }
                    Result.Status.SUCCESS -> {
                        binding?.progressBar?.visible(false)
                        navigator.navigateToListPerson(findNavController())
                    }
                    Result.Status.ERROR -> {
                        binding?.progressBar?.visible(false)
                        showError(result.message!!)
                    }
                }
            }
        })

        viewModel.sharePerson.observe(viewLifecycleOwner, Observer { result ->
            if(result!=null){
                when (result.status) {
                    Result.Status.LOADING -> {
                        binding?.progressBar?.visible(true)
                    }
                    Result.Status.SUCCESS -> {
                        binding?.progressBar?.visible(false)
                        show(getString(R.string.share_done))
                    }
                    Result.Status.ERROR -> {
                        binding?.progressBar?.visible(false)
                        showError(result.message!!)
                    }
                }
            }
        })
    }

    @ExperimentalCoroutinesApi
    @FlowPreview
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.add_person_menu, menu)
        val saveMenuItem = menu.findItem(R.id.addPerson)
        viewModel.enableAdding.value.let {
            saveMenuItem.isVisible = it ?: false
        }
        val deleteMenuItem = menu.findItem(R.id.deletePerson)
        val shareMenu = menu.findItem(R.id.sharePersonToUser)
        val current_id = arguments?.getLong("current_id",-1)?:-1
        deleteMenuItem.isVisible = current_id>0
        shareMenu.isVisible = current_id>0
        return super.onCreateOptionsMenu(menu, inflater)
    }

    @ExperimentalCoroutinesApi
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        hideKeyboard()
        if (!isActiveNetwork()){
            showError(getString(R.string.network_inactive))
            return false
        }
        val current_id = arguments?.getLong("current_id",-1)?:-1
        when(item.itemId){
            R.id.addPerson -> {
                if(current_id>0){
                    viewModel.editPerson(current_id)
                }
                else {
                    viewModel.addPerson()
                }
            }
            R.id.deletePerson -> {
                viewModel.deletePerson(current_id)
            }
            R.id.sharePersonToUser -> {
                showShareDialog(current_id)
            }
        }
        return false
    }

    private fun showShareDialog(id: Long) {
        val dialogBuilder = AlertDialog.Builder(requireContext())
        dialogBuilder.apply {
            val view = layoutInflater.inflate(R.layout.dialog_share, null)
            setView(view)
            val etShareToUser = view.findViewById<EditText>(R.id.etShareToUser)
            setCancelable(false)
            setPositiveButton("Ok") { dialog, p1 ->
                dialog.dismiss()
                viewModel.sharePersonToUser(id, etShareToUser.text.toString())
            }
            setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }
        }
        val alertDialog = dialogBuilder.create()
        alertDialog.show()
    }


}