package com.mrozon.healthdiary.presentation.showperson

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.mrozon.healthdiary.R
import com.mrozon.healthdiary.databinding.FragmentShowPersonBinding

class ShowPersonFragment: Fragment() {

    private lateinit var viewModel: ShowPersonViewModal
    private lateinit var binding: FragmentShowPersonBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {

        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_show_person,
            container,
            false
        )

        return binding.root
    }

}