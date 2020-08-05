package com.mrozon.healthdiary.presentation.showperson

import com.mrozon.core.base.BaseFragment
import com.mrozon.healthdiary.R
import com.mrozon.healthdiary.databinding.FragmentShowPersonBinding

class ShowPersonFragment: BaseFragment<FragmentShowPersonBinding>() {

    lateinit var showPersonViewModal: ShowPersonViewModal

    override fun getLayoutId(): Int = R.layout.fragment_show_person

}
