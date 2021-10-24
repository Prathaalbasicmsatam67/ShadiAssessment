package com.assignment.data.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.sql.RowId
import java.util.*

@Entity(tableName = "users")
data class UserEntity(

    @PrimaryKey(autoGenerate = true)
    val id: Int?,
    val name: String,
    val age : Int,
    val picURL: String,
    val city : String,
    val serverUUID: String?
)

@Entity(tableName = "match_req_status")
data class MatchRequestEntity(
    @PrimaryKey()
    val id: Int?,
    val serverUUID: String?,
    val userApproval: Int // 1 - Rejected, 2 - Approved, 0 - No Action
)

data class UserListJoinResult(
    @ColumnInfo(name="id") val userId: Int?,
    val name: String,
    val age : Int,
    val picURL: String,
    @ColumnInfo(name="uuid")
    val uuid: String?,
    val city : String,
    val userApproval: Int
)