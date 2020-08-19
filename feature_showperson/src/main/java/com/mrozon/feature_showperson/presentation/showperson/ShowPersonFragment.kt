package com.mrozon.feature_showperson.presentation.showperson

import com.mrozon.feature_showperson.R
import com.mrozon.feature_showperson.databinding.FragmentShowPersonBinding
import javax.inject.Inject

class ShowPersonFragment: com.mrozon.utils.base.BaseFragment<FragmentShowPersonBinding>() {

    @Inject
    lateinit var showPersonViewModal: ShowPersonViewModal

    override fun getLayoutId(): Int = R.layout.fragment_show_person

}
