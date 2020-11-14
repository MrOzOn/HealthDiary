package com.mrozon.feature_measure.presentation

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.mrozon.core_api.entity.MeasureType

class TabMeasureAdapter(
    fragment: Fragment,
    private val personId: Long,
    private val measureTypes: List<MeasureType>) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = measureTypes.size

    override fun createFragment(position: Int): Fragment {
//        val fragment = DemoObjectFragment()
//        fragment.arguments = Bundle().apply {
//            // Our object is just an integer :-P
//            putInt(ARG_OBJECT, position + 1)
//        }
//        return fragment
        TODO("Not yet implemented createFragment for current Measure for current person")
    }
}