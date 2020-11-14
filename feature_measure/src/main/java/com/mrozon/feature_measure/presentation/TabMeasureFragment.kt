package com.mrozon.feature_measure.presentation

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.TableLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabItem
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.MODE_SCROLLABLE
import com.google.android.material.tabs.TabLayoutMediator
import com.mrozon.core_api.entity.Gender
import com.mrozon.feature_measure.R
import com.mrozon.feature_measure.databinding.FragmentTabMeasureBinding
import com.mrozon.feature_measure.di.TabMeasureFragmentComponent
import com.mrozon.utils.base.BaseFragment
import com.mrozon.utils.extension.setTitleActionBar
import com.mrozon.utils.extension.visible
import com.mrozon.utils.network.Result
import timber.log.Timber
import java.util.*
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val id = arguments?.getLong("id", 0)?:0
        if(id>0){
            viewModel.loadProfilePerson(id)
        }
    }

    override fun subscribeUi() {
        viewModel.selectedPerson.observe(viewLifecycleOwner, Observer { event ->
            event.getContentIfNotHandled()?.let { result ->
                when (result.status) {
                    Result.Status.LOADING -> {
                        binding?.progressBar?.visible(true)
                    }
                    Result.Status.SUCCESS -> {
                        if(arguments?.containsKey("id") == true)
                            arguments?.remove("id")
                        binding?.progressBar?.visible(false)
                        setTitleActionBar(result.data?.name!!)

//                        binding?.measureTypesTabs?.tabMode = MODE_SCROLLABLE
//                        binding?.measureTypesTabs?.addTab(TabLayout.Tab(),0)
//                        binding?.measureTypesTabs?.addTab(TabLayout.Tab(),1)
//                        binding?.measureTypesTabs?.addTab(TabLayout.Tab(),2)

//                       TabLayoutMediator(binding?.measureTypesTabs!!, binding?.viewpager!!) { tab, position ->
//                            tab.text = "position: $position"
//                       }

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