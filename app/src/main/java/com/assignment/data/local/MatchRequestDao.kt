package com.assignment.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.assignment.data.local.entities.MatchRequestEntity

@Dao
interface MatchRequestDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(matchRequest: MatchRequestEntity): Long
}