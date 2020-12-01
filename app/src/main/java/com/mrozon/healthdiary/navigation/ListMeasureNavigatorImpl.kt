package com.mrozon.healthdiary.navigation

import androidx.core.os.bundleOf
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.mrozon.core_api.navigation.ListMeasureNavigator
import com.mrozon.core_api.navigation.ListPersonNavigator
import com.mrozon.healthdiary.R
import javax.inject.Inject

class ListMeasureNavigatorImpl @Inject constructor()
    : ListMeasureNavigator {

    override fun navigateToEditMeasure(navController: NavController, title: String, id: Long) {
        val bundle = bundleOf("title" to title, "id" to id)
        navController.navigate(R.id.action_listMeasureFragment_to_editMeasureFragment, bundle)
    }
}
