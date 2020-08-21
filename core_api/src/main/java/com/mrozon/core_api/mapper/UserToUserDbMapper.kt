package com.mrozon.core_api.mapper

import com.mrozon.core_api.db.model.UserDb
import com.mrozon.core_api.entity.User
import com.mrozon.utils.base.BaseMapper
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserToUserDbMapper @Inject constructor() : BaseMapper<User, UserDb>() {

    override fun map(entity: User?): UserDb? {
        entity?.let {
            return UserDb(id = it.id,
                    email = it.email,
                    firstname = it.firstname,
                    lastname = it.lastname,
                    token = it.token
                )
        }
        return null
    }

    override fun reverseMap(model: UserDb?): User? {
        model?.let {
            return User(id = it.id,
                email = it.email,
                firstname = it.firstname,
                lastname = it.lastname,
                token = it.token
            )
        }
        return null
    }

}