package com.mrozon.feature_measure.presentation

import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.MaterialTimePicker.INPUT_MODE_CLOCK
import com.google.android.material.timepicker.TimeFormat
import com.mrozon.feature_measure.R
import com.mrozon.feature_measure.databinding.FragmentEditMeasureBinding
import com.mrozon.feature_measure.di.TabMeasureFragmentComponent
import com.mrozon.utils.base.BaseFragment
import com.mrozon.utils.extension.hideKeyboard
import com.mrozon.utils.extension.isActiveNetwork
import com.mrozon.utils.extension.toDateString
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import timber.log.Timber
import javax.inject.Inject

class EditMeasureFragment: BaseFragment<FragmentEditMeasureBinding>() {

    override fun getLayoutId(): Int = R.layout.fragment_edit_measure

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel by viewModels<EditMeasureFragmentViewModel> { viewModelFactory }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        TabMeasureFragmentComponent.injectFragment(this)
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
        arguments?.let {
            val id = requireArguments().getLong("id", 0)
            val personId = requireArguments().getLong("personId", 0)
            val measureTypeId = requireArguments().getLong("measureTypeId", 0)

            viewModel.initialLoadData(id, personId, measureTypeId)
        }

        binding?.buttonChooseDate?.setOnClickListener {
            try {
                val builder = MaterialDatePicker.Builder.datePicker()
                    .apply {
                        setInputMode(MaterialDatePicker.INPUT_MODE_CALENDAR)
                        setSelection(viewModel.currentDatetime.value?.time)
                    }
                val picker = builder.build()
                picker.addOnPositiveButtonClickListener {
                    viewModel.changeDate(it)
                }
                picker.show(childFragmentManager, picker.toString())
            } catch (e: IllegalArgumentException) {
                Timber.e(e)
                showError(e.message!!)
            }
        }

        binding?.buttonChooseTime?.setOnClickListener {
            try {
                val picker = MaterialTimePicker.Builder()
                    .setTimeFormat(TimeFormat.CLOCK_24H)
                    .setInputMode(INPUT_MODE_CLOCK)
                    .setHour(viewModel.currentHour)
                    .setMinute(viewModel.currentMinute)
                    .build()
                picker.addOnPositiveButtonClickListener {
                    viewModel.changeTime(picker.hour, picker.minute)
                }
                picker.show(childFragmentManager, picker.toString())
            } catch (e: IllegalArgumentException) {
                Timber.e(e)
                showError(e.message!!)
            }
        }
    }

    @ExperimentalCoroutinesApi
    @FlowPreview
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.add_measure_menu, menu)
        return super.onCreateOptionsMenu(menu, inflater)
    }

    @ExperimentalCoroutinesApi
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        hideKeyboard()
        if (!isActiveNetwork()){
            showError(getString(R.string.network_inactive))
            return false
        }
        return false
    }


    override fun subscribeUi() {
        viewModel.measureType.observe(viewLifecycleOwner, Observer { measureType ->
            measureType?.let {
                binding?.tilMeasureValue?.hint = getString(
                    R.string.hint_value_added,
                    it.name,
                    it.mark
                )
            }
        })
        viewModel.currentDatetime.observe(viewLifecycleOwner, Observer { datetime ->
            datetime?.let {
                binding?.tvMeasureAddedDate?.text = it.toDateString("EEE, d MMM YY HH:mm:ss")
            }
        })
    }
}