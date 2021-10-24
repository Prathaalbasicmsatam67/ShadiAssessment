package com.assignment.data.remote

import javax.inject.Inject

class UserRemoteDataSource @Inject constructor(
    private val userDataService: UserDataService
) : BaseDataSource() {

    suspend fun getUsers() = getResult { userDataService.getAllUsers() }

}