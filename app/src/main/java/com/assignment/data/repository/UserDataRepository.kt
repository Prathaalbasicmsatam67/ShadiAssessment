package com.assignment.data.repository

import com.assignment.data.local.MatchRequestDao
import com.assignment.data.local.UserDao
import com.assignment.data.local.entities.MatchRequestEntity
import com.assignment.data.local.entities.UserEntity
import com.assignment.data.remote.UserRemoteDataSource
import com.assignment.utils.performGetOperation
import javax.inject.Inject

class UserDataRepository @Inject constructor(
    private val remoteDataSource: UserRemoteDataSource,
    private val localDataSource: UserDao,
    private val matchRequestDataSource: MatchRequestDao
) {


    fun getUsers() = performGetOperation(
        databaseQuery = { localDataSource.getAllUsersWithMatchStatus() },
        networkCall = { remoteDataSource.getUsers() },
        saveCallResult = {
            val users: MutableList<UserEntity> = ArrayList();
            for (user in it.results) {
                users.add(
                    UserEntity(
                        null,
                        user.name.title + " " + user.name.first + " " + user.name.last,
                        user.dob.age,
                        user.picture.large,
                        user.location.city,
                        user.login.uuid
                    )
                )
            }
            localDataSource.insertAllUsers(users)
        }
    )

    suspend fun updateUserStatus(uuid: String, status: Int) =
        matchRequestDataSource.insert(MatchRequestEntity(null, uuid, status))

}