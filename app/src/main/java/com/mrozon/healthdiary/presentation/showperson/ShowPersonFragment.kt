package com.mrozon.healthdiary.presentation.showperson

import com.mrozon.utils.base.BaseFragment
import com.mrozon.healthdiary.R
import com.mrozon.healthdiary.databinding.FragmentShowPersonBinding
import javax.inject.Inject

class ShowPersonFragment: com.mrozon.utils.base.BaseFragment<FragmentShowPersonBinding>() {

    @Inject
    lateinit var showPersonViewModal: ShowPersonViewModal

    override fun getLayoutId(): Int = R.layout.fragment_show_person

}
