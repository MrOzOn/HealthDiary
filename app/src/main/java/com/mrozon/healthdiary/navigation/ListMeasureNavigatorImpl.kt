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

    override fun navigateToEditMeasure(
        navController: NavController,
        title: String,
        id: Long,
        personId: Long,
        measureTypeId: Long
    ) {
        val bundle = bundleOf("title" to title, "id" to id, "personId" to personId, "measureTypeId" to measureTypeId)
        navController.navigate(R.id.action_tabMeasureFragment_to_editMeasureFragment, bundle)
    }
}
