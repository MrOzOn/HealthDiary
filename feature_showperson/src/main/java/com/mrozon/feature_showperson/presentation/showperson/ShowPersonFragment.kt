package com.mrozon.feature_showperson.presentation.showperson

import android.os.Bundle
import android.view.View
import com.mrozon.feature_showperson.R
import com.mrozon.feature_showperson.databinding.FragmentShowPersonBinding
import timber.log.Timber
import javax.inject.Inject

class ShowPersonFragment: com.mrozon.utils.base.BaseFragment<FragmentShowPersonBinding>() {

    @Inject
    lateinit var showPersonViewModal: ShowPersonViewModal

    override fun getLayoutId(): Int = R.layout.fragment_show_person

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Timber.d("onViewCreated")
    }
}
