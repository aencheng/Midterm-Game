package com.example.midtermgame

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "score_table")
data class Score(
    @PrimaryKey(autoGenerate = true)
    var userId: Long = 0L,
    @ColumnInfo(name="user_name")
    var userName: String = "",
    @ColumnInfo(name="user_score")
    var userScore: Int = 0
)