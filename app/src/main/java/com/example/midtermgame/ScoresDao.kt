package com.example.midtermgame

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface ScoresDao {
    @Query("SELECT * FROM score_table WHERE userId = :key")
    fun get(key: Long): LiveData<Score>

    @Query("SELECT * FROM score_table ORDER BY user_score ASC")
    fun getAll(): LiveData<List<Score>>

    @Update
    suspend fun update(score: Score)

    @Insert
    suspend fun insert(score: Score): Long

    @Delete
    suspend fun delete(score: Score)
}