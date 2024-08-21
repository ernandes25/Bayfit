package com.baysoftware.bayfit.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate
import java.time.LocalTime

@Entity(tableName = "exercise_sessions")
data class ExerciseSessionEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    var date: LocalDate,
    var totalTime: Int,
    var startTime: LocalTime,
    var endTime: LocalTime,
    var restTime: Int
)
