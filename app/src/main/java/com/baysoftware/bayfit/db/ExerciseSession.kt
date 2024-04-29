package com.baysoftware.bayfit.db


import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "history_table")
data class ExerciseSession(
    @PrimaryKey @ColumnInfo(name = "data")
    val data: String,
    val duration: Int,
    val totalTimePause: Int
)