package com.assignment.data.remote

import com.assignment.data.remote.entities.UserAPIResponse
import retrofit2.Response
import retrofit2.http.GET

interface UserDataService {
    @GET("api/?results=10")
    suspend fun getAllUsers(): Response<UserAPIResponse>

}