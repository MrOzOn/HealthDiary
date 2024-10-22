package com.mrozon.healthdiary.navigation

import androidx.core.os.bundleOf
import androidx.navigation.NavController
import com.mrozon.core_api.navigation.EditMeasureNavigator
import com.mrozon.core_api.navigation.EditPersonNavigator
import com.mrozon.healthdiary.R
import javax.inject.Inject

class EditMeasureNavigatorImpl @Inject constructor()
    : EditMeasureNavigator {

    override fun navigateToListMeasure(navController: NavController, id: Long, measureTypeId: Long) {
        val bundle = bundleOf("id" to id, "measureTypeId" to measureTypeId)
        navController.navigate(R.id.action_editMeasureFragment_to_listMeasureFragment, bundle)
    }
}
