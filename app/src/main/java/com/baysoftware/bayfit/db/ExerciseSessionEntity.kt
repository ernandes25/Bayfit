package com.baysoftware.bayfit.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "exercise_session")
data class ExerciseSessionEntity(
    @PrimaryKey @ColumnInfo(name = "data") val date: String,
    @ColumnInfo(name = "duration") val duration: Int,
    @ColumnInfo(name = "total_time_pause") val totalTimePause: Int
)