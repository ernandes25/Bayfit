package com.baysoftware.bayfit.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate
import java.time.LocalTime

@Entity(tableName = "exercise_session")
data class ExerciseSessionEntity(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val date: LocalDate,
    val duration: LocalTime
)