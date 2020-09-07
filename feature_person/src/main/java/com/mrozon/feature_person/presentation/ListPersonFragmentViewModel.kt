package com.mrozon.feature_person.presentation

import com.mrozon.feature_person.data.PersonRepository
import com.mrozon.utils.base.BaseViewModel
import javax.inject.Inject

class ListPersonFragmentViewModel @Inject constructor(repository: PersonRepository): BaseViewModel() {

    val persons by lazy { repository.getPersons() }
}