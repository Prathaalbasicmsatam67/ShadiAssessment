package com.assignment.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.assignment.data.local.entities.UserEntity
import com.assignment.data.local.entities.UserListJoinResult

@Dao
interface UserDao {

    @Query("SELECT * FROM users")
    fun getAllUsers(): LiveData<List<UserEntity>>


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllUsers(users: MutableList<UserEntity>)


    @Query("SELECT *, users.serverUUID as uuid FROM users LEFT JOIN match_req_status ON users.serverUUID = match_req_status.serverUUID")
    fun getAllUsersWithMatchStatus(): LiveData<List<UserListJoinResult>>


}