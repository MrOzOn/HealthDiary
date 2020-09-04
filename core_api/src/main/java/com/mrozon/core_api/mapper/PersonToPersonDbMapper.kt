package com.mrozon.core_api.mapper

import com.mrozon.core_api.db.model.PersonDb
import com.mrozon.core_api.db.model.UserDb
import com.mrozon.core_api.entity.Gender
import com.mrozon.core_api.entity.Person
import com.mrozon.core_api.entity.User
import com.mrozon.utils.base.BaseMapper
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PersonToPersonDbMapper @Inject constructor(): BaseMapper<Person, PersonDb>() {

    override fun map(entity: Person?): PersonDb? {
        entity?.let {
            return PersonDb(id = it.id,
                name = it.name,
                gender = it.gender.code,
                born = it.born
            )
        }
        return null
    }

    override fun reverseMap(model: PersonDb?): Person? {
        model?.let {
            return Person(id = it.id,
                name = it.name,
                gender = Gender.values()[it.gender],
                born = it.born
            )
        }
        return null
    }

}